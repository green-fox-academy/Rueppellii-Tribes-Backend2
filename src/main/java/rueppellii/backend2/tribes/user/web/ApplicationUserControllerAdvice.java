package rueppellii.backend2.tribes.user.web;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import rueppellii.backend2.tribes.user.exceptions.UserNameIsTakenException;
import rueppellii.backend2.tribes.user.exceptions.UserRoleNotFoundException;
import rueppellii.backend2.tribes.user.util.ErrorResponse;

import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice(assignableTypes = ApplicationUserController.class)
public class ApplicationUserControllerAdvice {

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
