package rueppellii.backend2.tribes.testController;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestRestAPIs {

    @GetMapping("/api/test/user")
    @PreAuthorize("hasRole('USER')")
    public String userAccess() {
        return ">>> User Contents!";
    }

}
