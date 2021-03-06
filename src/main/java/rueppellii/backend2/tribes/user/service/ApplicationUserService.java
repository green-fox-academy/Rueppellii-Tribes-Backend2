package rueppellii.backend2.tribes.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import rueppellii.backend2.tribes.kingdom.service.KingdomService;

import rueppellii.backend2.tribes.location.exception.CountryCodeNotValidException;
import rueppellii.backend2.tribes.location.exception.LocationIsTakenException;
import rueppellii.backend2.tribes.security.model.UserContext;
import rueppellii.backend2.tribes.user.exceptions.UserNameIsTakenException;
import rueppellii.backend2.tribes.user.exceptions.UserRoleNotFoundException;
import rueppellii.backend2.tribes.user.persistence.model.ApplicationUserRole;
import rueppellii.backend2.tribes.user.web.RegisterResponse;
import rueppellii.backend2.tribes.user.persistence.repository.ApplicationUserRepository;
import rueppellii.backend2.tribes.user.persistence.model.ApplicationUser;
import rueppellii.backend2.tribes.user.util.ApplicationUserDTO;
import rueppellii.backend2.tribes.user.util.Role;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ApplicationUserService {

    private KingdomService kingdomService;
    private ApplicationUserRepository applicationUserRepository;
    private PasswordEncoder encoder;
    private RoleService roleService;

    @Autowired
    public ApplicationUserService(KingdomService kingdomService, ApplicationUserRepository applicationUserRepository,
                                  PasswordEncoder encoder, RoleService roleService) {
        this.kingdomService = kingdomService;
        this.applicationUserRepository = applicationUserRepository;
        this.encoder = encoder;
        this.roleService = roleService;
    }

    public UserContext createUserContext(String username) {
        ApplicationUser applicationUser = findByUserName(username);

        if (applicationUser.getRoles() == null)
            throw new InsufficientAuthenticationException("User has no roles assigned");
        List<GrantedAuthority> authorities = applicationUser.getRoles().stream()
                .map(authority -> new SimpleGrantedAuthority(authority.getRoleEnum().authority()))
                .collect(Collectors.toList());

        return UserContext.create(applicationUser.getUsername(), authorities);
    }

    public ApplicationUser findByUserName(String username) throws UsernameNotFoundException {
        return applicationUserRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
    }

    public RegisterResponse registerApplicationUser(ApplicationUserDTO applicationUserDTO)
            throws UserNameIsTakenException, UserRoleNotFoundException, IOException, CountryCodeNotValidException, LocationIsTakenException {

        if (!existsByUsername(applicationUserDTO.getUsername())) {

            final ApplicationUser applicationUser = new ApplicationUser();
            //TODO this is used only for development purpose
            ApplicationUserRole applicationUserRole = new ApplicationUserRole(1L, Role.USER);
            roleService.saveRole(applicationUserRole);
            List<ApplicationUserRole> userRoles = new ArrayList<>();
            userRoles.add(roleService.findById(1L));

            applicationUser.setUsername(applicationUserDTO.getUsername());
            applicationUser.setPassword(encoder.encode(applicationUserDTO.getPassword()));
            applicationUser.setKingdom(kingdomService.createKingdom(applicationUserDTO));
            applicationUser.getKingdom().setApplicationUser(applicationUser);
            applicationUser.setRoles(userRoles);

            applicationUserRepository.save(applicationUser);

            return new RegisterResponse(applicationUser.getId(),
                    applicationUser.getUsername(),
                    applicationUser.getKingdom().getId());
        }
        throw new UserNameIsTakenException();
    }

    private Boolean existsByUsername(String username) {
        return applicationUserRepository.existsByUsername(username);
    }

    public List<String> findAllUsernames() {
        List<ApplicationUser> allUsers = applicationUserRepository.findAll();
        return allUsers.stream().map(ApplicationUser::getUsername).collect(Collectors.toList());
    }
}
