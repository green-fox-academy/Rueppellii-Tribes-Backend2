package rueppellii.backend2.tribes.resource.service;

import com.google.common.collect.Iterables;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rueppellii.backend2.tribes.gameUtility.timeService.TimeService;
import rueppellii.backend2.tribes.kingdom.persistence.model.Kingdom;
import rueppellii.backend2.tribes.kingdom.service.KingdomService;
import rueppellii.backend2.tribes.resource.presistence.model.Food;
import rueppellii.backend2.tribes.resource.presistence.model.Gold;
import rueppellii.backend2.tribes.resource.presistence.model.Resource;
import rueppellii.backend2.tribes.resource.presistence.repository.ResourceRepository;
import rueppellii.backend2.tribes.resource.utility.ResourceType;
import rueppellii.backend2.tribes.resource.exception.NoResourceException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.requireNonNull;
import static rueppellii.backend2.tribes.gameUtility.timeService.TimeConstants.ONE_MINUTE_IN_SECONDS;
import static rueppellii.backend2.tribes.resource.utility.ResourceConstants.RESOURCE_PER_MINUTE_BUILDING_LEVEL_MULTIPLIER;
import static rueppellii.backend2.tribes.resource.utility.ResourceFactory.makeResource;

@Service
public class ResourceService {
    private ResourceRepository resourceRepository;
    private TimeService timeService;
    private KingdomService kingdomService;

    @Autowired
    public ResourceService(ResourceRepository resourceRepository, TimeService timeService, KingdomService kingdomService) {
        this.resourceRepository = resourceRepository;
        this.timeService = timeService;
        this.kingdomService = kingdomService;
    }

    public void saveResource(Resource resource) {
        resourceRepository.save(resource);
    }

    public Resource returnResource(ResourceType type, Long id) throws NoResourceException {
        return resourceRepository.findByTypeAndResourcesKingdom_Id(type, id).orElseThrow(() -> new NoResourceException("No resource found!"));
    }

    public Integer getKingdomsGoldAmount(Long kingdomId) throws NoResourceException {
        return returnResource(ResourceType.GOLD, kingdomId).getAmount();
    }

    public Boolean hasEnoughGold(Long kingdomId, Integer amount) throws NoResourceException {
        return getKingdomsGoldAmount(kingdomId) >= amount;
    }

    public void minusGoldAmount(Integer gold, Long kingdomId) throws NoResourceException {
        Resource resource = returnResource(ResourceType.GOLD, kingdomId);
        resource.setAmount(resource.getAmount() - gold);
        saveResource(resource);
    }

    public static List<Resource> starterKit(Kingdom kingdom) {
        List<Resource> starterResources = new ArrayList<>();
        for (ResourceType t : ResourceType.values()) {
            starterResources.add(makeResource(t));
        }
        starterResources.forEach(resource -> resource.setResourcesKingdom(kingdom));
        return starterResources;
    }

    public void updateResources(Kingdom kingdom) {
        List<Resource> resources = kingdom.getKingdomsResources();
        for (Resource r : resources) {
            Integer resourcePerMinute = r.getResourcePerMinute();
            Integer elapsedSeconds = timeService.calculateElapsedSeconds(r.getUpdatedAt());
            Long remainderSecondsInMillis = timeService.calculateRemainder(r.getUpdatedAt());
            if (r instanceof Gold && calculateGoldPerSecond(resourcePerMinute, elapsedSeconds) != 0) {
                r.setAmount(r.getAmount() + calculateGoldPerSecond(resourcePerMinute, elapsedSeconds));
                r.setUpdatedAt(System.currentTimeMillis() + remainderSecondsInMillis);
            }
            if (r instanceof Food && calculateFoodPerSecond(kingdom, resourcePerMinute, elapsedSeconds) != 0) {
                r.setAmount(r.getAmount() + calculateFoodPerSecond(kingdom, resourcePerMinute, elapsedSeconds));
                r.setUpdatedAt(System.currentTimeMillis() + remainderSecondsInMillis);
            }
        }
        kingdomService.save(kingdom);
    }

    private Integer calculateGoldPerSecond(Integer resourcePerMinute, Integer elapsedSeconds) {
        return (int) ((double) elapsedSeconds * ((double) resourcePerMinute / ONE_MINUTE_IN_SECONDS));
    }

    private Integer calculateFoodPerSecond(Kingdom kingdom, Integer resourcePerMinute, Integer elapsedSeconds) {
        if (kingdom.getKingdomsTroops() != null) {
            return elapsedSeconds * (resourcePerMinute - kingdom.getKingdomsTroops().size()) / ONE_MINUTE_IN_SECONDS;
        }
        return elapsedSeconds * (resourcePerMinute / ONE_MINUTE_IN_SECONDS);
    }

    public void setResourcePerMinute(String type, List<Resource> kingdomsResources) {
        if(type.equals("MINE")) {
            Gold gold = getGold(kingdomsResources);
            gold.setResourcePerMinute(gold.getResourcePerMinute() + RESOURCE_PER_MINUTE_BUILDING_LEVEL_MULTIPLIER);
            resourceRepository.save(gold);
        }
        if(type.equals("FARM")) {
            Food food = getFood(kingdomsResources);
            food.setResourcePerMinute(food.getResourcePerMinute() + RESOURCE_PER_MINUTE_BUILDING_LEVEL_MULTIPLIER);
            resourceRepository.save(food);
        }
    }

    private Gold getGold(List<Resource> kingdomsResources) {
        return (Gold) Iterables.getOnlyElement(kingdomsResources.stream()
                .filter(r -> r instanceof Gold)
                .collect(Collectors.toList()));
    }

    private Food getFood(List<Resource> kingdomsResources) {
        return (Food) Iterables.getOnlyElement(kingdomsResources.stream()
                .filter(r -> r instanceof Food)
                .collect(Collectors.toList()));
    }
}
