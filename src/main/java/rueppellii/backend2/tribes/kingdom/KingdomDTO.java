package rueppellii.backend2.tribes.kingdom;

import lombok.Getter;
import lombok.Setter;
import rueppellii.backend2.tribes.building.Building;
import rueppellii.backend2.tribes.resource.Resource;
import rueppellii.backend2.tribes.troop.models.Troop;

import java.util.List;

@Getter
@Setter
public class KingdomDTO {

    private Long id;
    private String name;
    private List<Building> kingdomsBuildings;
    private List<Troop> troops;
    private List<Resource> resources;
}
