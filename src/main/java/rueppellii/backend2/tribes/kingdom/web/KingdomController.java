package rueppellii.backend2.tribes.kingdom.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import rueppellii.backend2.tribes.kingdom.utility.KingdomListWithLocationDTO;
import rueppellii.backend2.tribes.gameUtility.gameChainService.GameChainService;
import rueppellii.backend2.tribes.kingdom.persistence.model.Kingdom;
import rueppellii.backend2.tribes.kingdom.utility.KingdomDTO;
import rueppellii.backend2.tribes.kingdom.service.KingdomService;
import rueppellii.backend2.tribes.kingdom.exception.KingdomNotFoundException;
import rueppellii.backend2.tribes.building.exception.BuildingNotFoundException;
import rueppellii.backend2.tribes.kingdom.utility.KingdomNameDTO;
import rueppellii.backend2.tribes.troop.exception.TroopNotFoundException;

import java.security.Principal;

@RestController
@RequestMapping("/api/kingdom")
public class KingdomController {

    private KingdomService kingdomService;
    private GameChainService gameChainService;

    @Autowired
    public KingdomController(KingdomService kingdomService, GameChainService gameChainService) {
        this.kingdomService = kingdomService;
        this.gameChainService = gameChainService;
    }

    @GetMapping("")
    public KingdomDTO showKingdom(Principal principal)
            throws KingdomNotFoundException, TroopNotFoundException, BuildingNotFoundException {
        Kingdom kingdom = kingdomService.findByPrincipal(principal);
        gameChainService.updateGameAssets(kingdom);
        return kingdomService.mapKingdomDTO(kingdom);
    }

    @PutMapping("")
    @ResponseStatus(HttpStatus.OK)
    public void resetKingdomsName(@RequestBody KingdomNameDTO kingdomNameDTO, Principal principal)
            throws KingdomNotFoundException {
        Kingdom kingdom = kingdomService.findByPrincipal(principal);
        kingdom.setName(kingdomNameDTO.getName());
        kingdomService.save(kingdom);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public KingdomDTO showOtherKingdom(@PathVariable Long id)
            throws KingdomNotFoundException {
        return kingdomService.findOtherKingdom(id);
    }
  
    @GetMapping("/map")
    @ResponseStatus(HttpStatus.OK)
    public KingdomListWithLocationDTO showLocation() {
        return kingdomService.listKingdomsWithLocation();
    }
}