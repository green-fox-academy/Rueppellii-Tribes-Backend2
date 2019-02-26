package rueppellii.backend2.tribes.building.utility;

import lombok.Getter;
import lombok.Setter;
import rueppellii.backend2.tribes.kingdom.persistence.model.Kingdom;

import java.util.List;

@Getter
@Setter
public class ListNumberOfKingdomsBuildingsDTO {

    List<Kingdom> kingdoms;

}
