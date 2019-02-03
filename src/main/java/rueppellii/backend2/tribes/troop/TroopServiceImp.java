package rueppellii.backend2.tribes.troop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rueppellii.backend2.tribes.troop.models.Troop;
import rueppellii.backend2.tribes.troop.models.TroopTypes;

import static rueppellii.backend2.tribes.troop.TroopFactory.troopBuilder;

@Service
public class TroopServiceImp implements TroopService {

    private TroopRepository troopRepository;

    @Autowired
    public TroopServiceImp(TroopRepository troopRepository) {
        this.troopRepository = troopRepository;
    }

    @Override
    public void saveAndUpdateTroop(TroopDTO troopDTO) {
        for (TroopTypes types : TroopTypes.values()) {
            if (TroopTypes.valueOf(troopDTO.getTroopType().toUpperCase()).equals(types)) {
                troopRepository.save(troopBuilder(types));
            }
        }
    }

    @Override
    public void deleteTroop(Long troop_id) {
        troopRepository.deleteById(troop_id);
    }

    @Override
    public Troop findById(Long troop_id) {
        if (troopRepository.findById(troop_id).isPresent()) {
            return troopRepository.findById(troop_id).get();
        }
        return null;
    }
}
