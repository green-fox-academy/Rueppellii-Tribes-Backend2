package rueppellii.backend2.tribes.troop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rueppellii.backend2.tribes.kingdom.persistence.model.Kingdom;
import rueppellii.backend2.tribes.kingdom.service.KingdomService;
import rueppellii.backend2.tribes.progression.exception.InvalidProgressionRequestException;
import rueppellii.backend2.tribes.progression.persistence.ProgressionModel;
import rueppellii.backend2.tribes.troop.exception.TroopNotFoundException;
import rueppellii.backend2.tribes.troop.persistence.model.Troop;
import rueppellii.backend2.tribes.troop.persistence.repository.TroopRepository;

import static rueppellii.backend2.tribes.troop.utility.TroopFactory.*;

@Service
public class TroopService {

    private TroopRepository troopRepository;

    @Autowired
    public TroopService(TroopRepository troopRepository) {
        this.troopRepository = troopRepository;
    }

    public void saveTroop(Troop troop) {
        troopRepository.save(troop);
    }

    public void deleteTroop(Long troop_id) {
        troopRepository.deleteById(troop_id);
    }

    public Troop findById(Long id) throws TroopNotFoundException {
        return troopRepository.findById(id).orElseThrow(() -> new TroopNotFoundException("Troop not found by id: " + id));
    }

    public void createTroop(Kingdom kingdom) {
        Troop troop = makeTroop();
        troop.setTroopsKingdom(kingdom);
        troopRepository.save(troop);
    }

    public void upgradeTroop(Long troopId) throws TroopNotFoundException, InvalidProgressionRequestException {
        if (isUpgradeableTroop(troopId)) {
            Troop troop = findById(troopId);
            troop.setLevel(troop.getLevel() + 1);
            troop.setAttack(troop.getLevel() * 5);
            troop.setDefense(troop.getLevel() * 5);
            troopRepository.save(troop);
            return;
        }
        throw new InvalidProgressionRequestException("Error Archie, fix it");
    }

    private Boolean isUpgradeableTroop(Long troopId) throws TroopNotFoundException, InvalidProgressionRequestException {
        if (levelOfTroop(troopId) == 3) {
            throw new InvalidProgressionRequestException("Troop is on maximum level");
        } else if (levelOfTroop(troopId) > 3) {
            throw new InvalidProgressionRequestException("upgradeableTroop method error, troop cannot be over level 3");
        }
        return true;
    }

    private Integer levelOfTroop(Long troopId) throws TroopNotFoundException {
        Troop upgradeThisTroop = findById(troopId);
        return upgradeThisTroop.getLevel();
    }
}

