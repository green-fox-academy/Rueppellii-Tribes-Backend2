package rueppellii.backend2.tribes.building;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;

@Getter
@Setter
@Entity
public abstract class Building {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long building_id;
    private BuildingType type;
    private Integer level;
    private Integer HP;
    private Timestamp started_at;
    private Timestamp finished_at;

    public Building(BuildingType type) {
        this.type = type;
    }

    abstract void buildBuilding();
}
