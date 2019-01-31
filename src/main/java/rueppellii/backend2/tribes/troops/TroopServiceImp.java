package rueppellii.backend2.tribes.troops;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static rueppellii.backend2.tribes.troops.TroopFactory.troopBuilder;

@Service
public class TroopServiceImp implements TroopService {

    private TroopRepository troopRepository;

    @Autowired
    public TroopServiceImp(TroopRepository troopRepository) {
        this.troopRepository = troopRepository;
    }

    public void saveAndUpdateTroop(TroopDTO troopDTO) {
        for (TroopTypes types : TroopTypes.values()) {
            if (TroopTypes.valueOf(troopDTO.getTroopType().toUpperCase().toUpperCase()).equals(types)) {
                troopRepository.save(troopBuilder(types));
            }
        }
    }

    public void deleteTroop(Long troopId) {
        troopRepository.deleteById(troopId);
    }

    public Troop findById(Long troopId) {
        if (troopRepository.findById(troopId).isPresent()) {
            return troopRepository.findById(troopId).get();
        } else {
            return null;
        }
    }
}
