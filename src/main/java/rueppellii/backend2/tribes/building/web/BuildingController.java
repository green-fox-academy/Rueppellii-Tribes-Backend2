package rueppellii.backend2.tribes.building.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import rueppellii.backend2.tribes.building.utility.BuildingDTO;
import rueppellii.backend2.tribes.building.service.BuildingService;
import rueppellii.backend2.tribes.building.persistence.model.Building;
import rueppellii.backend2.tribes.gameUtility.TimeServiceDTO;
import rueppellii.backend2.tribes.progression.persistence.ProgressionModel;
import rueppellii.backend2.tribes.progression.util.ProgressionDTO;
import rueppellii.backend2.tribes.user.persistence.model.ApplicationUser;
import rueppellii.backend2.tribes.user.service.ApplicationUserService;

import javax.servlet.http.HttpServletResponse;
import java.security.Principal;

@RestController
@RequestMapping("/api/building")
public class BuildingController {

    private ApplicationUserService applicationUserService;

    @Autowired
    public BuildingController(ApplicationUserService applicationUserService) {
        this.applicationUserService = applicationUserService;
    }

//    @PostMapping("")
//    @ResponseStatus(HttpStatus.OK)
//    public TimeServiceDTO createNewBuilding(@RequestBody ProgressionDTO progressionDTO, Principal principal) throws Exception {
//        ApplicationUser applicationUser = applicationUserService.findByPrincipal(principal);
//
//        timeServiceDTO = new TimeServiceDTO();
//        timeServiceDTO = timeService.calculateDuration(applicationUser, actionCode, progressionDTO.getObjectToProgress());
//
//        ProgressionModel progressionModel = new ProgressionModel();
//        progressionModel.setObjectToProgress(progressionDTO.getObjectToProgress());
//        progressionModel.setTimeToCreate(timeServiceDTO.getDuration());
//        if(actionCode > 0) {
//            progressionModel.setGameObjectId(timeServiceDTO.getGameObjectToProgressId());
//        }
//        progressionModel.setProgressKingdom(applicationUser.getKingdomName());
//        applicationUser.getKingdomName().getKingdomsProgresses().add(progressionModel);
//        applicationUserService.save(applicationUser);
//    }


}
