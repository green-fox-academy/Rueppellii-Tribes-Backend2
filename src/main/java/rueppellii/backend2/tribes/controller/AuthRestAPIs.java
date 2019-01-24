package rueppellii.backend2.tribes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import rueppellii.backend2.tribes.exception.InvalidSignUpFormException;
import rueppellii.backend2.tribes.exception.UserNameIsTakenException;
import rueppellii.backend2.tribes.kingdom.Kingdom;
import rueppellii.backend2.tribes.kingdom.KingdomService;
import rueppellii.backend2.tribes.message.request.LoginForm;
import rueppellii.backend2.tribes.message.request.SignUpForm;
import rueppellii.backend2.tribes.message.response.JwtResponse;
import rueppellii.backend2.tribes.message.response.SignUpResponse;
import rueppellii.backend2.tribes.security.jwt.JwtProvider;
import rueppellii.backend2.tribes.user.ApplicationUser;
import rueppellii.backend2.tribes.user.ApplicationUserService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/auth")
public class AuthRestAPIs {

    private AuthenticationManager authenticationManager;
    private ApplicationUserService applicationUserService;
    private PasswordEncoder encoder;
    private JwtProvider jwtProvider;
    private KingdomService kingdomService;

    @Autowired
    public AuthRestAPIs(AuthenticationManager authenticationManager,
                        ApplicationUserService applicationUserService,
                        PasswordEncoder encoder, JwtProvider jwtProvider, KingdomService kingdomService) {
        this.authenticationManager = authenticationManager;
        this.applicationUserService = applicationUserService;
        this.encoder = encoder;
        this.jwtProvider = jwtProvider;
        this.kingdomService = kingdomService;
    }

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@RequestBody @Valid LoginForm loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtProvider.generateJwtToken(authentication);
        return ResponseEntity.ok(new JwtResponse(jwt));
    }

    @PostMapping("/signup")
    public ResponseEntity<SignUpResponse> registerUser(@RequestBody @Valid SignUpForm signUpRequest,
                                                       BindingResult bindingResult) throws Exception {

        if(bindingResult.hasErrors()){
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            throw new InvalidSignUpFormException(fieldErrors);
        }
        if (!(applicationUserService.existsByUsername(signUpRequest.getUsername()))) {

            Kingdom kingdom = new Kingdom();
            kingdom.setName(signUpRequest.getKingdom());
            ApplicationUser applicationUser = new ApplicationUser();
            applicationUser.setRole("ROLE_USER");
            applicationUser.setUsername(signUpRequest.getUsername());
            applicationUser.setPassword(encoder.encode(signUpRequest.getPassword()));
            kingdom.setApplicationUser(applicationUser);
            applicationUser.setKingdom(kingdom);
            applicationUserService.save(applicationUser);
            kingdomService.saveKingdom(kingdom);

            return ResponseEntity.ok(new SignUpResponse(applicationUser.getId(),
                    applicationUser.getUsername(),
                    applicationUser.getKingdom().getId()));
        }
        throw new UserNameIsTakenException("Username already taken, please choose an other one.");
    }
}
