package rueppellii.backend2.tribes.troop.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import rueppellii.backend2.tribes.gameUtility.purchaseService.PurchaseService;
import rueppellii.backend2.tribes.kingdom.exception.KingdomNotFoundException;
import rueppellii.backend2.tribes.kingdom.persistence.model.Kingdom;
import rueppellii.backend2.tribes.kingdom.service.KingdomService;
import rueppellii.backend2.tribes.building.exception.BuildingNotFoundException;
import rueppellii.backend2.tribes.progression.exception.InvalidProgressionRequestException;
import rueppellii.backend2.tribes.progression.service.ProgressionService;
import rueppellii.backend2.tribes.resource.exception.NoResourceException;
import rueppellii.backend2.tribes.resource.service.ResourceService;
import rueppellii.backend2.tribes.troop.exception.TroopNotFoundException;
import rueppellii.backend2.tribes.troop.service.TroopService;

import java.security.Principal;

@RestController
@RequestMapping("/api/kingdom/troop")
public class TroopController {

    private KingdomService kingdomService;
    private ProgressionService progressionService;
    private PurchaseService purchaseService;
    private ResourceService resourceService;
    private TroopService troopService;

    @Autowired
    public TroopController(KingdomService kingdomService, ProgressionService progressionService,
                           PurchaseService purchaseService, ResourceService resourceService, TroopService troopService) {
        this.kingdomService = kingdomService;
        this.progressionService = progressionService;
        this.purchaseService = purchaseService;
        this.resourceService = resourceService;
        this.troopService = troopService;
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.OK)
    public void createTroop(Principal principal) throws UsernameNotFoundException,
            KingdomNotFoundException, TroopNotFoundException, BuildingNotFoundException, NoResourceException {
        Kingdom kingdom = kingdomService.findByPrincipal(principal);
        progressionService.updateProgression(kingdom);
        resourceService.updateResources(kingdom);
        purchaseService.buyTroop(kingdom.getId());
        progressionService.generateTroopCreationModel(kingdom);
    }

    @PutMapping("{level}")
    @ResponseStatus(HttpStatus.OK)
    public void upgradeTroop(@PathVariable Integer level, Principal principal)
            throws UsernameNotFoundException,KingdomNotFoundException, TroopNotFoundException,
            BuildingNotFoundException, NoResourceException, InvalidProgressionRequestException {
        Kingdom kingdom = kingdomService.findByPrincipal(principal);
        troopService.validateLevel(level);
        progressionService.updateProgression(kingdom);
        resourceService.updateResources(kingdom);
        purchaseService.upgradeTroops(troopService.getTroopsWithTheGivenLevel(level, kingdom));
        progressionService.generateTroopUpgradeModel(troopService.getTroopsWithTheGivenLevel(level, kingdom));
    }
}
