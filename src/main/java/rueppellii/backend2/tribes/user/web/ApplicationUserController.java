package rueppellii.backend2.tribes.user.web;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import rueppellii.backend2.tribes.user.exceptions.UserRoleNotFoundException;
import rueppellii.backend2.tribes.user.util.ErrorResponse;
import rueppellii.backend2.tribes.user.exceptions.UserNameIsTakenException;
import rueppellii.backend2.tribes.user.util.ApplicationUserDTO;
import rueppellii.backend2.tribes.user.service.ApplicationUserService;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/user")
public class ApplicationUserController {

    private ApplicationUserService applicationUserService;

    @Autowired
    public ApplicationUserController(ApplicationUserService applicationUserService) {
        this.applicationUserService = applicationUserService;
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.OK)
    public RegisterResponse registerUser(@RequestBody @Valid ApplicationUserDTO applicationUserDTO)
            throws MethodArgumentNotValidException, UserNameIsTakenException, UserRoleNotFoundException {
        return applicationUserService.registerApplicationUser(applicationUserDTO);
    }

    @GetMapping("")
    @PreAuthorize("hasRole('ADMIN')")
    public List<ApplicationUserDTO> getUsers() {
        return applicationUserService.getAllUsers();
    }
}
