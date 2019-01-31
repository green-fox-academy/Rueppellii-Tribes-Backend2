package rueppellii.backend2.tribes.troops;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rueppellii.backend2.tribes.troops.models.Troop;
import rueppellii.backend2.tribes.troops.models.TroopTypes;

import static rueppellii.backend2.tribes.troops.TroopFactory.troopBuilder;

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
    public void deleteTroop(Long troopId) {
        troopRepository.deleteById(troopId);
    }

    @Override
    public Troop findById(Long troopId) {
        if (troopRepository.findById(troopId).isPresent()) {
            return troopRepository.findById(troopId).get();
        } else {
            return null;
        }
    }
}
