package rueppellii.backend2.tribes.building.utility;

import rueppellii.backend2.tribes.building.persistence.model.Building;

public class BuildingFactory {

    public static Building makeBuilding(BuildingType type) {
        return type.buildBuilding();
    }
}
