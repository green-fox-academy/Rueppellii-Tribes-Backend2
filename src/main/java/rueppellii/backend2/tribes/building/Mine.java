package rueppellii.backend2.tribes.building;

public class Mine extends Building {

    public Mine() {
        super(BuildingType.MINE);
    }

    @Override
    void buildBuilding() {
        setLevel(1);
        setHP(20);
    }
}
