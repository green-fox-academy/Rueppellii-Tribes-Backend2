package rueppellii.backend2.tribes.troop.persistence.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;
import rueppellii.backend2.tribes.kingdom.persistence.model.Kingdom;
import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "troops")
public class Troop {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer level;
    private Integer HP;
    private Integer attack;
    private Integer defense;

    @JsonBackReference
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinTable(name = "kingdom_troops", joinColumns = {
            @JoinColumn(name = "troop_id", referencedColumnName = "id")}, inverseJoinColumns = {
            @JoinColumn(name = "kingdom_id", referencedColumnName = "id")})
    private Kingdom troopsKingdom;

    public Troop() {
        this.level = 1;
        this.HP =   100;
        this.attack = 20;
        this.defense = 10;
    }

}
