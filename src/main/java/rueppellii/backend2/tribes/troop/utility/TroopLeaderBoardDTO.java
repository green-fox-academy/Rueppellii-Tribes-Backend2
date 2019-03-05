package rueppellii.backend2.tribes.troop.utility;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TroopLeaderBoardDTO {

    String kingdomName;
    Integer numberOfTroops;

    public TroopLeaderBoardDTO(String kingdomName, Integer numberOfTroops) {
        this.kingdomName = kingdomName;
        this.numberOfTroops = numberOfTroops;
    }
}
