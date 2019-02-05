package rueppellii.backend2.tribes.upgrade.persistence;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;
import rueppellii.backend2.tribes.kingdom.Kingdom;

import javax.persistence.*;

@Setter
@Getter
@Entity
public class UpgradeModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonBackReference
    @ManyToOne
    @JoinTable(name = "kingdom_upgrade")
    private Kingdom upgradesKingdom;

    private String objectToUpgrade;
    private Long timeToCreate;

    public UpgradeModel(Long duration) {
        this.timeToCreate = System.currentTimeMillis() + duration;
    }
}
