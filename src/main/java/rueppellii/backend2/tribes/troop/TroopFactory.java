package rueppellii.backend2.tribes.troop;

import rueppellii.backend2.tribes.troop.models.TroopTypes;

class TroopFactory {

   static Troop troopBuilder(TroopTypes types) {
        return types.createTroop();
    }
}
