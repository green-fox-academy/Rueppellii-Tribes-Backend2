package rueppellii.backend2.tribes.gameUtility.timeService;

import org.springframework.stereotype.Service;
import rueppellii.backend2.tribes.progression.persistence.ProgressionModel;

import static rueppellii.backend2.tribes.gameUtility.timeService.TimeConstants.*;
import static rueppellii.backend2.tribes.gameUtility.timeService.TimeConstants.ONE_MINUTE_IN_MILLIS;

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

    public Long calculateElapsedMillis(Long updatedAt){
        return (System.currentTimeMillis() - updatedAt);
    }

    public Long calculateRemainderMillis(Integer resourcePerMinute, Long elapsedMillis){
        double remainderResource = ((double) elapsedMillis * ((double) resourcePerMinute / ONE_MINUTE_IN_MILLIS)) % 1;
        return ((long)(remainderResource * ONE_MINUTE_IN_MILLIS) / resourcePerMinute);
    }

}
