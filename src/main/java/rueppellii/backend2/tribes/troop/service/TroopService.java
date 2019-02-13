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

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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

    public void upgradeTroop(ProgressionModel progressionModel) throws TroopNotFoundException {
        Troop troop = findById(progressionModel.getId());
        troop.setLevel(troop.getLevel() + 1);
        troopRepository.save(troop);
    }

    public List<Troop> getKingfomTroops(Kingdom kingdom) {
        List<Troop> troopsOfKingdom = kingdom.getKingdomsTroops();
        return troopsOfKingdom;
    }

    private Boolean validateLevel(Integer troopLevel) throws InvalidProgressionRequestException {
        if (troopLevel >= 3) {
            throw new InvalidProgressionRequestException("Troops cannot be upgraded over level 3");
        }
        return true;
    }

    private List<Troop> getTroopsWithTheGivenLevel(Integer troopLevel, Kingdom kingdom) {
        return troopRepository.findAllByLevelAndAndTroopsKingdom(troopLevel, kingdom);
    }

    public void enhanceValidTroopsLevel(Integer troopLevel, Kingdom kingdom) throws InvalidProgressionRequestException {
        if (validateLevel(troopLevel)) {
            List<Troop> enhanceTroopsLevel = getTroopsWithTheGivenLevel(troopLevel, kingdom);
            enhanceTroopsLevel.forEach(troop -> troop.setAttack(5 ^ troop.getLevel()));
            enhanceTroopsLevel.forEach(troop -> troop.setDefense(5 ^ troop.getLevel()));
        }
    }

}

