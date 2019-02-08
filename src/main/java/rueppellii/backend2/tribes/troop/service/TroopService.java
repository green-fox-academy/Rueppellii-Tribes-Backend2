package rueppellii.backend2.tribes.troop.service;

import rueppellii.backend2.tribes.troop.utility.TroopDTO;
import rueppellii.backend2.tribes.troop.persistence.model.Troop;

public interface TroopService {

    void saveTroop(TroopDTO troopDTO);
    void deleteTroop(Long troop_id);
    Troop findById(Long troop_id);
}
