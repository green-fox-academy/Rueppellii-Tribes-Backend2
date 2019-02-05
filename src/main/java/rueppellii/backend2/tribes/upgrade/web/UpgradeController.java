package rueppellii.backend2.tribes.upgrade.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rueppellii.backend2.tribes.upgrade.util.UpgradeDTO;
import rueppellii.backend2.tribes.user.service.ApplicationUserService;

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
    public String upgrade(UpgradeDTO upgradeDTO, Principal principal) {
        String userName = applicationUserService.getUsername(principal);

        return userName;
    }


}
