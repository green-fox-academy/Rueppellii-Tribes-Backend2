package rueppellii.backend2.tribes.resource.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rueppellii.backend2.tribes.building.service.BuildingService;
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

import static rueppellii.backend2.tribes.gameUtility.timeService.TimeConstants.ONE_MINUTE_IN_SECONDS;
import static rueppellii.backend2.tribes.resource.utility.ResourceFactory.makeResource;

@Service
public class ResourceService {
    private ResourceRepository resourceRepository;
    private TimeService timeService;
    private BuildingService buildingService;
    private KingdomService kingdomService;

    @Autowired
    public ResourceService(ResourceRepository resourceRepository, TimeService timeService, BuildingService buildingService, KingdomService kingdomService) {
        this.resourceRepository = resourceRepository;
        this.timeService = timeService;
        this.buildingService = buildingService;
        this.kingdomService = kingdomService;
    }

    public void saveResource(Resource resource) {
        resourceRepository.save(resource);
    }

    public Resource returnResource(ResourceType type, Long id) throws NoResourceException {
        return resourceRepository.findByTypeAndResourcesKingdom_Id(type, id).orElseThrow(() -> new NoResourceException("No resource found!"));
    }

    public void minusGoldAmount(Integer gold, Long kingdomId) throws NoResourceException {
        Resource resource = returnResource(ResourceType.GOLD, kingdomId);
        resource.setAmount(resource.getAmount() - gold);
        saveResource(resource);
    }

    public static List<Resource> starterKit() {
        List<Resource> starterResources = new ArrayList<>();
        for (ResourceType t : ResourceType.values()) {
            starterResources.add(makeResource(t));
        }
        return starterResources;
    }

    public void updateResources(Kingdom kingdom) {
        List<Resource> resources = kingdom.getKingdomsResources();
        for (Resource r : resources) {
            System.out.println(r.getType().getName());
            Integer baseResourcePerMinute = r.getResourcePerMinute();
            Integer totalResourceMultiplier = buildingService.getTotalResourceMultiplier(kingdom.getKingdomsBuildings(), r.getType());
            Integer elapsedSeconds = timeService.calculateElapsedSeconds(r.getUpdatedAt());
            Long remainderSeconds = timeService.calculateRemainder(r.getUpdatedAt());
            r.setUpdatedAt(System.currentTimeMillis() + remainderSeconds);
            if (r instanceof Gold) {
                System.out.println("Current amount " + r.getAmount());
                r.setAmount(r.getAmount() + calculateGold(baseResourcePerMinute, totalResourceMultiplier, elapsedSeconds));
                System.out.println("calculategold: " + calculateGold(baseResourcePerMinute, totalResourceMultiplier, elapsedSeconds).toString());
            }
            if (r instanceof Food) {
                r.setAmount(r.getAmount() + calculateFood(kingdom, baseResourcePerMinute, totalResourceMultiplier, elapsedSeconds));
            }
        }
        kingdomService.save(kingdom);
    }

    private Integer calculateFood(Kingdom kingdom, Integer baseResourcePerMinute,
                                  Integer totalResourceMultiplier, Integer elapsedSeconds) {
        if (kingdom.getKingdomsTroops() != null) {
            return elapsedSeconds * (baseResourcePerMinute + totalResourceMultiplier - kingdom.getKingdomsTroops().size()) / ONE_MINUTE_IN_SECONDS;
        }
        return elapsedSeconds * (baseResourcePerMinute + totalResourceMultiplier) / ONE_MINUTE_IN_SECONDS;
    }


    private Integer calculateGold(Integer baseResourcePerMinute, Integer totalResourceMultiplier, Integer elapsedSeconds) {
        return (int)((double) elapsedSeconds * ((double)(baseResourcePerMinute + totalResourceMultiplier) / ONE_MINUTE_IN_SECONDS));
    }

}
