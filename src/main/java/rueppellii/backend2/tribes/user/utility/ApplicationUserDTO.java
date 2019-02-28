package rueppellii.backend2.tribes.user.utility;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ApplicationUserDTO {

    Long id;

    @NotBlank
    String username;
    @NotBlank
    String password;

    String kingdomName;
    List<Role> roles;

}