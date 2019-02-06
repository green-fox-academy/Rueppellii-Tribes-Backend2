package rueppellii.backend2.tribes.timeService;

import java.sql.Timestamp;

public interface TimeService {

    Long levelBonus(Integer currentLevel);
    Timestamp constructingTime(Long buildingTime);
    Timestamp upgradingTime(Long buildingTime);
}
