package rueppellii.backend2.tribes.building.persistence.model;

import rueppellii.backend2.tribes.building.utility.BuildingType;

import javax.persistence.Entity;

@Entity
public class Barracks extends Building {

    public Barracks() {
        setType(BuildingType.BARRACKS);
        setHP(100);
    }
}
