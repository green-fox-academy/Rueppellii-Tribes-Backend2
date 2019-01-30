package rueppellii.backend2.tribes.building;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;
import rueppellii.backend2.tribes.kingdom.Kingdom;

import javax.persistence.*;
import java.sql.Timestamp;

@Getter
@Setter
@Entity
public class Building {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long building_id;

    private BuildingType type;
    private Integer level;
    private Integer HP;
    private Timestamp started_at;
    private Timestamp finished_at;

    @JsonBackReference
    @ManyToOne
    @JoinTable(name = "kingdom_building")
    private Kingdom buildingsKingdom;

    public Building() {
        this.level = 1;
    }
}
