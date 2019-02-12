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
import rueppellii.backend2.tribes.troop.exception.TroopNotFoundException;
import rueppellii.backend2.tribes.troop.service.TroopService;
import rueppellii.backend2.tribes.user.util.ErrorResponse;

import java.security.Principal;

@RestController
@RequestMapping("/api/kingdom/troop")
public class TroopController {

    private KingdomService kingdomService;
    private ProgressionService progressionService;
    private PurchaseService purchaseService;
    private TroopService troopService;

    @Autowired
    public TroopController(KingdomService kingdomService, ProgressionService progressionService, PurchaseService purchaseService, TroopService troopService) {
        this.kingdomService = kingdomService;
        this.progressionService = progressionService;
        this.purchaseService = purchaseService;
        this.troopService = troopService;
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.OK)
    public void createTroop(Principal principal) throws UsernameNotFoundException,
            KingdomNotFoundException, TroopNotFoundException, BuildingNotFoundException, NoResourceException, InvalidProgressionRequestException {

        Kingdom kingdom = kingdomService.findByPrincipal(principal);
        progressionService.refreshProgression(kingdom);
        //TODO: ResourceService will call timeService and refresh the actual resources(kingdom)
        purchaseService.buyTroop(kingdom.getId());
        progressionService.generateTroopCreationModel(kingdom);
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public void upgradeTroop(@PathVariable Long troopId, Principal principal) throws UsernameNotFoundException,
            KingdomNotFoundException, TroopNotFoundException, BuildingNotFoundException, NoResourceException, InvalidProgressionRequestException {
        //TODO: validate if troop really belongs to the user who makes the request
        Kingdom kingdom = kingdomService.findByPrincipal(principal);
        progressionService.refreshProgression(kingdom);
        //TODO: ResourceService will call timeService and refresh the actual resources(kingdom)
        purchaseService.upgradeTroop(kingdom.getId(), troopId);
        progressionService.generateTroopUpgradeModel(kingdom, troopId);
    }

    @ResponseBody
    @ExceptionHandler(UsernameNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ErrorResponse userNotFoundHandler(UsernameNotFoundException ex) {
        return new ErrorResponse(ex.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(KingdomNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ErrorResponse kingdomNotFoundException(KingdomNotFoundException ex) {
        return new ErrorResponse(ex.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(TroopNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ErrorResponse troopNotFoundException(TroopNotFoundException ex)  {
        return new ErrorResponse(ex.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(BuildingNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ErrorResponse buildingNotFoundHandler(BuildingNotFoundException ex) {
        return new ErrorResponse(ex.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(NoResourceException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    ErrorResponse NoResourceHandler(NoResourceException ex) {
        return new ErrorResponse(ex.getMessage());
    }
}
