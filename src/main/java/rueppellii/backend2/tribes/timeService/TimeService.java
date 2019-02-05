package rueppellii.backend2.tribes.timeService;

import java.sql.Timestamp;
import java.util.concurrent.TimeUnit;

public interface TimeService {

    Long objectCreatingTime = TimeUnit.MINUTES.toSeconds(5);
    Long setLevelBonus(Integer currentLevel);
    Timestamp setConstructingTime(Long second);

}
