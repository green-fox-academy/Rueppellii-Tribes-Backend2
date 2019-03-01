package rueppellii.backend2.tribes.kingdom.utility;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class KingdomListWithLocationDTO {

    private List<KingdomWithLocationDTO> kingdoms;

    public KingdomListWithLocationDTO(List<KingdomWithLocationDTO> kingdoms) {
        this.kingdoms = kingdoms;
    }
}
