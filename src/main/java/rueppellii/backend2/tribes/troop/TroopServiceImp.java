package rueppellii.backend2.tribes.troop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rueppellii.backend2.tribes.kingdom.KingdomRepository;

@Service
public class TroopServiceImp {

    private TroopRepository troopRepository;
    private KingdomRepository kingdomRepository;

    @Autowired
    public TroopServiceImp(TroopRepository troopRepository, KingdomRepository kingdomRepository) {
        this.troopRepository = troopRepository;
        this.kingdomRepository = kingdomRepository;
    }

    public void saveTroop(Long kingdomId, Troop troop) {
        if (kingdomRepository.findById(kingdomId).isPresent()) {
                troopRepository.save(troop);
        }
    }

    public void deleteTroop(Long troop_id) {
        troopRepository.deleteById(troop_id);
    }

    public Troop findById(Long troop_id) {
        if (troopRepository.findById(troop_id).isPresent()) {
            return troopRepository.findById(troop_id).get();
        }
        return null;
    }
}