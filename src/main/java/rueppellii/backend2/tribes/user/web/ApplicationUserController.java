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
import rueppellii.backend2.tribes.user.util.ListUserNamesDTO;

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

    @GetMapping("/users")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ListUserNamesDTO getAllUserName() {
        List<String> usernames = applicationUserService.findAllUsernames();
        ListUserNamesDTO listUserNamesDTO = new ListUserNamesDTO();
        listUserNamesDTO.setUserNames(usernames);
        return listUserNamesDTO;
    }

    @ResponseBody
    @ExceptionHandler(UserNameIsTakenException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    ErrorResponse usernameIsTakenHandler() {
        return new ErrorResponse("Username already taken, please choose an other one.");
    }

    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ErrorResponse invalidFieldHandler(MethodArgumentNotValidException ex) {
        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
        String errors = fieldErrors.stream().map(FieldError::getField).collect(Collectors.joining(", "));
        String errorMsg = "Missing parameter(s): " + errors + "!";
        return new ErrorResponse(errorMsg);
    }

    @ResponseBody
    @ExceptionHandler(UserRoleNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ErrorResponse userRoleNotFoundHandler(UserRoleNotFoundException ex) {
        return new ErrorResponse(ex.getMessage());
    }

}
