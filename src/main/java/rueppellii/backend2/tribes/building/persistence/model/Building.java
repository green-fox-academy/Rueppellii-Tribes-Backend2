package rueppellii.backend2.tribes.building.persistence.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;
import rueppellii.backend2.tribes.building.utility.BuildingType;
import rueppellii.backend2.tribes.common.Upgradeable;
import rueppellii.backend2.tribes.kingdom.persistence.model.Kingdom;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type")
@Table(name = "buildings")
public abstract class Building extends Upgradeable<Building> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Transient
    @Enumerated(EnumType.STRING)
    private BuildingType type;

    private Integer level;
    private Integer HP;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "kingdom_id")
    private Kingdom buildingsKingdom;

    public Building() {
        this.level = 1;
    }


}
