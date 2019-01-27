package rueppellii.backend2.tribes.message.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.*;

@Getter
@Setter
public class SignUpForm {
    @NotBlank
    private String username;
    @NotBlank
    private String password;
    private String kingdom;

}
