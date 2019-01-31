package rueppellii.backend2.tribes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import rueppellii.backend2.tribes.exception.UserNameIsTakenException;
import rueppellii.backend2.tribes.exception.UserNotFoundException;
import rueppellii.backend2.tribes.message.request.LoginForm;
import rueppellii.backend2.tribes.message.request.SignUpForm;
import rueppellii.backend2.tribes.user.ApplicationUserService;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private ApplicationUserService applicationUserService;

    @Autowired
    public AuthController(ApplicationUserService applicationUserService) {
        this.applicationUserService = applicationUserService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody @Valid SignUpForm signUpForm)
            throws MethodArgumentNotValidException, UserNameIsTakenException {

        return ResponseEntity.ok(applicationUserService.saveApplicationUser(signUpForm));
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody @Valid LoginForm loginForm)
            throws MethodArgumentNotValidException, UserNotFoundException {
        return ResponseEntity.ok(applicationUserService.authenticateApplicationUser(loginForm));
    }
}
