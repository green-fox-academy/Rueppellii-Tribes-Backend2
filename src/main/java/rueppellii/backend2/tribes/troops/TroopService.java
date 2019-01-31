package rueppellii.backend2.tribes.troops;

public interface TroopService {

    void saveAndUpdateTroop(TroopDTO troopDTO);
    void deleteTroop(Long troopId);
    Troop findById(Long troopId);
}
