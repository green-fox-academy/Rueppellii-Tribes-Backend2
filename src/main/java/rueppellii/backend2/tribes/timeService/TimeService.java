package rueppellii.backend2.tribes.timeService;

import java.sql.Timestamp;

public interface TimeService {

    Long setTroopLevelBonus(Integer currentLevel);
    Long setBuildingLevelPenalty(Integer currentLevel);
    Timestamp setConstructingTime(Long second);

}
