package rueppellii.backend2.tribes.kingdom.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rueppellii.backend2.tribes.kingdom.persistence.model.Kingdom;
import rueppellii.backend2.tribes.kingdom.utility.KingdomDTO;
import rueppellii.backend2.tribes.kingdom.service.KingdomService;
import rueppellii.backend2.tribes.kingdom.exception.KingdomNotFoundException;
import rueppellii.backend2.tribes.building.exception.BuildingNotFoundException;
import rueppellii.backend2.tribes.progression.service.ProgressionService;
import rueppellii.backend2.tribes.troop.exception.TroopNotFoundException;

import java.security.Principal;

@RestController
@RequestMapping("/api/kingdom")
public class KingdomController {

    private KingdomService kingdomService;
    private ProgressionService progressionService;


    @Autowired
    public KingdomController(KingdomService kingdomService, ProgressionService progressionService) {
        this.kingdomService = kingdomService;
        this.progressionService = progressionService;
    }

    @GetMapping("")
    public KingdomDTO showKingdom(Principal principal) throws KingdomNotFoundException, TroopNotFoundException, BuildingNotFoundException {
        Kingdom kingdom = kingdomService.findByPrincipal(principal);
        progressionService.refreshProgression(kingdom);
        return kingdomService.mapKingdomDTO(kingdom);
    }
}
