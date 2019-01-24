package rueppellii.backend2.tribes.controller;

import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import rueppellii.backend2.tribes.exception.InvalidSignUpFormException;
import rueppellii.backend2.tribes.exception.UserNameIsTakenException;
import rueppellii.backend2.tribes.message.response.ErrorResponse;
import rueppellii.backend2.tribes.exception.UserNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ResponseBody
    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ErrorResponse notFoundHandler(UserNotFoundException ex) {
        return new ErrorResponse(ex.getMessage());
    }


    @ResponseBody
    @ExceptionHandler(UserNameIsTakenException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    ErrorResponse usernameIsTakenHandler(UserNameIsTakenException ex) {
        return new ErrorResponse(ex.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(InvalidSignUpFormException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ErrorResponse invalidSignUpFormHandler(InvalidSignUpFormException ex) {
        List<FieldError> fieldErrors = ex.getFieldErrorList();
        String errors = fieldErrors.stream().map(FieldError::getField).collect(Collectors.joining(", "));
        String errorMsg = "Missing parameter(s): " + errors + "!";
        return new ErrorResponse(errorMsg);
    }



}
