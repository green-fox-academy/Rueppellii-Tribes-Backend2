package rueppellii.backend2.tribes.progression.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import rueppellii.backend2.tribes.gameUtility.TimeService;
import rueppellii.backend2.tribes.gameUtility.TimeServiceDTO;
import rueppellii.backend2.tribes.progression.persistence.ProgressionModel;
import rueppellii.backend2.tribes.progression.service.ProgressionModelService;
import rueppellii.backend2.tribes.progression.util.ProgressionDTO;
import rueppellii.backend2.tribes.user.persistence.model.ApplicationUser;
import rueppellii.backend2.tribes.user.service.ApplicationUserService;
import rueppellii.backend2.tribes.user.util.ErrorResponse;

import java.security.Principal;


@RestController
@RequestMapping("/api")
public class ProgressionController {

    private ApplicationUserService applicationUserService;
    private ProgressionModelService progressionModelService;
    private TimeService timeService;
    private TimeServiceDTO timeServiceDTO;

    @Autowired
    public ProgressionController(ApplicationUserService applicationUserService, ProgressionModelService progressionModelService, TimeService timeService) {
        this.applicationUserService = applicationUserService;
        this.progressionModelService = progressionModelService;
        this.timeService = timeService;
    }

    @PostMapping("/upgrade/{actionCode}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void upgrade(@RequestBody ProgressionDTO progressionDTO, Principal principal, @PathVariable(name = "actionCode") Integer actionCode) throws UsernameNotFoundException {
        String userName = applicationUserService.getUsernameByPrincipal(principal);
        ApplicationUser applicationUser = applicationUserService.findByUserName(userName);

        //TODO TimeService method to check the progression and update/create if time is up

        //TODO ResourceService will call TimeService and refresh the actual resources(applicationUser)

        //TODO if actionCode 0 than it will create a ProgressionModel with boolean create=true otherwise hits up user database
        // and check for the gameObject based on the actionCode which is the level of the gameObject


        //TODO PurchaseService will check if user have sufficient funds for the progression(progressionDTO.getObjectToProgress, applicationUser, actionCode)
        //TODO Will return Boolean and deduct the amount(the amount is gonna be based on the type of the gameObject, whether if its create or upgrade and the level)


        //TODO  comes from TimeService which calculates the readyToProgress of ProgressionModel(applicationUser, actionCode, progressionDTO.getObjectToProgress())
        //TODO Will return a Long with the duration
        timeServiceDTO = new TimeServiceDTO();
        timeServiceDTO = timeService.calculateDuration(applicationUser, actionCode, progressionDTO.getObjectToProgress());

        ProgressionModel progressionModel = new ProgressionModel();
        progressionModel.setObjectToProgress(progressionDTO.getObjectToProgress());
        progressionModel.setTimeToCreate(timeServiceDTO.getDuration());
        if(actionCode > 0) {
            progressionModel.setGameObjectId(timeServiceDTO.getGameObjectToProgressId());
        }
        progressionModel.setProgressKingdom(applicationUser.getKingdom());
        applicationUser.getKingdom().getKingdomsProgresses().add(progressionModel);
        applicationUserService.save(applicationUser);

    }

    @ResponseBody
    @ExceptionHandler(UsernameNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ErrorResponse userNotFoundHandler(MethodArgumentNotValidException ex) {
        return new ErrorResponse(ex.getMessage());
    }

}
