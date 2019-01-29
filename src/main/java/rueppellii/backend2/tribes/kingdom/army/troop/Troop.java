package rueppellii.backend2.tribes.kingdom.army.troop;

import lombok.Getter;
import lombok.Setter;
import rueppellii.backend2.tribes.kingdom.Kingdom;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.sql.Timestamp;

@Getter
public abstract class Troop implements TroopMaker {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long troopId;

    @ManyToOne(fetch = FetchType.LAZY)
    @NotBlank
    private Kingdom kingdom;

    @Enumerated
    @Setter
    private TroopType troopType;

    private Integer level;
    private Integer HP;
    private Integer attackPower;
    private Integer defensePower;
    private Integer capacity;
    private Timestamp createdAt;

    public Troop(TroopType troopType) {
        this.troopType = troopType;
        this.level = 1;
        this.createdAt = new Timestamp(System.currentTimeMillis());
    }
}
