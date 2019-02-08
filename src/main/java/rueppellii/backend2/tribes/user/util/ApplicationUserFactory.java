package rueppellii.backend2.tribes.user.util;

import org.springframework.stereotype.Component;
import rueppellii.backend2.tribes.user.persistence.model.ApplicationUser;

@Component
public class ApplicationUserFactory {
    public ApplicationUser makeApplicationUser(){
        return new ApplicationUser();
    }
}
