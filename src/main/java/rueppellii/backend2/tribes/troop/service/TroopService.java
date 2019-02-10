package rueppellii.backend2.tribes.troop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rueppellii.backend2.tribes.kingdom.persistence.model.Kingdom;
import rueppellii.backend2.tribes.kingdom.service.KingdomService;
import rueppellii.backend2.tribes.progression.persistence.ProgressionModel;
import rueppellii.backend2.tribes.troop.exception.TroopNotFoundException;
import rueppellii.backend2.tribes.troop.persistence.model.Troop;
import rueppellii.backend2.tribes.troop.persistence.repository.TroopRepository;

import static rueppellii.backend2.tribes.troop.utility.TroopFactory.*;

@Service
public class TroopService {

    private TroopRepository troopRepository;
    private KingdomService kingdomService;

    @Autowired
    public TroopService(TroopRepository troopRepository, KingdomService kingdomService) {
        this.troopRepository = troopRepository;
        this.kingdomService = kingdomService;
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
        Troop troop = findById(progressionModel.getGameObjectId());
        troop.setLevel(troop.getLevel() + 1);
        troopRepository.save(troop);
    }





}

