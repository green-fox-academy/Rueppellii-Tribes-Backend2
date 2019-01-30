package rueppellii.backend2.tribes.kingdom.army.troops;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Getter
@Setter
@NoArgsConstructor
public abstract class Troop {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long troopId;

    @Enumerated(EnumType.STRING)
    private TroopTypes type;

    private Integer attackPower;
    private Integer defensePower;

    private Timestamp startedAt;
    private Timestamp finishedAt;
    private Boolean finished;

    protected abstract void troopBuilder();
}
