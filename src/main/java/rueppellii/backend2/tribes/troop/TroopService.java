package rueppellii.backend2.tribes.troop;

import rueppellii.backend2.tribes.timeService.TimeService;
import rueppellii.backend2.tribes.troop.models.Troop;

public interface TroopService extends TimeService {

    void saveTroop(TroopDTO troopDTO);
    void deleteTroop(Long troop_id);
    Troop findById(Long troop_id);
}
