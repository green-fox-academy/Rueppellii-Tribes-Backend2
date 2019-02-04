package rueppellii.backend2.tribes.troop.models;

import javax.persistence.Entity;
import java.sql.Timestamp;

@Entity
public class WarriorTroop extends Troop {

    public WarriorTroop() {
        super();
        troopBuilder();
    }

    @Override
    protected void troopBuilder() {
        this.setType(TroopTypes.WARRIOR);
        this.setHP(100);
        this.setAttack(10);
        this.setDefense(5);
        this.setStartedAt(new Timestamp(System.currentTimeMillis()));
        this.setFinished(false);
    }
}
