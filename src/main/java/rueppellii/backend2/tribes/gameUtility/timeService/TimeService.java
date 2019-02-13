package rueppellii.backend2.tribes.gameUtility.timeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rueppellii.backend2.tribes.building.service.BuildingService;
import rueppellii.backend2.tribes.kingdom.persistence.model.Kingdom;
import rueppellii.backend2.tribes.progression.persistence.ProgressionModel;

import static rueppellii.backend2.tribes.gameUtility.timeService.TimeConstants.*;

@Service
public class TimeService {

    private BuildingService buildingService;

    @Autowired
    public TimeService(BuildingService buildingService) {
        this.buildingService = buildingService;
    }

    public Long calculateTimeOfBuildingCreation(Kingdom kingdom) {
        return System.currentTimeMillis() + (BUILDING_CREATION_TIME / buildingService.getLevelOfTownHall(kingdom.getKingdomsBuildings()));
    }

    public Long calculateTimeOfBuildingUpgrade(Kingdom kingdom) {
        return System.currentTimeMillis() + (BUILDING_UPGRADE_TIME / buildingService.getLevelOfTownHall(kingdom.getKingdomsBuildings()));
    }

    public Long calculateTimeOfTroopCreationAndUpgrade(Kingdom kingdom) {
        return System.currentTimeMillis() + (long) (TROOP_CREATION_AND_UPGRADE_TIME * buildingService.getTroopUpgradeTimeMultiplier(kingdom));
    }

    public Boolean timeIsUp(ProgressionModel progressionModel) {
        return progressionModel.getTimeToProgress() <= System.currentTimeMillis();
    }

    public Integer calculateElapsedSeconds(Long updatedAt){
        long elapsedTimeInMillis = System.currentTimeMillis() - updatedAt;
        return (int) ((double) elapsedTimeInMillis / ONE_SECOND_IN_MILLIS);
    }

    public Long calculateRemainder(Long updatedAt) {
        long elapsedTimeInMillis = System.currentTimeMillis() - updatedAt;
        return elapsedTimeInMillis % ONE_SECOND_IN_MILLIS;
    }

    //TODO: Might need Later
//    public Long getBuildingIdToProgress(ApplicationUser applicationUser, Integer actionCode, String gameObjectToProgress) {
//        List<Building> buildingsToProgress = applicationUser.getTroopsKingdom().getKingdomsBuildings()
//                .stream()
//                .filter(building -> building.getType().getName().matches(gameObjectToProgress.toUpperCase()))
//                .filter(building -> Objects.equals(building.getLevel(), actionCode))
//                .collect(Collectors.toList());
//        return buildingsToProgress.get(0).getBuilding_id();
//    }
//
//    public Long getTroopIdToProgress(ApplicationUser applicationUser, Integer actionCode, String gameObjectToProgress) {
//        List<Troop> troopsToProgress = applicationUser.getTroopsKingdom().getTroops()
//                .stream()
//                .filter(troop -> Objects.equals(troop.getLevel(), actionCode))
//                .collect(Collectors.toList());
//        return troopsToProgress.get(0).getTroop_id();
//    }


}
