package rueppellii.backend2.tribes.gameUtility.timeService;

import org.springframework.stereotype.Service;
import rueppellii.backend2.tribes.progression.persistence.ProgressionModel;

import static rueppellii.backend2.tribes.gameUtility.timeService.TimeConstants.*;

@Service
public class TimeService {

    public Long calculateTimeOfBuildingCreation() {
        return System.currentTimeMillis() + BUILDING_PROGRESSION_TIME;
    }

    public Long calculateTimeOfBuildingUpgrade(Integer buildingLevel, Integer townhallLevel) {
        return System.currentTimeMillis() + (BUILDING_PROGRESSION_TIME * buildingLevel) / townhallLevel;
    }

    public Long calculateTimeOfTroopCreation(Double troopUpgradeTimeMultiplier) {
        return System.currentTimeMillis() + (long) (TROOP_PROGRESSION_TIME * troopUpgradeTimeMultiplier);
    }

    public Long calculateTimeOfTroopUpgrade(Double troopUpgradeTimeMultiplier, Integer troopLevel) {
        return System.currentTimeMillis() + (long) (TROOP_PROGRESSION_TIME * troopUpgradeTimeMultiplier * troopLevel);
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
