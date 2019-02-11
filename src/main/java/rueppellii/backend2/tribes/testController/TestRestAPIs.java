package rueppellii.backend2.tribes.testController;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import rueppellii.backend2.tribes.security.auth.jwt.JwtAuthenticationToken;
import rueppellii.backend2.tribes.security.model.UserContext;

import java.security.Principal;

@RestController
public class TestRestAPIs {

    @GetMapping("/api/test/user")
    @PreAuthorize("hasRole('USER')")
    public String userAccess() {
        return ">>> User Contents!";
    }

    @GetMapping("/api/test/showuser")
    @PreAuthorize("hasRole('USER')")
    public Object showUser(Principal principal) {
        JwtAuthenticationToken authenticationToken = (JwtAuthenticationToken) principal;
        UserContext userContext = (UserContext) authenticationToken.getPrincipal();

        return userContext.getUsername();

    }

}
