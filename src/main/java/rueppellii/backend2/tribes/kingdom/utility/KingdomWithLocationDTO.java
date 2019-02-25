package rueppellii.backend2.tribes.kingdom.utility;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class KingdomWithLocationDTO {

    private Long id;
    private String name;
    private Integer population;
    private List<String> location;
}
