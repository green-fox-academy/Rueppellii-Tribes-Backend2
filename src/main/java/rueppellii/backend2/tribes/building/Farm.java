package rueppellii.backend2.tribes.building;

import javax.persistence.Entity;
import java.sql.Timestamp;

@Entity
public class Farm extends Building {

    public Farm() {
        setType(BuildingType.FARM);
        setHP(1);
        setStarted_at(new Timestamp(System.currentTimeMillis()));
    }
}
