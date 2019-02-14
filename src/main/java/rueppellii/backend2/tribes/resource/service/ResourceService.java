package rueppellii.backend2.tribes.resource.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rueppellii.backend2.tribes.building.persistence.model.Building;
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

import static java.util.Objects.requireNonNull;
import static rueppellii.backend2.tribes.gameUtility.timeService.TimeConstants.ONE_MINUTE_IN_SECONDS;
import static rueppellii.backend2.tribes.resource.utility.ResourceConstants.RESOURCE_PER_MINUTE_BUILDING_LEVEL_MULTIPLIER;
import static rueppellii.backend2.tribes.resource.utility.ResourceConstants.RESOURCE_PER_MINUTE_BUILDING_LEVEL_ONE;
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
    //TODO: Archie: Fix resource service to update resourcePerMinute in GOLD and FOOD entities
    public void updateResources(Kingdom kingdom) {
        List<Resource> resources = kingdom.getKingdomsResources();
        for (Resource r : resources) {
            Integer baseResourcePerMinute = r.getResourcePerMinute();
            Integer totalBuildingLevelMultiplier = getTotalBuildingLevelMultiplier(kingdom.getKingdomsBuildings(), r);
            Integer elapsedSeconds = timeService.calculateElapsedSeconds(r.getUpdatedAt());
            Long remainderSeconds = timeService.calculateRemainder(r.getUpdatedAt());
            if (r instanceof Gold && calculateGold(baseResourcePerMinute, totalBuildingLevelMultiplier, elapsedSeconds) != 0) {
                r.setAmount(r.getAmount() + calculateGold(baseResourcePerMinute, totalBuildingLevelMultiplier, elapsedSeconds));
                r.setUpdatedAt(System.currentTimeMillis() + remainderSeconds);
            }
            if (r instanceof Food && calculateFood(kingdom, baseResourcePerMinute, totalBuildingLevelMultiplier, elapsedSeconds) != 0) {
                r.setAmount(r.getAmount() + calculateFood(kingdom, baseResourcePerMinute, totalBuildingLevelMultiplier, elapsedSeconds));
                r.setUpdatedAt(System.currentTimeMillis() + remainderSeconds);
            }
        }
        kingdomService.save(kingdom);
    }

    public Integer getTotalBuildingLevelMultiplier(List<Building> kingdomsBuildings, Resource resource) {
        String buildingName = null;
        if (resource instanceof Gold) {
            buildingName = "MINE";
        }
        if (resource instanceof Food) {
            buildingName = "FARM";
        }
        String finalBuildingName = buildingName;
        Integer numberOfBuildings = (int) kingdomsBuildings.stream().filter(building -> building.getType().getName().matches(requireNonNull(finalBuildingName))).count();
        Integer levelOneBuildingMultiplier = numberOfBuildings * RESOURCE_PER_MINUTE_BUILDING_LEVEL_ONE;
        Integer totalLevelOfBuildings = kingdomsBuildings.stream().filter(building -> building.getType().getName().matches(finalBuildingName)).mapToInt(Building::getLevel).sum();
        if (!numberOfBuildings.equals(totalLevelOfBuildings)) {
            Integer totalBuildingLevelMultiplier = RESOURCE_PER_MINUTE_BUILDING_LEVEL_MULTIPLIER * (totalLevelOfBuildings - numberOfBuildings);
            return levelOneBuildingMultiplier + totalBuildingLevelMultiplier;
        }
        return levelOneBuildingMultiplier;
    }

    private Integer calculateFood(Kingdom kingdom, Integer baseResourcePerMinute,
                                  Integer totalResourceMultiplier, Integer elapsedSeconds) {
        if (kingdom.getKingdomsTroops() != null) {
            return elapsedSeconds * (baseResourcePerMinute + totalResourceMultiplier - kingdom.getKingdomsTroops().size()) / ONE_MINUTE_IN_SECONDS;
        }
        return elapsedSeconds * (baseResourcePerMinute + totalResourceMultiplier) / ONE_MINUTE_IN_SECONDS;
    }

    private Integer calculateGold(Integer baseResourcePerMinute, Integer totalResourceMultiplier, Integer elapsedSeconds) {
        return (int) ((double) elapsedSeconds * ((double) (baseResourcePerMinute + totalResourceMultiplier) / ONE_MINUTE_IN_SECONDS));
    }

}
