package rueppellii.backend2.tribes.troops;

import rueppellii.backend2.tribes.troops.models.Troop;
import rueppellii.backend2.tribes.troops.models.TroopTypes;

class TroopFactory {

   static Troop troopBuilder(TroopTypes types) {
        return types.createTroop();
    }
}
