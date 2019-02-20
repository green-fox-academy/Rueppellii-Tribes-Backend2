package rueppellii.backend2.tribes.troop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rueppellii.backend2.tribes.kingdom.persistence.model.Kingdom;
import rueppellii.backend2.tribes.progression.exception.InvalidProgressionRequestException;
import rueppellii.backend2.tribes.progression.persistence.ProgressionModel;
import rueppellii.backend2.tribes.resource.service.ResourceService;
import rueppellii.backend2.tribes.troop.exception.TroopNotFoundException;
import rueppellii.backend2.tribes.troop.persistence.model.Troop;
import rueppellii.backend2.tribes.troop.persistence.repository.TroopRepository;

import java.util.List;
import java.util.stream.Collectors;

import static rueppellii.backend2.tribes.troop.utility.TroopFactory.*;

@Service
public class TroopService {

    private TroopRepository troopRepository;
    private ResourceService resourceService;

    @Autowired
    public TroopService(TroopRepository troopRepository, ResourceService resourceService) {
        this.troopRepository = troopRepository;
        this.resourceService = resourceService;
    }

    public Troop findById(Long id) throws TroopNotFoundException {
        return troopRepository.findById(id).orElseThrow(() -> new TroopNotFoundException("Troop not found by id: " + id));
    }

    public void createTroop(Kingdom kingdom) {
        Troop troop = makeTroop();
        resourceService.feedTheTroop(kingdom);
        troop.setTroopsKingdom(kingdom);
        troopRepository.save(troop);
    }

    public void upgradeTroop(ProgressionModel progressionModel) throws TroopNotFoundException {
        Troop troop = findById(progressionModel.getGameObjectId());
        troop.setAttack((int) Math.pow(5, troop.getLevel()));
        troop.setDefense((int) Math.pow(5, troop.getLevel()));
        troop.setLevel(troop.getLevel() + 1);
        troopRepository.save(troop);
    }

<<<<<<< HEAD
    //TODO get level: validate level, find kingdom troops.
=======
>>>>>>> 1b2baebdfb63c5d32f5f4b8e742db214cc2e2922
    private List<Troop> getKingdomTroops(Kingdom kingdom) {
        return kingdom.getKingdomsTroops();
    }

    public void validateUpgradeTroopRequest(Integer troopLevel, Kingdom kingdom) throws InvalidProgressionRequestException, TroopNotFoundException {
<<<<<<< HEAD
        if(troopLevel >= 3) {
            throw new InvalidProgressionRequestException("Troops cannot be upgraded to level " + (troopLevel + 1));
        }
        if(troopLevel > 0 && getTroopsWithTheGivenLevel(troopLevel, kingdom).size() == 0) {
            throw new TroopNotFoundException("no troops with the given level found");
        }
    }

    public List<Troop> getTroopsWithTheGivenLevel(Integer troopLevel, Kingdom kingdom) {
        return kingdom.getKingdomsTroops()
                .stream()
=======
        if(getTroopsWithTheGivenLevel(troopLevel, kingdom).size() == 0) {
            throw new TroopNotFoundException("Troops not found with the given level!");
        }
        if(troopLevel == 3) {
            throw new InvalidProgressionRequestException("Troops has reached the maximum level!");
        }

    }

    public List<Troop> getTroopsWithTheGivenLevel(Integer troopLevel, Kingdom kingdom) {
        return getKingdomTroops(kingdom).stream()
>>>>>>> 1b2baebdfb63c5d32f5f4b8e742db214cc2e2922
                .filter(t -> t.getLevel().equals(troopLevel))
                .collect(Collectors.toList());
    }
}

