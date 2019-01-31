package rueppellii.backend2.tribes.kingdom;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import rueppellii.backend2.tribes.building.Building;
import rueppellii.backend2.tribes.resource.Resource;
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
    @JsonBackReference
    @OneToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "application_user_user_id")
    private ApplicationUser applicationUser;

    @JsonManagedReference
    @OneToMany(mappedBy = "buildingsKingdom", targetEntity = Building.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Building> kingdomsBuildings;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "kingdomresource", joinColumns = {@JoinColumn(name = "kingdom_id", referencedColumnName = "id")}, inverseJoinColumns = {@JoinColumn(name = "resource_id", referencedColumnName = "resource_id")})
    public List<Resource> resources;
}
