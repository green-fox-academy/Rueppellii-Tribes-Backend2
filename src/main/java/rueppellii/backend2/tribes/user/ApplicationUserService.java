package rueppellii.backend2.tribes.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import rueppellii.backend2.tribes.exception.InvalidFieldException;
import rueppellii.backend2.tribes.exception.UserNameIsTakenException;
import rueppellii.backend2.tribes.exception.UserNotFoundException;
import rueppellii.backend2.tribes.kingdom.Kingdom;
import rueppellii.backend2.tribes.kingdom.KingdomService;
import rueppellii.backend2.tribes.message.request.LoginForm;
import rueppellii.backend2.tribes.message.request.SignUpForm;
import rueppellii.backend2.tribes.message.response.JwtResponse;
import rueppellii.backend2.tribes.message.response.SignUpResponse;
import rueppellii.backend2.tribes.security.jwt.JwtProvider;

import java.util.Optional;

@Service
public class ApplicationUserService {

    private ApplicationUserRepository userRepository;
    private PasswordEncoder encoder;
    private KingdomService kingdomService;
    private JwtProvider jwtProvider;
    private AuthenticationManager authenticationManager;

    @Autowired
    public ApplicationUserService(ApplicationUserRepository userRepository, PasswordEncoder encoder, KingdomService kingdomService, JwtProvider jwtProvider, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.encoder = encoder;
        this.kingdomService = kingdomService;
        this.jwtProvider = jwtProvider;
        this.authenticationManager = authenticationManager;
    }

    public Optional<ApplicationUser> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public Boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    public ResponseEntity<?> saveApplicationUser(SignUpForm signUpForm, BindingResult bindingResult)
            throws InvalidFieldException, UserNameIsTakenException {

        if (bindingResult.hasErrors()) {
            throw new InvalidFieldException(bindingResult.getFieldErrors());
        }
        if (!(existsByUsername(signUpForm.getUsername()))) {

            Kingdom kingdom = new Kingdom();
            kingdom.setName(kingdomNameValidation(signUpForm.getKingdom(), signUpForm.getUsername()));
            ApplicationUser applicationUser = new ApplicationUser();
            applicationUser.setRole("ROLE_USER");
            applicationUser.setUsername(signUpForm.getUsername());
            applicationUser.setPassword(encoder.encode(signUpForm.getPassword()));
            kingdom.setApplicationUser(applicationUser);
            applicationUser.setKingdom(kingdom);
            userRepository.save(applicationUser);
            kingdomService.saveKingdom(kingdom);

            return ResponseEntity.ok(new SignUpResponse(applicationUser.getId(),
                    applicationUser.getUsername(),
                    applicationUser.getKingdom().getId()));
        }
        throw new UserNameIsTakenException();
    }

    private String kingdomNameValidation(String kingdomName, String userName) {
        if (kingdomName.isEmpty()) {
            return userName + "'s Kingdom";
        }
        return kingdomName;
    }

    public ResponseEntity<?> authenticateApplicationUser(LoginForm loginForm, BindingResult bindingResult)
            throws InvalidFieldException, UserNotFoundException {

        if (bindingResult.hasErrors()) {
            throw new InvalidFieldException(bindingResult.getFieldErrors());
        }
        if (existsByUsername(loginForm.getUsername())) {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginForm.getUsername(),
                            loginForm.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtProvider.generateJwtToken(authentication);
            return ResponseEntity.ok(new JwtResponse(jwt));
        }
        throw new UserNotFoundException(loginForm.getUsername());
    }
}
