package rueppellii.backend2.tribes.security.endpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rueppellii.backend2.tribes.security.auth.jwt.JwtAuthenticationToken;
import rueppellii.backend2.tribes.security.model.UserContext;
import rueppellii.backend2.tribes.user.persistence.model.ApplicationUser;
import rueppellii.backend2.tribes.user.service.ApplicationUserService;

import java.security.Principal;

@RestController
@RequestMapping("/api/auth")
public class FrontendAuthController {

    private ApplicationUserService applicationUserService;

    @Autowired
    public FrontendAuthController(ApplicationUserService applicationUserService) {
        this.applicationUserService = applicationUserService;
    }

    @GetMapping("")
    public UserIdDTO auth(Principal principal){
        JwtAuthenticationToken authenticationToken = (JwtAuthenticationToken) principal;
        UserContext userContext = (UserContext) authenticationToken.getPrincipal();
        String loggedInUser = userContext.getUsername();
        ApplicationUser applicationUser = applicationUserService.findByUserName(loggedInUser);
        UserIdDTO userIdDTO = new UserIdDTO();
        userIdDTO.setId(applicationUser.getId());
        return userIdDTO;
    }

}
