package rueppellii.backend2.tribes.building.persistence.model;

import rueppellii.backend2.tribes.building.utility.BuildingType;

import javax.persistence.Entity;

@Entity
public class Farm extends Building {

    public Farm() {
        setType(BuildingType.FARM);
        setHP(1);
    }
}
