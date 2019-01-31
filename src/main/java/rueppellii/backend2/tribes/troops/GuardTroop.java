package rueppellii.backend2.tribes.kingdom.troops;

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
        this.setHP(80);
        this.setAttack(5);
        this.setDefense(10);
        this.setStartedAt(new Timestamp(System.currentTimeMillis()));
        this.setFinished(false);
    }
}
