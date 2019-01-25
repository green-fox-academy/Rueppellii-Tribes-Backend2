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
@RequestMapping("/auth")
public class AuthController {

    private ApplicationUserService applicationUserService;

    @Autowired
    public AuthController(ApplicationUserService applicationUserService) {
        this.applicationUserService = applicationUserService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody @Valid LoginForm loginForm,
                                              BindingResult bindingResult) throws Exception {

        return applicationUserService.authenticateApplicationUser(loginForm, bindingResult);
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody @Valid SignUpForm signUpForm,
                                          BindingResult bindingResult)
            throws InvalidFieldException, UserNameIsTakenException {

        return applicationUserService.saveApplicationUser(signUpForm, bindingResult);
    }
}
