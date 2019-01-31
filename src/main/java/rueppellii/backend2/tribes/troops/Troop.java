package rueppellii.backend2.tribes.troops;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import rueppellii.backend2.tribes.kingdom.Kingdom;

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

    private Integer HP;
    private Integer attack;
    private Integer defense;
    private Timestamp startedAt;
    private Timestamp finishedAt;

    private Boolean finished;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinTable(name = "troops", joinColumns = {
            @JoinColumn(name = "troop_id", referencedColumnName = "troopId")}, inverseJoinColumns = {
            @JoinColumn(name = "kingdom_id", referencedColumnName = "id")})
    private Kingdom kingdom;

    protected abstract void troopBuilder();
}
