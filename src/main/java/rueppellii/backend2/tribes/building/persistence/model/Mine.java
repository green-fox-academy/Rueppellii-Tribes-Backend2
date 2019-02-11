package rueppellii.backend2.tribes.building.persistence.model;

import rueppellii.backend2.tribes.building.utility.BuildingType;

import javax.persistence.Entity;
import java.sql.Timestamp;

@Entity
public class Mine extends Building {

    public Mine() {
        setType(BuildingType.MINE);
        setHP(5);
    }
}
