package rueppellii.backend2.tribes.building;

public class BuildingFactory {

    public static Building makeBuilding(BuildingType type) {
        return type.buildBuilding();
    }
}
