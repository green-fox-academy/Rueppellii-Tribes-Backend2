package rueppellii.backend2.tribes.kingdom.utility;

import lombok.Getter;
import lombok.Setter;
import rueppellii.backend2.tribes.building.persistence.model.Building;
import rueppellii.backend2.tribes.resource.presistence.model.Resource;
import rueppellii.backend2.tribes.troop.persistence.model.Troop;

import java.util.List;

@Getter
@Setter
public class KingdomDTO {

    private Long id;
    private String name;
    private String location;
    private List<Troop> kingdomsTroops;
    private List<Resource> kingdomsResources;
    private List<Building> kingdomsBuildings;
}
