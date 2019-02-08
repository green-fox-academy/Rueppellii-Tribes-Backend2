package rueppellii.backend2.tribes.testController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import rueppellii.backend2.tribes.kingdom.Kingdom;
import rueppellii.backend2.tribes.kingdom.KingdomService;
import rueppellii.backend2.tribes.resource.ResourceServiceImp;
import rueppellii.backend2.tribes.security.auth.jwt.JwtAuthenticationToken;
import rueppellii.backend2.tribes.security.model.UserContext;
import rueppellii.backend2.tribes.troop.Troop;
import rueppellii.backend2.tribes.troop.TroopServiceImp;
import rueppellii.backend2.tribes.upgrade.PurchaseService;

import java.security.Principal;

@RestController
@RequestMapping("/api")
public class ResourceController {

    private PurchaseService purchaseService;
    private KingdomService kingdomService;
    private TroopServiceImp troopServiceImp;
    private ResourceServiceImp resourceServiceImp;

    @Autowired
    public ResourceController(PurchaseService purchaseService, KingdomService kingdomService, TroopServiceImp troopServiceImp, ResourceServiceImp resourceServiceImp) {
        this.purchaseService = purchaseService;
        this.kingdomService = kingdomService;
        this.troopServiceImp = troopServiceImp;
        this.resourceServiceImp = resourceServiceImp;
    }

    @GetMapping("/kingdom/gold")
    public Integer showResource(Principal principal) throws Exception {
        JwtAuthenticationToken authenticationToken = (JwtAuthenticationToken) principal;
        UserContext userContext = (UserContext) authenticationToken.getPrincipal();
        String loggedInUser = userContext.getUsername();
        Kingdom kingdom = kingdomService.getKingdomByUsername(loggedInUser);
        return purchaseService.getGold(kingdom.getId());
    }

    @PostMapping("/troop/create")
    @ResponseStatus(HttpStatus.OK)
    public void createNewTroop(@RequestBody Troop troop, Kingdom kingdom, Long kingdomId) throws Exception {
        int numberOfTroops = kingdom.getTroops().size();
        troopServiceImp.saveTroop(troop);
        int updatedTroops = numberOfTroops + 1;
        resourceServiceImp.updateFoodPerMinuteBasedOnTroop(kingdom, kingdomId);

    }
}
