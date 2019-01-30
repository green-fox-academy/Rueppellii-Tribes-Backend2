package rueppellii.backend2.tribes.kingdom.army.troops;

import javax.persistence.Entity;
import java.sql.Timestamp;

@Entity
public class GuardTroop extends Troop {

    public GuardTroop() {
        super();
        troopBuilder();
    }

    @Override
    protected void troopBuilder() {
        this.setType(TroopTypes.GUARD);
        this.setAttackPower(5);
        this.setDefensePower(10);
        this.setStartedAt(new Timestamp(System.currentTimeMillis()));
        this.setFinished(false);
    }
}
