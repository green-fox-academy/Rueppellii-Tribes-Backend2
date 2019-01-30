package rueppellii.backend2.tribes.building;

import org.springframework.beans.factory.annotation.Autowired;

import static rueppellii.backend2.tribes.building.BuildingFactory.makeBuilding;

public class BuildingService {

    private BuildingRepository buildingRepository;

    @Autowired
    public BuildingService(BuildingRepository buildingRepository) {
        this.buildingRepository = buildingRepository;
    }

    public void createBuilding(BuildingDTO buildingDTO) {
        for (BuildingType t : BuildingType.values()) {
            if (BuildingType.valueOf(buildingDTO.getType().toUpperCase()).equals(t)) {
                buildingRepository.save(makeBuilding(t));
            }
        }
    }
}
