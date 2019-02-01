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

    Long id;

    @NotBlank
    String username;
    @NotBlank
    String password;

    String kingdom;
    List<Role> roles;

    public ApplicationUserDTO(@NotBlank String username, @NotBlank String password, Long id, String kingdom, List<Role> roles) {
        this.roles = roles;
        this.kingdom = kingdom;
        this.id = id;
        this.username = username;
        this.password = password;
    }
}