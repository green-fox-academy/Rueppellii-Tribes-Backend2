package rueppellii.backend2.tribes.building;

import javax.persistence.Entity;
import java.sql.Timestamp;

@Entity
public class TownHall extends Building {

    public TownHall() {
        setType(BuildingType.TOWNHALL);
        setHP(10);
        setStarted_at(new Timestamp(System.currentTimeMillis()));
    }
}
