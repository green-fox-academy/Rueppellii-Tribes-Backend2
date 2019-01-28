package rueppellii.backend2.tribes.user;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
import rueppellii.backend2.tribes.security.jwt.JwtProvider;

@Service
public class ApplicationUserService {

    private ApplicationUserRepository userRepository;
    private PasswordEncoder encoder;
    private JwtProvider jwtProvider;
    private AuthenticationManager authenticationManager;

    @Autowired
    public ApplicationUserService(ApplicationUserRepository userRepository, PasswordEncoder encoder, KingdomService kingdomService, JwtProvider jwtProvider, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.encoder = encoder;
        this.jwtProvider = jwtProvider;
        this.authenticationManager = authenticationManager;
    }

    public Boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    public SignUpResponse saveApplicationUser(SignUpForm signUpForm)
            throws MethodArgumentNotValidException, UserNameIsTakenException {

        if (!(existsByUsername(signUpForm.getUsername()))) {

            ApplicationUser applicationUser = new ApplicationUser();
            applicationUser.setRole("ROLE_USER");
            applicationUser.setUsername(signUpForm.getUsername());
            applicationUser.setPassword(encoder.encode(signUpForm.getPassword()));
            applicationUser.setKingdom(createNewKingdom(signUpForm));
            applicationUser.getKingdom().setApplicationUser(applicationUser);
            userRepository.save(applicationUser);

            return new SignUpResponse(applicationUser.getId(),
                    applicationUser.getUsername(),
                    applicationUser.getKingdom().getId());
        }
        throw new UserNameIsTakenException();
    }

    private String kingdomNameValidation(String kingdomName, String userName) {
        if (kingdomName.isEmpty()) {
            return userName + "'s Kingdom";
        }
        return kingdomName;
    }

    private Kingdom createNewKingdom(SignUpForm signUpForm) {
        Kingdom kingdom = new Kingdom();
        kingdom.setName(kingdomNameValidation(signUpForm.getKingdom(), signUpForm.getUsername()));
        return kingdom;
    }

    public JwtResponse authenticateApplicationUser(LoginForm loginForm)
            throws MethodArgumentNotValidException, UserNotFoundException {

        if (existsByUsername(loginForm.getUsername())) {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginForm.getUsername(),
                            loginForm.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtProvider.generateJwtToken(authentication);
            return new JwtResponse(jwt);
        }
        throw new UserNotFoundException(loginForm.getUsername());
    }
}
