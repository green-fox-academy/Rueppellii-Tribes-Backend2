package rueppellii.backend2.tribes.kingdom.persistence.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;

import rueppellii.backend2.tribes.building.persistence.model.*;
import rueppellii.backend2.tribes.building.service.BuildingService;
import rueppellii.backend2.tribes.kingdom.service.KingdomService;
import rueppellii.backend2.tribes.resource.presistence.model.Resource;
import rueppellii.backend2.tribes.progression.persistence.ProgressionModel;
import rueppellii.backend2.tribes.resource.presistence.model.Food;
import rueppellii.backend2.tribes.resource.presistence.model.Gold;
import rueppellii.backend2.tribes.resource.service.ResourceService;
import rueppellii.backend2.tribes.troop.persistence.model.Troop;
import rueppellii.backend2.tribes.user.persistence.model.ApplicationUser;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "kingdoms")
public class Kingdom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;

    @JsonBackReference
    @OneToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "application_user_user_id")
    private ApplicationUser applicationUser;

    @JsonManagedReference
    @OneToMany(mappedBy = "troopsKingdom", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Troop> kingdomsTroops;

    @JsonManagedReference
    @OneToMany(mappedBy = "resourcesKingdom", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    public List<Resource> kingdomsResources;

    @JsonManagedReference
    @OneToMany(mappedBy = "buildingsKingdom", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Building> kingdomsBuildings;

    @JsonManagedReference
    @OneToMany(mappedBy = "progressKingdom", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ProgressionModel> kingdomsProgresses;

    public Kingdom() {
        kingdomsBuildings = BuildingService.starterKit(this);
        kingdomsResources = ResourceService.starterKit(this);
    }
}
