package rueppellii.backend2.tribes.kingdom.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rueppellii.backend2.tribes.kingdom.persistence.model.Kingdom;
import rueppellii.backend2.tribes.kingdom.utility.KingdomDTO;
import rueppellii.backend2.tribes.kingdom.service.KingdomService;
import rueppellii.backend2.tribes.kingdom.exception.KingdomNotValidException;

import java.security.Principal;

@RestController
@RequestMapping("/api/kingdom")
public class KingdomController {

    private KingdomService kingdomService;

    @Autowired
    public KingdomController(KingdomService kingdomService) {
        this.kingdomService = kingdomService;
    }

    @GetMapping("")
    public KingdomDTO showKingdom(Principal principal) throws KingdomNotValidException {
        Kingdom kingdom = kingdomService.findKingdomByPrincipal(principal);
        return kingdomService.mapKingdomDTO(kingdom);
    }
}
