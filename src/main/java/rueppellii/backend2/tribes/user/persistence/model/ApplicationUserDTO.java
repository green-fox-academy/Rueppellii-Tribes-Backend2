package rueppellii.backend2.tribes.user.persistence.model;

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

    @NotBlank
    String name;
    @NotBlank
    String password;
    List<Role> roles;

    public ApplicationUserDTO(@NotBlank String name, @NotBlank String password) {
        this.name = name;
        this.password = password;
    }
}