package rueppellii.backend2.tribes.kingdom;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import rueppellii.backend2.tribes.troops.Troop;
import rueppellii.backend2.tribes.user.ApplicationUser;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "kingdoms")
public class Kingdom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    @NotBlank
    private String name;
    @OneToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "application_user_user_id")
    private ApplicationUser applicationUser;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "army", joinColumns = {
            @JoinColumn(name = "kingdom_id", referencedColumnName = "id")}, inverseJoinColumns = {
            @JoinColumn(name = "troop_id", referencedColumnName = "troopId")})
    private List<Troop> troop;
}
