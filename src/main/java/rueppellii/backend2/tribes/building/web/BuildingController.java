package rueppellii.backend2.tribes.building.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import rueppellii.backend2.tribes.timeService.TimeService;
import rueppellii.backend2.tribes.progression.persistence.ProgressionModel;
import rueppellii.backend2.tribes.progression.util.ProgressionDTO;
import rueppellii.backend2.tribes.user.persistence.model.ApplicationUser;
import rueppellii.backend2.tribes.user.service.ApplicationUserService;
import rueppellii.backend2.tribes.user.util.ErrorResponse;

import java.security.Principal;

@RestController
@RequestMapping("/api/kingdom/building")
public class BuildingController {

    private ApplicationUserService applicationUserService;
    private TimeService timeService;

    @Autowired
    public BuildingController(ApplicationUserService applicationUserService, TimeService timeService) {
        this.applicationUserService = applicationUserService;
        this.timeService = timeService;
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.OK)
    public void createBuilding(@RequestBody ProgressionDTO progressionDTO,
                               Principal principal) throws UsernameNotFoundException {
        ApplicationUser applicationUser = applicationUserService.findByPrincipal(principal);
        //TODO: timeService method to check the progression and update/create if time is up

        //TODO: ResourceService will call timeService and refresh the actual resources(applicationUser)

        //TODO: PurchaseService will check if user have sufficient funds for the progression(progressionDTO.getObjectToProgress, applicationUser, actionCode)
        //TODO: Will return Boolean and deduct the amount(the amount is gonna be based on the type of the gameObject, whether if its create or upgrade and the level)

        ProgressionModel progressionModel = new ProgressionModel();
        progressionModel.setObjectToProgress(progressionDTO.getObjectToProgress());
        progressionModel.setTimeToCreate(timeService.calculateTimeOfBuildingCreation(applicationUser));

        progressionModel.setProgressKingdom(applicationUser.getKingdom());
        applicationUser.getKingdom().getKingdomsProgresses().add(progressionModel);
        applicationUserService.save(applicationUser);
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public void upgradeBuilding(@PathVariable Long id, Principal principal) throws UsernameNotFoundException {
        ApplicationUser applicationUser = applicationUserService.findByPrincipal(principal);
        //TODO: timeService method to check the progression and update/create if time is up

        //TODO: ResourceService will call timeService and refresh the actual resources(applicationUser)

        //TODO: PurchaseService will check if user have sufficient funds for the progression(progressionDTO.getObjectToProgress, applicationUser, actionCode)
        //TODO: Will return Boolean and deduct the amount(the amount is gonna be based on the type of the gameObject, whether if its create or upgrade and the level)

        ProgressionModel progressionModel = new ProgressionModel();
        progressionModel.setGameObjectId(id);
        progressionModel.setTimeToCreate(timeService.calculateTimeOfBuildingUpgrade(applicationUser));

        progressionModel.setProgressKingdom(applicationUser.getKingdom());
        applicationUser.getKingdom().getKingdomsProgresses().add(progressionModel);
        applicationUserService.save(applicationUser);
    }


    @ResponseBody
    @ExceptionHandler(UsernameNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ErrorResponse userNotFoundHandler(UsernameNotFoundException ex) {
        return new ErrorResponse(ex.getMessage());
    }


}
