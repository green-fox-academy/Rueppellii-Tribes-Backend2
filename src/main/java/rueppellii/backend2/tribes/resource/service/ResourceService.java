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
            Integer baseResourcePerMinute = r.getResourcePerMinute();
            Integer totalResourceMultiplier = buildingService.getTotalResourceMultiplier(kingdom.getKingdomsBuildings(), r.getType());
            Integer elapsedTimeInMinutes = timeService.calculateElapsedMinutes(r.getUpdatedAt());
            if (r instanceof Gold) {
                r.setAmount(r.getAmount() + calculateGold(baseResourcePerMinute, totalResourceMultiplier, elapsedTimeInMinutes));
            }
            if (r instanceof Food) {
                r.setAmount(r.getAmount() + calculateFood(kingdom, baseResourcePerMinute, totalResourceMultiplier, elapsedTimeInMinutes));
            }
        }
        kingdom.setKingdomsResources(resources);
        kingdomService.save(kingdom);
    }

    private Integer calculateFood(Kingdom kingdom, Integer baseResourcePerMinute,
                                  Integer totalResourceMultiplier, Integer elapsedTimeInMinutes) {
        if (kingdom.getKingdomsTroops() != null) {
            return elapsedTimeInMinutes * (baseResourcePerMinute + totalResourceMultiplier - kingdom.getKingdomsTroops().size());
        }
        return elapsedTimeInMinutes * (baseResourcePerMinute + totalResourceMultiplier);
    }


    private Integer calculateGold(Integer baseResourcePerMinute, Integer totalResourceMultiplier, Integer elapsedTimeInMinutes) {
        return elapsedTimeInMinutes * (baseResourcePerMinute + totalResourceMultiplier);
    }

}
