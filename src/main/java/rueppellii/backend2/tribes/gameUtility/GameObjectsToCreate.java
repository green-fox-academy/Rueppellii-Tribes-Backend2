package rueppellii.backend2.tribes.gameUtility;

import rueppellii.backend2.tribes.building.persistence.model.*;

import static rueppellii.backend2.tribes.gameUtility.TimeConstants.TOWNHALL_UPGRADE_TIME;

public enum GameObjectsToCreate {

    TOWNHALL {
        public Long calculateTime() {
            return System.currentTimeMillis() + (TOWNHALL_UPGRADE_TIME);
        }
    }, FARM {
        public Long calculateTime() {
            return System.currentTimeMillis() + (TOWNHALL_UPGRADE_TIME );
        }
    }, MINE {
        public Long calculateTime() {
            return System.currentTimeMillis() + (TOWNHALL_UPGRADE_TIME );
        }
    }, BARRACKS {
        public Long calculateTime() {
            return System.currentTimeMillis() + (TOWNHALL_UPGRADE_TIME );
        }
    }, TROOP {
        public Long calculateTime() {
            return System.currentTimeMillis() + (TOWNHALL_UPGRADE_TIME );
        }
    };

    public Long calculateTime() {
        return null;
    }


}