package rueppellii.backend2.tribes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import rueppellii.backend2.tribes.kingdom.Kingdom;
import rueppellii.backend2.tribes.kingdom.KingdomService;
import rueppellii.backend2.tribes.message.request.LoginForm;
import rueppellii.backend2.tribes.message.request.SignUpForm;
import rueppellii.backend2.tribes.message.response.JwtResponse;
import rueppellii.backend2.tribes.security.jwt.JwtProvider;
import rueppellii.backend2.tribes.user.ApplicationUser;
import rueppellii.backend2.tribes.user.ApplicationUserService;

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
    public ResponseEntity<?> authenticateUser(@RequestBody LoginForm loginRequest) {

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
    public ResponseEntity<String> registerUser(@RequestBody SignUpForm signUpRequest) {
        if (applicationUserService.existsByUsername(signUpRequest.getUsername())) {
            return new ResponseEntity<>("Fail -> Username is already taken!",
                    HttpStatus.BAD_REQUEST);
        }

        // Creating user's account and saving new kingdom
        Kingdom kingdom = new Kingdom();
        kingdom.setName(signUpRequest.getKingdom());

        ApplicationUser applicationUser = new ApplicationUser();
        applicationUser.setUsername(signUpRequest.getUsername());
        applicationUser.setPassword(encoder.encode(signUpRequest.getPassword()));
        applicationUser.setKingdom(kingdom);
        kingdom.setApplicationUser(applicationUser);
        applicationUserService.save(applicationUser);
        kingdomService.saveKingdom(kingdom);

        return ResponseEntity.ok().body("User registered successfully!");
    }
}
