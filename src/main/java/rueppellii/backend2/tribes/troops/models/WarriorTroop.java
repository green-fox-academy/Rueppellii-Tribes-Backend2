package rueppellii.backend2.tribes.troops.models;

import rueppellii.backend2.tribes.troops.models.Troop;
import rueppellii.backend2.tribes.troops.models.TroopTypes;

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
