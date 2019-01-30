package rueppellii.backend2.tribes.building;

public class Farm extends Building {

    public Farm() {
        super(BuildingType.FARM);
    }

    @Override
    void buildBuilding() {
        setLevel(1);
        setHP(1);
    }
}
