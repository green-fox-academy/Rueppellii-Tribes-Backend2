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

    public KingdomWithLocationDTO(Long id, String name, Integer population, List<String> location) {
        this.id = id;
        this.name = name;
        this.population = population;
        this.location = location;
    }
}
