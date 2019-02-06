package rueppellii.backend2.tribes.troop;


import rueppellii.backend2.tribes.troop.models.Troop;
import rueppellii.backend2.tribes.troop.models.TroopTypes;

public interface TroopService {

    void saveTroop(TroopTypes troopType);
    void deleteTroop(Long troop_id);
    Troop findById(Long troop_id);
}
