package rueppellii.backend2.tribes.user;

import lombok.Getter;
import lombok.Setter;
import rueppellii.backend2.tribes.kingdom.Kingdom;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Entity
@Table(name = "users")
public class ApplicationUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;
    @NotBlank
    @NotNull
    private String username;
    @NotBlank
    @NotNull
    private String password;
    private String role;
    @OneToOne(mappedBy = "applicationUser", targetEntity = Kingdom.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Kingdom kingdom;
}
