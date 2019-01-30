package rueppellii.backend2.tribes.building;

import javax.persistence.Entity;
import java.sql.Timestamp;

@Entity
public class Mine extends Building {

    public Mine() {
        setType(BuildingType.MINE);
        setHP(5);
        setStarted_at(new Timestamp(System.currentTimeMillis()));
    }
}
