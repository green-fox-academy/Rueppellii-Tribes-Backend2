package rueppellii.backend2.tribes.user.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.MethodArgumentNotValidException;
import rueppellii.backend2.tribes.exception.UserNameIsTakenException;
import rueppellii.backend2.tribes.exception.UserNotFoundException;
import rueppellii.backend2.tribes.kingdom.Kingdom;
import rueppellii.backend2.tribes.kingdom.KingdomService;
import rueppellii.backend2.tribes.message.request.LoginForm;
import rueppellii.backend2.tribes.message.request.SignUpForm;
import rueppellii.backend2.tribes.message.response.JwtResponse;
import rueppellii.backend2.tribes.message.response.SignUpResponse;
import rueppellii.backend2.tribes.user.persistence.dao.ApplicationUserRepository;
import rueppellii.backend2.tribes.user.persistence.model.ApplicationUser;
import rueppellii.backend2.tribes.user.persistence.model.ApplicationUserRole;
import rueppellii.backend2.tribes.user.util.Role;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class ApplicationUserService {

    private ApplicationUserRepository applicationUserRepository;
    private PasswordEncoder encoder;

    @Autowired
    public ApplicationUserService(ApplicationUserRepository applicationUserRepository, PasswordEncoder encoder, AuthenticationManager authenticationManager) {
        this.applicationUserRepository = applicationUserRepository;
        this.encoder = encoder;
    }

    public Optional<ApplicationUser> findByUserName(String username) {
        return applicationUserRepository.findByUsername(username);
    }

    public Boolean existsByUsername(String username) {
        return applicationUserRepository.existsByUsername(username);
    }

    public SignUpResponse saveApplicationUser(SignUpForm signUpForm)
            throws MethodArgumentNotValidException, UserNameIsTakenException {

        ApplicationUserRole applicationUserRole = new ApplicationUserRole();
        applicationUserRole.setRole(Role.USER);

        List<ApplicationUserRole> applicationUserRoles = Arrays.asList(applicationUserRole);

        if (!existsByUsername(signUpForm.getUsername())) {

            ApplicationUser applicationUser = new ApplicationUser();
            applicationUser.setRoles(applicationUserRoles);
            applicationUser.setUsername(signUpForm.getUsername());
            applicationUser.setPassword(encoder.encode(signUpForm.getPassword()));
            applicationUser.setKingdom(createNewKingdomAndSetName(signUpForm));
            applicationUser.getKingdom().setApplicationUser(applicationUser);
            applicationUserRepository.save(applicationUser);

            return new SignUpResponse(applicationUser.getId(),
                    applicationUser.getUsername(),
                    applicationUser.getKingdom().getId());
        }
        throw new UserNameIsTakenException();
    }

    private Kingdom createNewKingdomAndSetName(SignUpForm signUpForm) {
        Kingdom kingdom = new Kingdom();
        if (signUpForm.getKingdom().isEmpty()) {
            kingdom.setName(signUpForm.getUsername() + "'s Kingdom");
        } else {
            kingdom.setName(signUpForm.getKingdom());
        }
        return kingdom;
    }

}
