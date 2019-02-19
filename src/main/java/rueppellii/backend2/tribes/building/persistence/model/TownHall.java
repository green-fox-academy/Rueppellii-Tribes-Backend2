package rueppellii.backend2.tribes.building.persistence.model;

import rueppellii.backend2.tribes.building.utility.BuildingType;

import javax.persistence.Entity;

@Entity
public class TownHall extends Building {

    public TownHall() {
        setType(BuildingType.TOWNHALL);
        setHP(10);
    }
}
