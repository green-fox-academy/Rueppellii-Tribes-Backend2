package rueppellii.backend2.tribes.timeService;

import rueppellii.backend2.tribes.building.Building;
import rueppellii.backend2.tribes.troop.models.Troop;

import java.sql.Timestamp;
import java.util.concurrent.TimeUnit;

public class TimeServiceImp implements TimeService{

    private Long seconds;
    private Troop troop;
    private Building building;

    public TimeServiceImp(Troop troop, Building building) {
        this.seconds = TimeUnit.MINUTES.toSeconds(5);
        this.troop = troop;
        this.building = building;
    }

    @Override
    public Long setTroopLevelBonus(Integer currentLevel) {
        seconds -= currentLevel * 30;
        return seconds;
    }

    @Override
    public Long setBuildingLevelPenalty(Integer currentLevel) {
        seconds += currentLevel * 30;
        return seconds;
    }

    @Override
    public Timestamp setConstructingTime(Long second) {
        return new Timestamp(System.currentTimeMillis() + seconds);
    }
}
