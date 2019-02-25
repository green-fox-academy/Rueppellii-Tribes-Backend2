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

    public Long calculateRemainderInMillis(Long updatedAt) {
        long elapsedTimeInMillis = System.currentTimeMillis() - updatedAt;
        return elapsedTimeInMillis % ONE_SECOND_IN_MILLIS;
    }

}
