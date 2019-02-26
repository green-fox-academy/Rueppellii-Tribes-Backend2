package rueppellii.backend2.tribes.user.web;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import rueppellii.backend2.tribes.user.exceptions.UserRoleNotFoundException;
import rueppellii.backend2.tribes.user.util.ErrorResponse;
import rueppellii.backend2.tribes.user.exceptions.UserNameIsTakenException;
import rueppellii.backend2.tribes.user.util.ApplicationUserDTO;
import rueppellii.backend2.tribes.user.service.ApplicationUserService;
import rueppellii.backend2.tribes.user.util.ListUserNamesDTO;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/user")
public class ApplicationUserController {

    private ApplicationUserService applicationUserService;

    @Autowired
    public ApplicationUserController(ApplicationUserService applicationUserService) {
        this.applicationUserService = applicationUserService;
    }

    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ListUserNamesDTO getAllUserName() {
        List<String> usernames = applicationUserService.findAllUsernames();
        ListUserNamesDTO listUserNamesDTO = new ListUserNamesDTO();
        listUserNamesDTO.setUserNames(usernames);
        return listUserNamesDTO;
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.OK)
    public RegisterResponse registerUser(@RequestBody @Valid ApplicationUserDTO applicationUserDTO) throws MethodArgumentNotValidException, UserNameIsTakenException, UserRoleNotFoundException {
        return applicationUserService.registerApplicationUser(applicationUserDTO);
    }
}
