package rueppellii.backend2.tribes.kingdom.web;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import rueppellii.backend2.tribes.kingdom.exception.KingdomNotFoundException;
import rueppellii.backend2.tribes.user.util.ErrorResponse;

@ControllerAdvice(assignableTypes = KingdomController.class)
public class KingdomControllerAdvice {

    @ResponseBody
    @ExceptionHandler(KingdomNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ErrorResponse kingdomNotFoundException(KingdomNotFoundException ex) {
        return new ErrorResponse(ex.getMessage());
    }
}
