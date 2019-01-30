package rueppellii.backend2.tribes.kingdom.army.troops;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static rueppellii.backend2.tribes.kingdom.army.troops.TroopFactory.troopBuilder;

@Service
public class TroopService {

    private TroopRepository troopRepository;

    @Autowired
    public TroopService(TroopRepository troopRepository) {
        this.troopRepository = troopRepository;
    }

    public void saveAndUpdateTroop(TroopDTO troopDTO) {
        for (TroopTypes types : TroopTypes.values()) {
            if (TroopTypes.valueOf(troopDTO.getTroopType().toUpperCase().toUpperCase()).equals(types)) {
                troopRepository.save(troopBuilder(types));
            }
        }
    }
}
