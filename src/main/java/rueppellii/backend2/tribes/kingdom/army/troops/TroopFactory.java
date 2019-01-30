package rueppellii.backend2.tribes.kingdom.army.troops;

public class TroopFactory {

    static Troop troopBuilder(TroopTypes types) {
        return types.createTroop();
    }
}
