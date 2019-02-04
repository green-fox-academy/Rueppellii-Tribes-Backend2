package rueppellii.backend2.tribes.user.service;


import org.springframework.beans.factory.annotation.Autowired;
<<<<<<< HEAD
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.MethodArgumentNotValidException;
import rueppellii.backend2.tribes.exception.UserNameIsTakenException;
import rueppellii.backend2.tribes.kingdom.Kingdom;
import rueppellii.backend2.tribes.message.request.SignUpForm;
import rueppellii.backend2.tribes.message.response.SignUpResponse;
=======
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.MethodArgumentNotValidException;
import rueppellii.backend2.tribes.user.exceptions.UserNameIsTakenException;
import rueppellii.backend2.tribes.kingdom.Kingdom;
import rueppellii.backend2.tribes.user.persistence.model.ApplicationUserRole;
import rueppellii.backend2.tribes.user.web.RegisterResponse;
>>>>>>> 4fba8a431eccdaf170bf5c224994b9ad42154acf
import rueppellii.backend2.tribes.user.persistence.dao.ApplicationUserRepository;
import rueppellii.backend2.tribes.user.persistence.dao.ApplicationUserRoleRepository;
import rueppellii.backend2.tribes.user.persistence.model.ApplicationUser;
import rueppellii.backend2.tribes.user.persistence.model.ApplicationUserDTO;
import rueppellii.backend2.tribes.user.util.Role;

<<<<<<< HEAD
import java.util.Arrays;
=======
import java.util.ArrayList;
>>>>>>> 4fba8a431eccdaf170bf5c224994b9ad42154acf
import java.util.List;
import java.util.Optional;

@Service
public class ApplicationUserService {

    private ApplicationUserRepository applicationUserRepository;
    private PasswordEncoder encoder;
    private ApplicationUserRoleRepository applicationUserRoleRepository;

    @Autowired
<<<<<<< HEAD
    public ApplicationUserService(ApplicationUserRepository applicationUserRepository, PasswordEncoder encoder) {
=======
    public ApplicationUserService(ApplicationUserRepository applicationUserRepository, PasswordEncoder encoder, ApplicationUserRoleRepository applicationUserRoleRepository) {
>>>>>>> 4fba8a431eccdaf170bf5c224994b9ad42154acf
        this.applicationUserRepository = applicationUserRepository;
        this.encoder = encoder;
        this.applicationUserRoleRepository = applicationUserRoleRepository;
    }

    public List<ApplicationUserDTO> getAllUser(){
        List<ApplicationUser> allUser = applicationUserRepository.findAll();
        List<ApplicationUserDTO> allUserDTO = new ArrayList<>();

        for (ApplicationUser user : allUser) {
            ApplicationUserDTO dto = new ApplicationUserDTO();
            dto.setUsername(user.getUsername());
            dto.setKingdom(user.getKingdom().getName());
            List<Role> roles = new ArrayList<>();
            for (int i = 0; i < user.getRoles().size(); i++) {
                roles.add(user.getRoles().get(i).getRoleEnum());

            }
            dto.setRoles(roles);
            allUserDTO.add(dto);

        }
        return allUserDTO;
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
            try {
                userRoles.add(applicationUserRoleRepository.findById(2L).orElseThrow(Exception::new));
            } catch (Exception e) {
                e.printStackTrace();
            }
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

<<<<<<< HEAD
    public Optional<ApplicationUser> findByUsername(String username) {
        return (Optional<ApplicationUser>) applicationUserRepository.findByUsername(username);
    }
=======

>>>>>>> 4fba8a431eccdaf170bf5c224994b9ad42154acf
}
