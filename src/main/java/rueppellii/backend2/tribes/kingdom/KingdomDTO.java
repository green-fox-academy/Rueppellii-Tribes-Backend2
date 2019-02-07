package rueppellii.backend2.tribes.kingdom;

import lombok.Getter;
import lombok.Setter;
import rueppellii.backend2.tribes.building.Building;
import rueppellii.backend2.tribes.resource.Resource;
import rueppellii.backend2.tribes.troop.Troop;

import java.util.List;

@Getter
@Setter
public class KingdomDTO {

    private Long id;
    private String name;
    private List<Troop> kingdomsTroops;
    private List<Resource> kingdomsResources;
    private List<Building> kingdomsBuildings;
}
