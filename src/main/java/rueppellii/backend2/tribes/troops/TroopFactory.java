package rueppellii.backend2.tribes.troops;

public class TroopFactory {

    static Troop troopBuilder(TroopTypes types) {
        return types.createTroop();
    }
}
