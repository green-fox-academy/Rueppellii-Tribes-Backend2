package rueppellii.backend2.tribes.user.web;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import rueppellii.backend2.tribes.user.util.ErrorResponse;

@ControllerAdvice(assignableTypes = ApplicationUserController.class)
public class ApplicationUserControllerAdvice {

    @ResponseBody
    @ExceptionHandler(UsernameNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ErrorResponse userNotFoundHandler(UsernameNotFoundException ex) {
        return new ErrorResponse(ex.getMessage());
    }
}
