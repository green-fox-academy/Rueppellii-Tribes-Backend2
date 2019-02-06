package rueppellii.backend2.tribes.troop.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import rueppellii.backend2.tribes.kingdom.Kingdom;
import javax.persistence.*;
import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "troops")
public abstract class Troop {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long troop_id;

    @Enumerated(EnumType.STRING)
    private TroopTypes type;

    private Integer level;
    private Integer HP;
    private Integer attack;
    private Integer defense;
    private Timestamp startedAt;
    private Timestamp finishedAt;

    private Boolean finished;

    @JsonBackReference
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinTable(name = "kingdom_troops", joinColumns = {
            @JoinColumn(name = "troop_id", referencedColumnName = "troop_id")}, inverseJoinColumns = {
            @JoinColumn(name = "kingdom_id", referencedColumnName = "id")})
    private Kingdom kingdom;

    protected abstract void troopBuilder();
}
