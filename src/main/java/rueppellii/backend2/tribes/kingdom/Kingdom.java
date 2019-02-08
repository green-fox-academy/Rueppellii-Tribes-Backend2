package rueppellii.backend2.tribes.kingdom;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;
import rueppellii.backend2.tribes.building.*;
import rueppellii.backend2.tribes.resource.Food;
import rueppellii.backend2.tribes.resource.Gold;
import rueppellii.backend2.tribes.building.Building;
import rueppellii.backend2.tribes.building.TownHall;
import rueppellii.backend2.tribes.resource.Resource;
import rueppellii.backend2.tribes.troop.Troop;
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
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "kingdom_troops", joinColumns = {
            @JoinColumn(name = "kingdom_id", referencedColumnName = "id")}, inverseJoinColumns = {@JoinColumn(name = "troop_id", referencedColumnName = "id")})
    private List<Troop> kingdomsTroops;

    @JsonManagedReference
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "kingdom_resource", joinColumns = {
            @JoinColumn(name = "kingdom_id", referencedColumnName = "id")}, inverseJoinColumns = {
            @JoinColumn(name = "resource_id", referencedColumnName = "resource_id")})
    public List<Resource> kingdomsResources;

    @JsonManagedReference
    @OneToMany(mappedBy = "buildingsKingdom", targetEntity = Building.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Building> kingdomsBuildings;

    public Kingdom() {
        TownHall townHall = new TownHall();
        Barracks barracks = new Barracks();
        Farm farm = new Farm();
        Mine mine = new Mine();
        Gold gold = new Gold();
        Food food = new Food();
        townHall.setBuildingsKingdom(this);
        barracks.setBuildingsKingdom(this);
        farm.setBuildingsKingdom(this);
        mine.setBuildingsKingdom(this);
        kingdomsResources = new ArrayList<>();
        kingdomsResources.add(gold);
        kingdomsResources.add(food);

    }
}
