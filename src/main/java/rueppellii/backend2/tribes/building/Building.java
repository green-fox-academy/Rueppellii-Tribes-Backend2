package rueppellii.backend2.tribes.building;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Entity
public abstract class Building {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long building_id;
    private BuildingType.Type type;
    private Integer level;
    private Integer HP;
    private Timestamp started_at;
    private Timestamp finished_at;
}
