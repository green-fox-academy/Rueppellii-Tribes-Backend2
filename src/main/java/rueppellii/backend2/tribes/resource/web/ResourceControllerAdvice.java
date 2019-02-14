package rueppellii.backend2.tribes.resource.web;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import rueppellii.backend2.tribes.resource.exception.NoResourceException;
import rueppellii.backend2.tribes.user.util.ErrorResponse;

@ControllerAdvice(assignableTypes = ResourceController.class)
public class ResourceControllerAdvice {

    @ResponseBody
    @ExceptionHandler(NoResourceException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    ErrorResponse NoResourceHandler(NoResourceException ex) {
        return new ErrorResponse(ex.getMessage());
    }
}
