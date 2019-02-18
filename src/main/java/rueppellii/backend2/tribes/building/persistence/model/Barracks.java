package rueppellii.backend2.tribes.building.persistence.model;

import rueppellii.backend2.tribes.building.utility.BuildingType;

import javax.persistence.Entity;
import java.sql.Timestamp;

@Entity
public class Barracks extends Building {

    public Barracks() {
        setType(BuildingType.BARRACKS);
        setHP(100);
    }

    @Override
    public void upgrade() {

    }
}
