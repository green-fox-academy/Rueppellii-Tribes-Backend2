package rueppellii.backend2.tribes.kingdom.utility;

import rueppellii.backend2.tribes.kingdom.persistence.model.Kingdom;

public class KingdomFactory {
    public static Kingdom makeKingdom() {
        return new Kingdom();
    }
}
