package rueppellii.backend2.tribes.kingdom.army.troops;

import javax.persistence.Entity;
import java.sql.Timestamp;

@Entity
public class WarriorTroop extends Troop{

    public WarriorTroop() {
        super();
        troopBuilder();
    }

    @Override
    protected void troopBuilder() {
        this.setType(TroopTypes.WARRIOR);
        this.setAttackPower(10);
        this.setDefensePower(5);
        this.setStartedAt(new Timestamp(System.currentTimeMillis()));
        this.setFinished(false);
    }
}
