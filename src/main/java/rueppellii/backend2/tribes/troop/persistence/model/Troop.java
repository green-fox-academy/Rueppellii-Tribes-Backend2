package rueppellii.backend2.tribes.troop.persistence.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;
import rueppellii.backend2.tribes.common.Upgradeable;
import rueppellii.backend2.tribes.kingdom.persistence.model.Kingdom;
import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "troops")
public class Troop extends Upgradeable<Troop> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer level;
    private Integer HP;
    private Integer attack;
    private Integer defense;

    @JsonBackReference
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "kingdom_id")
    private Kingdom troopsKingdom;

    public Troop() {
        this.level = 1;
        this.HP =   10;
        this.attack = 1;
        this.defense = 1;
    }

    @Override
    public void upgrade(Troop troop) {

    }

    @Override
    public void create() {

    }
}
