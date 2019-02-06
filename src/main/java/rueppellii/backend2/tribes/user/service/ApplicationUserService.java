package rueppellii.backend2.tribes.user.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.MethodArgumentNotValidException;
import rueppellii.backend2.tribes.security.auth.jwt.JwtAuthenticationToken;
import rueppellii.backend2.tribes.security.model.UserContext;
import rueppellii.backend2.tribes.user.exceptions.UserNameIsTakenException;
import rueppellii.backend2.tribes.kingdom.Kingdom;
import rueppellii.backend2.tribes.user.persistence.model.ApplicationUserRole;
import rueppellii.backend2.tribes.user.web.RegisterResponse;
import rueppellii.backend2.tribes.user.persistence.dao.ApplicationUserRepository;
import rueppellii.backend2.tribes.user.persistence.dao.ApplicationUserRoleRepository;
import rueppellii.backend2.tribes.user.persistence.model.ApplicationUser;
import rueppellii.backend2.tribes.user.persistence.model.ApplicationUserDTO;
import rueppellii.backend2.tribes.user.util.Role;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    public void save(ApplicationUser applicationUser){
        applicationUserRepository.save(applicationUser);
    }

    public String getUsernameByPrincipal(Principal principal) {
        JwtAuthenticationToken token = (JwtAuthenticationToken) principal;
        UserContext user = (UserContext) token.getPrincipal();
        return user.getUsername();
    }

    public List<ApplicationUserDTO> getAllUser() {
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

    public UserContext createUserContext(String username) {
        ApplicationUser applicationUser = findByUserName(username);

        if (applicationUser.getRoles() == null) throw new InsufficientAuthenticationException("User has no roles assigned");
        List<GrantedAuthority> authorities = applicationUser.getRoles().stream()
                .map(authority -> new SimpleGrantedAuthority(authority.getRoleEnum().authority()))
                .collect(Collectors.toList());

        return UserContext.create(applicationUser.getUsername(), authorities);
    }

    public ApplicationUser findByUserName(String username) throws UsernameNotFoundException {
        return applicationUserRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
    }

    public Boolean existsByUsername(String username) {
        return applicationUserRepository.existsByUsername(username);
    }

    public RegisterResponse registerNewApplicationUser(ApplicationUserDTO applicationUserDTO)
            throws MethodArgumentNotValidException, UserNameIsTakenException {

        if (!existsByUsername(applicationUserDTO.getUsername())) {

            final ApplicationUser applicationUser = new ApplicationUser();
            //TODO this is used only for development purpose
            List<ApplicationUserRole> userRoles = new ArrayList<>();
            try {
                userRoles.add(applicationUserRoleRepository.findById(1L).orElseThrow(Exception::new));
            } catch (Exception e) {
                e.printStackTrace();
            }
            //TODO
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
