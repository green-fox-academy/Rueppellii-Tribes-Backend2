package rueppellii.backend2.tribes.testController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rueppellii.backend2.tribes.kingdom.Kingdom;
import rueppellii.backend2.tribes.kingdom.KingdomService;
import rueppellii.backend2.tribes.security.auth.jwt.JwtAuthenticationToken;
import rueppellii.backend2.tribes.security.model.UserContext;
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
        return purchaseService.getGold(kingdom.getId());
    }
}
