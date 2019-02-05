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

    public ApplicationUser findUser(String username) {
        return applicationUserRepository.findByUsername(username).get();
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
                userRoles.add(applicationUserRoleRepository.findById(1L).orElseThrow(Exception::new));
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


}
