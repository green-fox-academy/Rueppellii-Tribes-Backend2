package rueppellii.backend2.tribes.building.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import rueppellii.backend2.tribes.gameUtility.purchaseService.PurchaseService;
import rueppellii.backend2.tribes.gameUtility.timeService.TimeServiceImpl;
import rueppellii.backend2.tribes.kingdom.exception.KingdomNotFoundException;
import rueppellii.backend2.tribes.kingdom.persistence.model.Kingdom;
import rueppellii.backend2.tribes.kingdom.service.KingdomService;
import rueppellii.backend2.tribes.building.exception.BuildingNotFoundException;
import rueppellii.backend2.tribes.progression.exception.InvalidProgressionRequest;
import rueppellii.backend2.tribes.progression.service.ProgressionService;
import rueppellii.backend2.tribes.progression.util.ProgressionDTO;
import rueppellii.backend2.tribes.resource.exception.NoResourceException;
import rueppellii.backend2.tribes.troop.exception.TroopNotFoundException;
import rueppellii.backend2.tribes.user.util.ErrorResponse;

import java.security.Principal;

@RestController
@RequestMapping("/api/kingdom/building")
public class BuildingController {

    private KingdomService kingdomService;
    private ProgressionService progressionService;
    private PurchaseService purchaseService;

    @Autowired
    public BuildingController(KingdomService kingdomService, ProgressionService progressionService, PurchaseService purchaseService) {
        this.kingdomService = kingdomService;
        this.progressionService = progressionService;
        this.purchaseService = purchaseService;
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.OK)
    public void createBuilding(@RequestBody ProgressionDTO progressionDTO,
                               Principal principal, BindingResult bindingResult) throws KingdomNotFoundException,
            TroopNotFoundException, BuildingNotFoundException, NoResourceException, InvalidProgressionRequest {
        progressionService.validateProgressionRequest(bindingResult, progressionDTO);
        Kingdom kingdom = kingdomService.findByPrincipal(principal);
        progressionService.refreshProgression(kingdom);
        //TODO: ResourceService will call timeService and refresh the actual resources(kingdom)
        purchaseService.buyBuilding(kingdom.getId());
        progressionService.generateBuildingCreationModel(kingdom, progressionDTO);
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public void upgradeBuilding(@PathVariable Long id, Principal principal) throws KingdomNotFoundException, TroopNotFoundException, BuildingNotFoundException, NoResourceException {
        //TODO: validate if troop really belongs to the user who makes the request
        Kingdom kingdom = kingdomService.findByPrincipal(principal);
        progressionService.refreshProgression(kingdom);
        //TODO: ResourceService will call timeService and refresh the actual resources(kingdom)
        purchaseService.upgradeBuilding(kingdom.getId(), id);
        progressionService.generateBuildingUpgradeModel(kingdom, id);
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
    ErrorResponse kingdomNotFoundHandler(KingdomNotFoundException ex) {
        return new ErrorResponse(ex.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(TroopNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ErrorResponse troopNotFoundHandler(TroopNotFoundException ex) {
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

    @ResponseBody
    @ExceptionHandler(InvalidProgressionRequest.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ErrorResponse InvalidProgressionHandler(InvalidProgressionRequest ex) {
        return new ErrorResponse(ex.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ErrorResponse InvalidProgressionEnumHandler(IllegalArgumentException ex) {
        return new ErrorResponse(ex.getMessage());
    }
}
