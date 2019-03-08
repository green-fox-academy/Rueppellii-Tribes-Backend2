package rueppellii.backend2.tribes.user.util;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import rueppellii.backend2.tribes.user.util.Role;

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
    @NotBlank
    String location;

    String kingdomName;
    List<Role> roles;

}