package rueppellii.backend2.tribes.troop.web;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import rueppellii.backend2.tribes.troop.exception.TroopNotFoundException;
import rueppellii.backend2.tribes.user.util.ErrorResponse;

@ControllerAdvice(assignableTypes = TroopController.class)
public class TroopControllerAdvice {

    @ResponseBody
    @ExceptionHandler(TroopNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ErrorResponse troopNotFoundHandler(TroopNotFoundException ex) {
        return new ErrorResponse(ex.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(TroopNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ErrorResponse troopNotFoundException(TroopNotFoundException ex)  {
        return new ErrorResponse(ex.getMessage());
    }
}
