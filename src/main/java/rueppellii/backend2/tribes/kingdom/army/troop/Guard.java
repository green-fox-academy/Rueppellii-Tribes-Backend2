package rueppellii.backend2.tribes.kingdom.army.troop;

public class Guard extends Troop{
    private Troop troop;

    public Guard() {
        super(TroopType.GUARD);
    }

    @Override
    public void setHP() {
        this.troop.setHP();
    }

    @Override
    public void setAttackPower() {

    }

    @Override
    public void setDefensePower() {

    }

    @Override
    public void setCapacity() {

    }

    @Override
    public Troop getTroop() {
        return null;
    }
}
