package rueppellii.backend2.tribes.troop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TroopServiceImp {

    private TroopRepository troopRepository;

    @Autowired
    public TroopServiceImp(TroopRepository troopRepository) {
        this.troopRepository = troopRepository;
    }

    public void saveTroop(Troop troop) {
        troopRepository.save(troop);
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

    public Troop findByIds(Long id, Long kingdomId) throws Exception {
        return troopRepository.findByIdAndKingdom_Id(id, kingdomId).orElseThrow(() -> new Exception());
    }
}