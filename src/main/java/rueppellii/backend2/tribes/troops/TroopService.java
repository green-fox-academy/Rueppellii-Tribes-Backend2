package rueppellii.backend2.tribes.troops;

import rueppellii.backend2.tribes.troops.models.Troop;

public interface TroopService {

    void saveAndUpdateTroop(TroopDTO troopDTO);
    void deleteTroop(Long troop_id);
    Troop findById(Long troop_id);
}
