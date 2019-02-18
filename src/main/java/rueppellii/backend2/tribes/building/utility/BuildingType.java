package rueppellii.backend2.tribes.building.utility;

import rueppellii.backend2.tribes.building.persistence.model.*;

public enum BuildingType {

    TOWNHALL {
        @Override
        public Building buildBuilding() {
            return new TownHall();
        }
    }, FARM {
        @Override
        public Building buildBuilding() {
            return new Farm();
        }
    }, MINE {
        @Override
        public Building buildBuilding() {
            return new Mine();
        }
    }, BARRACKS {
        @Override
        public Building buildBuilding() {
            return new Barracks();
        }
    };

    public abstract Building buildBuilding();

    public String getName() {
        return this.name();
    }
}
