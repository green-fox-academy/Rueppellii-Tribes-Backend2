package rueppellii.backend2.tribes.building;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static rueppellii.backend2.tribes.building.BuildingFactory.makeBuilding;

@Service
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

    public List<Building> listBuildingsInKingdom(BuildingDTO) {
        
    }
}
