package rueppellii.backend2.tribes.upgrade.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import rueppellii.backend2.tribes.upgrade.persistence.UpgradeModel;
import rueppellii.backend2.tribes.upgrade.util.UpgradeDTO;
import rueppellii.backend2.tribes.user.persistence.model.ApplicationUser;
import rueppellii.backend2.tribes.user.service.ApplicationUserService;
import rueppellii.backend2.tribes.user.util.ErrorResponse;

import java.security.Principal;


@RestController
@RequestMapping("/api")
public class UpgradeController {

    private ApplicationUserService applicationUserService;

    @Autowired
    public UpgradeController(ApplicationUserService applicationUserService) {
        this.applicationUserService = applicationUserService;
    }

    @PostMapping("/upgrade")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void upgrade(UpgradeDTO upgradeDTO, Principal principal) throws UsernameNotFoundException {
        String userName = applicationUserService.getUsernameByPrincipal(principal);
        ApplicationUser applicationUser = applicationUserService.findByUserName(userName);
        //TODO the second parameter for the upgrademodel comes from timeservice
        UpgradeModel upgradeModel = new UpgradeModel(upgradeDTO.getObjectToUpgrade(), 5L);
        applicationUser.getKingdom().getKingdomsUpgrades().add(upgradeModel);
    }

    @ResponseBody
    @ExceptionHandler(UsernameNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ErrorResponse userNotFoundHandler(MethodArgumentNotValidException ex) {
        return new ErrorResponse(ex.getMessage());
    }

}
