package rueppellii.backend2.tribes.testController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import rueppellii.backend2.tribes.kingdom.Kingdom;
import rueppellii.backend2.tribes.kingdom.KingdomService;
import rueppellii.backend2.tribes.kingdom.exception.KingdomNotValidException;
import rueppellii.backend2.tribes.security.auth.jwt.JwtAuthenticationToken;
import rueppellii.backend2.tribes.security.model.UserContext;
import rueppellii.backend2.tribes.troop.Troop;
import rueppellii.backend2.tribes.upgrade.IdDTO;
import rueppellii.backend2.tribes.upgrade.PurchaseService;

import java.security.Principal;

@RestController
@RequestMapping("/api")
public class ResourceController {

    private PurchaseService purchaseService;
    private KingdomService kingdomService;

    @Autowired
    public ResourceController(PurchaseService purchaseService, KingdomService kingdomService) {
        this.purchaseService = purchaseService;
        this.kingdomService = kingdomService;
    }

    @GetMapping("/kingdom/gold")
    public Integer showResource(Principal principal) throws Exception {
        JwtAuthenticationToken authenticationToken = (JwtAuthenticationToken) principal;
        UserContext userContext = (UserContext) authenticationToken.getPrincipal();
        String loggedInUser = userContext.getUsername();
        Kingdom kingdom = kingdomService.getKingdomByUsername(loggedInUser);
        return purchaseService.getKingdomsGoldAmount(kingdom.getId());
    }

    @PostMapping("/kingdom/troop/build")
    public Kingdom makeTroop(Principal principal) throws Exception {
        JwtAuthenticationToken authenticationToken = (JwtAuthenticationToken) principal;
        UserContext userContext = (UserContext) authenticationToken.getPrincipal();
        String loggedInUser = userContext.getUsername();
        Kingdom kingdom = kingdomService.getKingdomByUsername(loggedInUser);
        purchaseService.buyTroop(kingdom.getId());
        return kingdom;
    }

    @PostMapping("/kingdom/troop/upgrade")
    public Kingdom upgradeTroop(Principal principal, @RequestBody IdDTO idDTO) throws Exception {
        JwtAuthenticationToken authenticationToken = (JwtAuthenticationToken) principal;
        UserContext userContext = (UserContext) authenticationToken.getPrincipal();
        String loggedInUser = userContext.getUsername();
        Kingdom kingdom = kingdomService.getKingdomByUsername(loggedInUser);
        purchaseService.upgradeTroop(kingdom.getId(), idDTO.getId());
        return kingdom;
    }
}
