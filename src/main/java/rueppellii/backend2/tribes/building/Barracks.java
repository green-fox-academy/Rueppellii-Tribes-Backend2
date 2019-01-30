package rueppellii.backend2.tribes.building;

public class Barracks extends Building {

    public Barracks() {
        super(BuildingType.BARRACKS);
    }

    @Override
    void buildBuilding() {
        setLevel(1);
        setHP(100);
    }
}
