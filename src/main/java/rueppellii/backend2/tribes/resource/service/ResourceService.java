package rueppellii.backend2.tribes.resource.service;

import com.google.common.collect.Iterables;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rueppellii.backend2.tribes.gameUtility.timeService.TimeService;
import rueppellii.backend2.tribes.kingdom.persistence.model.Kingdom;
import rueppellii.backend2.tribes.resource.presistence.model.Food;
import rueppellii.backend2.tribes.resource.presistence.model.Gold;
import rueppellii.backend2.tribes.resource.presistence.model.Resource;
import rueppellii.backend2.tribes.resource.presistence.repository.ResourceRepository;
import rueppellii.backend2.tribes.resource.utility.ResourceType;
import rueppellii.backend2.tribes.resource.exception.NoResourceException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static rueppellii.backend2.tribes.gameUtility.timeService.TimeConstants.ONE_MINUTE_IN_MILLIS;
import static rueppellii.backend2.tribes.resource.utility.ResourceConstants.RESOURCE_PER_MINUTE_BUILDING_LEVEL_MULTIPLIER;
import static rueppellii.backend2.tribes.resource.utility.ResourceFactory.makeResource;

@Service
public class ResourceService {
    private ResourceRepository resourceRepository;
    private TimeService timeService;

    @Autowired
    public ResourceService(ResourceRepository resourceRepository, TimeService timeService) {
        this.resourceRepository = resourceRepository;
        this.timeService = timeService;
    }

    private Resource returnResource(ResourceType type, Long id) throws NoResourceException {
        return resourceRepository.findByTypeAndResourcesKingdom_Id(type, id).orElseThrow(() ->
                new NoResourceException("No resource found!"));
    }

    private Integer getKingdomsGoldAmount(Long kingdomId) throws NoResourceException {
        return returnResource(ResourceType.GOLD, kingdomId).getAmount();
    }

    public Boolean hasEnoughGold(Long kingdomId, Integer amount) throws NoResourceException {
        return getKingdomsGoldAmount(kingdomId) >= amount;
    }

    public void minusGoldAmount(Integer gold, Long kingdomId) throws NoResourceException {
        Resource resource = returnResource(ResourceType.GOLD, kingdomId);
        resource.setAmount(resource.getAmount() - gold);
        resourceRepository.save(resource);
    }

    public static List<Resource> starterKit(Kingdom kingdom) {
        List<Resource> starterResources = new ArrayList<>();
        for (ResourceType t : ResourceType.values()) {
            starterResources.add(makeResource(t));
        }
        starterResources.forEach(resource -> resource.setResourcesKingdom(kingdom));
        return starterResources;
    }

    public void updateResources(List<Resource> resources) {
        for (Resource r : resources) {
            Integer resourcePerMinute = r.getResourcePerMinute();
            Long elapsedMillis = timeService.calculateElapsedMillis(r.getUpdatedAt());
            if (calculateResourceForElapsedTime(resourcePerMinute, elapsedMillis) != 0) {
                Integer calculateResourceForElapsedTime = calculateResourceForElapsedTime(resourcePerMinute, elapsedMillis);
                r.setAmount(r.getAmount() + calculateResourceForElapsedTime);
                r.setUpdatedAt(System.currentTimeMillis() -
                        timeService.calculateRemainderMillis(resourcePerMinute, elapsedMillis));
                resourceRepository.save(r);
            }
        }
    }

    private Integer calculateResourceForElapsedTime(Integer resourcePerMinute, Long elapsedMillis) {
        double resourcePerMillis = (double) resourcePerMinute / ONE_MINUTE_IN_MILLIS;
        double resourceForElapsedMillis = (double) elapsedMillis * resourcePerMillis;
        return (int)resourceForElapsedMillis;
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

    public void feedTheTroop(Kingdom kingdom) {
        Food food = getFood(kingdom.getKingdomsResources());
        food.setResourcePerMinute(food.getResourcePerMinute() - 1);
        resourceRepository.save(food);
    }
}
