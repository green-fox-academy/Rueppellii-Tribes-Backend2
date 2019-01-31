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

    private ApplicationUserRepository applicationUserRepository;
    private PasswordEncoder encoder;
    private JwtProvider jwtProvider;
    private AuthenticationManager authenticationManager;

    @Autowired
    public ApplicationUserService(ApplicationUserRepository applicationUserRepository, PasswordEncoder encoder, KingdomService kingdomService, JwtProvider jwtProvider, AuthenticationManager authenticationManager) {
        this.applicationUserRepository = applicationUserRepository;
        this.encoder = encoder;
        this.jwtProvider = jwtProvider;
        this.authenticationManager = authenticationManager;
    }

    public Boolean existsByUsername(String username) {
        return applicationUserRepository.existsByUsername(username);
    }

    public SignUpResponse saveApplicationUser(SignUpForm signUpForm)
            throws MethodArgumentNotValidException, UserNameIsTakenException {

        if (!(existsByUsername(signUpForm.getUsername()))) {

            ApplicationUser applicationUser = new ApplicationUser();
            applicationUser.setRole("ROLE_USER");
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

    public ApplicationUser findUserByName(String name) {
        return applicationUserRepository.findByUsername(name).get();
    }
}
