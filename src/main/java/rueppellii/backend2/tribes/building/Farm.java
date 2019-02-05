package rueppellii.backend2.tribes.building;

import javax.persistence.Entity;
import java.sql.Timestamp;
import java.util.concurrent.TimeUnit;

@Entity
public class Farm extends Building {

    public Farm() {
        setType(BuildingType.FARM);
        setHP(1);
    }
}
