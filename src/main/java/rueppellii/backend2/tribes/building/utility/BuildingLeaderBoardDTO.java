package rueppellii.backend2.tribes.building.utility;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BuildingLeaderBoardDTO {

    String kingdomName;
    Integer numberOfBuildings;

    public BuildingLeaderBoardDTO(String kingdomName, Integer numberOfBuildings) {
        this.kingdomName = kingdomName;
        this.numberOfBuildings = numberOfBuildings;
    }
}
