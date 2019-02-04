package rueppellii.backend2.tribes.troop;

import rueppellii.backend2.tribes.troop.models.Troop;

public interface TroopService {

    void saveTroop(TroopDTO troopDTO);
    void deleteTroop(Long troop_id);
    Troop findById(Long troop_id);
}
