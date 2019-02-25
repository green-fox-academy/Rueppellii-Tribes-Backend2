package rueppellii.backend2.tribes.troop.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import rueppellii.backend2.tribes.gameUtility.gameChain.GameChainService;
import rueppellii.backend2.tribes.kingdom.exception.KingdomNotFoundException;
import rueppellii.backend2.tribes.building.exception.BuildingNotFoundException;
import rueppellii.backend2.tribes.kingdom.service.KingdomService;
import rueppellii.backend2.tribes.progression.exception.InvalidProgressionRequestException;
import rueppellii.backend2.tribes.resource.exception.NoResourceException;
import rueppellii.backend2.tribes.troop.exception.TroopNotFoundException;
import rueppellii.backend2.tribes.troop.utility.ListKingdomsTroopsDTO;

import java.security.Principal;

@RestController
@RequestMapping("/api/kingdom/troop")
public class TroopController {

    private GameChainService gameChainService;
    private KingdomService kingdomService;

    @Autowired
    public TroopController(GameChainService gameChainService, KingdomService kingdomService) {
        this.gameChainService = gameChainService;
        this.kingdomService = kingdomService;
    }

    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    public ListKingdomsTroopsDTO showTroops(Principal principal)
            throws KingdomNotFoundException {
        ListKingdomsTroopsDTO dto = new ListKingdomsTroopsDTO();
        dto.setTroops(kingdomService.findByPrincipal(principal).getKingdomsTroops());
        return dto;
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.OK)
    public void createTroop(Principal principal)
            throws UsernameNotFoundException, KingdomNotFoundException, TroopNotFoundException,
            BuildingNotFoundException, NoResourceException {
        gameChainService.createTroopGameChain(principal);
    }

    @PutMapping("{level}")
    @ResponseStatus(HttpStatus.OK)
    public void upgradeTroop(@PathVariable Integer level, Principal principal)
            throws UsernameNotFoundException, KingdomNotFoundException, TroopNotFoundException,
            BuildingNotFoundException, NoResourceException, InvalidProgressionRequestException {
        gameChainService.upgradeTroopGameChain(level, principal);
    }
}
