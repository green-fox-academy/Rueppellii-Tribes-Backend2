package rueppellii.backend2.tribes.troop.web;

import org.springframework.http.HttpStatus;
<<<<<<< HEAD
=======
import org.springframework.security.core.userdetails.UsernameNotFoundException;
>>>>>>> 1b2baebdfb63c5d32f5f4b8e742db214cc2e2922
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import rueppellii.backend2.tribes.building.exception.BuildingNotFoundException;
import rueppellii.backend2.tribes.kingdom.exception.KingdomNotFoundException;
import rueppellii.backend2.tribes.resource.exception.NoResourceException;
import rueppellii.backend2.tribes.troop.exception.TroopNotFoundException;
import rueppellii.backend2.tribes.user.util.ErrorResponse;

@ControllerAdvice(assignableTypes = TroopController.class)
public class TroopControllerAdvice {

    @ResponseBody
<<<<<<< HEAD
=======
    @ExceptionHandler(UsernameNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ErrorResponse userNotFoundHandler(UsernameNotFoundException ex) {
        return new ErrorResponse(ex.getMessage());
    }

    @ResponseBody
>>>>>>> 1b2baebdfb63c5d32f5f4b8e742db214cc2e2922
    @ExceptionHandler(KingdomNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ErrorResponse kingdomNotFoundException(KingdomNotFoundException ex) {
        return new ErrorResponse(ex.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(TroopNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ErrorResponse troopNotFoundException(TroopNotFoundException ex)  {
        return new ErrorResponse(ex.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(BuildingNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ErrorResponse buildingNotFoundHandler(BuildingNotFoundException ex) {
        return new ErrorResponse(ex.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(NoResourceException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    ErrorResponse NoResourceHandler(NoResourceException ex) {
        return new ErrorResponse(ex.getMessage());
    }
}
