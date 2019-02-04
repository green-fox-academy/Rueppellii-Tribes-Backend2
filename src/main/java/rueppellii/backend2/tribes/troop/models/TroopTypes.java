package rueppellii.backend2.tribes.troop.models;

public enum TroopTypes {
    GUARD {
        public Troop createTroop() {
            return new GuardTroop();
        }
    },
    WARRIOR {
        public Troop createTroop() {
            return new WarriorTroop();
        }
    };

    public Troop createTroop() {
        return null;
    }
}
