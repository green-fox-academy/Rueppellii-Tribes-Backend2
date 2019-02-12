package rueppellii.backend2.tribes.user.persistence.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;
import rueppellii.backend2.tribes.kingdom.persistence.model.Kingdom;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

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

    @ManyToMany(cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    @JoinTable(
            name = "app_user_user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    @JsonManagedReference
    private List<ApplicationUserRole> roles = new ArrayList<>();

    @JsonManagedReference
    @OneToOne(mappedBy = "applicationUser", targetEntity = Kingdom.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Kingdom kingdom;

    public void setRoles(List<ApplicationUserRole> roles) {
        this.roles = roles;
    }
}
