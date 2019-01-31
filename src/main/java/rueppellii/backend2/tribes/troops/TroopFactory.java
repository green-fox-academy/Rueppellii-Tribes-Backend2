package rueppellii.backend2.tribes.kingdom.troops;

public class TroopFactory {

    static Troop troopBuilder(TroopTypes types) {
        return types.createTroop();
    }
}
