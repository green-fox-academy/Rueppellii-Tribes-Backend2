package rueppellii.backend2.tribes.troop;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;
import rueppellii.backend2.tribes.kingdom.Kingdom;

import javax.persistence.*;
import java.sql.Timestamp;

@Getter
@Setter
@Entity
@Table(name = "troops")
public class Troop {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long troop_id;

    private Integer level;
    private Integer HP;
    private Integer attack;
    private Integer defense;
    private Timestamp startedAt;
    private Timestamp finishedAt;

    @JsonBackReference
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinTable(name = "kingdom_troops", joinColumns = {
            @JoinColumn(name = "troop_id", referencedColumnName = "troop_id")}, inverseJoinColumns = {
            @JoinColumn(name = "kingdom_id", referencedColumnName = "id")})
    private Kingdom kingdom;

    public Troop() {
        this.level = 1;
        this.HP =   100;
        this.attack = 20;
        this.defense = 10;
        this.startedAt = new Timestamp(System.currentTimeMillis());
    }
}
