package rueppellii.backend2.tribes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import rueppellii.backend2.tribes.exception.InvalidFieldException;
import rueppellii.backend2.tribes.exception.UserNameIsTakenException;
import rueppellii.backend2.tribes.exception.UserNotFoundException;
import rueppellii.backend2.tribes.message.request.LoginForm;
import rueppellii.backend2.tribes.message.request.SignUpForm;
import rueppellii.backend2.tribes.user.ApplicationUserService;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthRestAPIs {

    private ApplicationUserService applicationUserService;

    @Autowired
    public AuthRestAPIs(ApplicationUserService applicationUserService) {
        this.applicationUserService = applicationUserService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody @Valid LoginForm loginForm,
                                              BindingResult bindingResult) throws Exception {
        if (bindingResult.hasErrors()) {
            throw new InvalidFieldException(bindingResult.getFieldErrors());
        }
        if (applicationUserService.existsByUsername(loginForm.getUsername())) {
            return applicationUserService.authenticateApplicationUser(loginForm);
        }
        throw new UserNotFoundException(loginForm.getUsername());
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody @Valid SignUpForm signUpForm,
                                                       BindingResult bindingResult) throws Exception {
        if (bindingResult.hasErrors()) {
            throw new InvalidFieldException(bindingResult.getFieldErrors());
        }
        if (!(applicationUserService.existsByUsername(signUpForm.getUsername()))) {
            return applicationUserService.saveApplicationUser(signUpForm);
        }
        throw new UserNameIsTakenException();
    }
}
