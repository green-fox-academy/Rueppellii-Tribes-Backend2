package rueppellii.backend2.tribes.building.utility;

import lombok.Getter;
import lombok.Setter;
import rueppellii.backend2.tribes.building.persistence.model.Building;

import java.util.List;

@Getter
@Setter
public class ListKingdomsBuildingsDTO {

    List<Building> buildings;
}
