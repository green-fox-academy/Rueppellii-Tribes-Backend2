package rueppellii.backend2.tribes.building.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import rueppellii.backend2.tribes.gameUtility.timeService.TimeServiceImpl;
import rueppellii.backend2.tribes.kingdom.exception.KingdomNotFoundException;
import rueppellii.backend2.tribes.kingdom.persistence.model.Kingdom;
import rueppellii.backend2.tribes.kingdom.service.KingdomService;
import rueppellii.backend2.tribes.progression.exception.BuildingNotFoundException;
import rueppellii.backend2.tribes.progression.persistence.ProgressionModel;
import rueppellii.backend2.tribes.progression.util.ProgressionDTO;
import rueppellii.backend2.tribes.progression.util.ProgressionFactory;
import rueppellii.backend2.tribes.troop.exception.TroopNotFoundException;
import rueppellii.backend2.tribes.user.util.ErrorResponse;

import java.security.Principal;

import static rueppellii.backend2.tribes.progression.util.ProgressionFactory.makeProgressionModel;

@RestController
@RequestMapping("/api/kingdom/building")
public class BuildingController {

    private KingdomService kingdomService;
    private TimeServiceImpl timeService;

    @Autowired
    public BuildingController(KingdomService kingdomService, TimeServiceImpl timeService) {
        this.kingdomService = kingdomService;
        this.timeService = timeService;
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.OK)
    public void createBuilding(@RequestBody ProgressionDTO progressionDTO,
                               Principal principal) throws KingdomNotFoundException, TroopNotFoundException, BuildingNotFoundException {
        Kingdom kingdom = kingdomService.findByPrincipal(principal);

        timeService.refreshProgression(kingdom);

        //TODO: ResourceService will call timeService and refresh the actual resources(applicationUser)

        //TODO: PurchaseService will check if user have sufficient funds for the progression(progressionDTO.getType, applicationUser, actionCode)
        //TODO: Will return Boolean and deduct the amount(the amount is gonna be based on the type of the gameObject, whether if its create or upgrade and the level)

        //TODO: generateProgressionModel should be implemented
        ProgressionModel progressionModel = makeProgressionModel();
        progressionModel.setType(progressionDTO.getType());
        progressionModel.setTimeToCreate(timeService.calculateTimeOfBuildingCreation(kingdom));

        progressionModel.setProgressKingdom(kingdom);
        kingdom.getKingdomsProgresses().add(progressionModel);
        kingdomService.save(kingdom);
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public void upgradeBuilding(@PathVariable Long id, Principal principal) throws KingdomNotFoundException {
        Kingdom kingdom = kingdomService.findByPrincipal(principal);
        //TODO: timeService method to check the progression and update/create if time is up

        //TODO: ResourceService will call timeService and refresh the actual resources(applicationUser)

        //TODO: PurchaseService will check if user have sufficient funds for the progression(progressionDTO.getType, applicationUser, actionCode)
        //TODO: Will return Boolean and deduct the amount(the amount is gonna be based on the type of the gameObject, whether if its create or upgrade and the level)

        //TODO: generateProgressionModel should be implemented
        ProgressionModel progressionModel = makeProgressionModel();
        progressionModel.setGameObjectId(id);
        progressionModel.setTimeToCreate(timeService.calculateTimeOfBuildingUpgrade(kingdom));

        progressionModel.setProgressKingdom(kingdom);
        kingdom.getKingdomsProgresses().add(progressionModel);
        kingdomService.save(kingdom);
    }


    @ResponseBody
    @ExceptionHandler(UsernameNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ErrorResponse userNotFoundHandler(UsernameNotFoundException ex) {
        return new ErrorResponse(ex.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(KingdomNotFoundException.class)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    ErrorResponse kingdomNotFoundException(KingdomNotFoundException ex) {
        return new ErrorResponse(ex.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(TroopNotFoundException.class)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    ErrorResponse troopNotFoundException(TroopNotFoundException ex) {
        return new ErrorResponse(ex.getMessage());
    }


}
