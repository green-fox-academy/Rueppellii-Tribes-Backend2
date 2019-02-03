package rueppellii.backend2.tribes.kingdom;

import lombok.Getter;
import lombok.Setter;
import rueppellii.backend2.tribes.building.Building;

import java.util.List;

@Getter
@Setter
public class KingdomDTO {

    private Long id;
    private String name;
    private List<Building> buildings;
}
