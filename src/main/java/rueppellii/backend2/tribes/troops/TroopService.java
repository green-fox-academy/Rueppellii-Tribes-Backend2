package rueppellii.backend2.tribes.troops;

import rueppellii.backend2.tribes.troop.TroopDTO;
import rueppellii.backend2.tribes.troop.Troop;

public interface TroopService {

    void saveTroop(TroopDTO troopDTO);
    void deleteTroop(Long troop_id);
    Troop findById(Long troop_id);
}
