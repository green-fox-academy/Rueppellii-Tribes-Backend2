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
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "kingdom_troops", joinColumns = {
            @JoinColumn(name = "kingdom_id", referencedColumnName = "id")}, inverseJoinColumns = {
            @JoinColumn(name = "troop_id", referencedColumnName = "id")})
    private List<Troop> kingdomsTroops;

    @JsonManagedReference
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "kingdom_resources", joinColumns = {
            @JoinColumn(name = "kingdom_id", referencedColumnName = "id")}, inverseJoinColumns = {
            @JoinColumn(name = "resource_id", referencedColumnName = "id")})
    public List<Resource> kingdomsResources;

    @JsonManagedReference
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "kingdom_buildings", joinColumns = {
            @JoinColumn(name = "kingdom_id", referencedColumnName = "id")}, inverseJoinColumns = {
            @JoinColumn(name = "building_id", referencedColumnName = "id")})
    private List<Building> kingdomsBuildings;

    @JsonManagedReference
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "kingdom_progresses", joinColumns = {
            @JoinColumn(name = "kingdom_id", referencedColumnName = "id")}, inverseJoinColumns = {
            @JoinColumn(name = "progress_id", referencedColumnName = "id")})
    private List<ProgressionModel> kingdomsProgresses;

    public Kingdom() {
        kingdomsBuildings = BuildingService.starterKit();
        kingdomsResources = ResourceService.starterKit();
    }
}
