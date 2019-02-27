package rueppellii.backend2.tribes.troop.utility;

import lombok.Getter;
import lombok.Setter;
import rueppellii.backend2.tribes.troop.persistence.model.Troop;

import java.util.List;

@Getter
@Setter
public class ListKingdomsTroopsDTO {

    List<Troop> troops;
}
