package rueppellii.backend2.tribes.troop.service;

import com.google.common.collect.Iterables;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rueppellii.backend2.tribes.building.persistence.model.Barracks;
import rueppellii.backend2.tribes.building.persistence.model.Building;
import rueppellii.backend2.tribes.building.persistence.model.TownHall;
import rueppellii.backend2.tribes.kingdom.exception.KingdomNotFoundException;
import rueppellii.backend2.tribes.kingdom.persistence.model.Kingdom;
import rueppellii.backend2.tribes.kingdom.service.KingdomService;
import rueppellii.backend2.tribes.troop.exception.TroopNotFoundException;
import rueppellii.backend2.tribes.troop.persistence.model.Troop;
import rueppellii.backend2.tribes.troop.persistence.repository.TroopRepository;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

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

    public void upgradeTroop(Principal principal, Integer troopLevel)
            throws KingdomNotFoundException, TroopNotFoundException {
        if (upgradeableTroops(principal, troopLevel)) {
            List<Troop> enhanceTroopsLevel = troopRepository.findAllByLevel(troopLevel);
            enhanceTroopsLevel.forEach(troop -> troop.setAttack(troop.getLevel() * 5));
            enhanceTroopsLevel.forEach(troop -> troop.setDefense(troop.getLevel() * 5));
        }
    }

    private Boolean upgradeableTroops(Principal principal, Integer troopLevel)
            throws KingdomNotFoundException, TroopNotFoundException {
        if (getBarracLevel(principal).equals(troopLevel)) {
            throw new TroopNotFoundException("This is the maximum level of troop");
        } else if(getBarracLevel(principal).equals(troopLevel) && getBarracLevel(principal).equals(3)) {
            throw  new TroopNotFoundException("Upgrade Barrack first or call Archie :)");
        }
        return true;
    }

    private Integer getBarracLevel(Principal principal) throws KingdomNotFoundException {
        List<Building> buildingsOfKingdom = kingdomService.findByPrincipal(principal).getKingdomsBuildings();
        return Iterables.getOnlyElement(buildingsOfKingdom
                .stream()
                .filter(building -> building instanceof Barracks)
                .collect(Collectors.toList())).getLevel();
    }
}
