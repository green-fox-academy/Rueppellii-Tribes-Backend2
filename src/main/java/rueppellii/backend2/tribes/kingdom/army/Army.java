package rueppellii.backend2.tribes.kingdom.army;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Army {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long armyId;
}
