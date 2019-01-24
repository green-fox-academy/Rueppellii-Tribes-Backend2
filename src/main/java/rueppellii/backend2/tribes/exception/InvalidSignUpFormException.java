package rueppellii.backend2.tribes.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.validation.FieldError;

import java.util.List;

@Getter
@Setter
public class InvalidSignUpFormException extends Exception {

    private List<FieldError> fieldErrorList;

    public InvalidSignUpFormException(List<FieldError> fieldErrorList) {
        this.fieldErrorList = fieldErrorList;
    }
}
