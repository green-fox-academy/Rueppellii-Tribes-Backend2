package rueppellii.backend2.tribes.message.request;


import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class LoginForm {
    @NotBlank
    private String username;
    @NotBlank
    private String password;

}