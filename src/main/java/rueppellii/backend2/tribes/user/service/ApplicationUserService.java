package rueppellii.backend2.tribes.user.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.MethodArgumentNotValidException;
import rueppellii.backend2.tribes.user.exceptions.UserNameIsTakenException;
import rueppellii.backend2.tribes.kingdom.Kingdom;
import rueppellii.backend2.tribes.user.persistence.model.ApplicationUserRole;
import rueppellii.backend2.tribes.user.web.RegisterResponse;
import rueppellii.backend2.tribes.user.persistence.dao.ApplicationUserRepository;
import rueppellii.backend2.tribes.user.persistence.dao.ApplicationUserRoleRepository;
import rueppellii.backend2.tribes.user.persistence.model.ApplicationUser;
import rueppellii.backend2.tribes.user.persistence.model.ApplicationUserDTO;
import rueppellii.backend2.tribes.user.util.Role;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ApplicationUserService {

    private ApplicationUserRepository applicationUserRepository;
    private PasswordEncoder encoder;
    private ApplicationUserRoleRepository applicationUserRoleRepository;

    @Autowired
    public ApplicationUserService(ApplicationUserRepository applicationUserRepository, PasswordEncoder encoder, ApplicationUserRoleRepository applicationUserRoleRepository) {
        this.applicationUserRepository = applicationUserRepository;
        this.encoder = encoder;
        this.applicationUserRoleRepository = applicationUserRoleRepository;
    }

    public Optional<ApplicationUser> findByUserName(String username) {
        return applicationUserRepository.findByUsername(username);
    }

    public Boolean existsByUsername(String username) {
        return applicationUserRepository.existsByUsername(username);
    }

    public RegisterResponse registerNewApplicationUser(ApplicationUserDTO applicationUserDTO)
            throws MethodArgumentNotValidException, UserNameIsTakenException {

        if (!existsByUsername(applicationUserDTO.getUsername())) {

            final ApplicationUser applicationUser = new ApplicationUser();

            List<ApplicationUserRole> userRoles = new ArrayList<>();
            userRoles.add(applicationUserRoleRepository.findById(1L).get());
            applicationUser.setUsername(applicationUserDTO.getUsername());
            applicationUser.setPassword(encoder.encode(applicationUserDTO.getPassword()));
            applicationUser.setKingdom(createNewKingdomAndSetName(applicationUserDTO));
            applicationUser.getKingdom().setApplicationUser(applicationUser);
            applicationUser.setRoles(userRoles);

            applicationUserRepository.save(applicationUser);

            return new RegisterResponse(applicationUser.getId(),
                    applicationUser.getUsername(),
                    applicationUser.getKingdom().getId());
        }
        throw new UserNameIsTakenException();
    }

    private Kingdom createNewKingdomAndSetName(ApplicationUserDTO applicationUserDTO) {
        Kingdom kingdom = new Kingdom();
        if (applicationUserDTO.getKingdom().isEmpty()) {
            kingdom.setName(applicationUserDTO.getUsername() + "'s Kingdom");
        } else {
            kingdom.setName(applicationUserDTO.getKingdom());
        }
        return kingdom;
    }


}
