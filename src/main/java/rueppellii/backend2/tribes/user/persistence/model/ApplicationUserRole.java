package rueppellii.backend2.tribes.user.persistence.model;

import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;
import rueppellii.backend2.tribes.user.util.Role;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "user_role")
@Getter
@Setter
public class ApplicationUserRole {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long role_id;

    @Enumerated(EnumType.STRING)
    private Role role;

    @ManyToMany(mappedBy = "roles")
    private List<ApplicationUser> user = new ArrayList<>();
}
