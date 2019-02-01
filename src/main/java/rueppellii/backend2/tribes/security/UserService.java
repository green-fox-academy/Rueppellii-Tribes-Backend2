package rueppellii.backend2.tribes.security;


import rueppellii.backend2.tribes.user.persistence.model.ApplicationUser;

import java.util.Optional;


public interface UserService {
    public Optional<ApplicationUser> getByUsername(String username);
}

