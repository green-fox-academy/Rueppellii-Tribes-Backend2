package rueppellii.backend2.tribes.building;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BuildingController {

    private BuildingService buildingService;

    @Autowired
    public BuildingController(BuildingService buildingService) {
        this.buildingService = buildingService;
    }

    @PostMapping("/kingdom/build")
    public Iterable<Building> buildNewBuilding(@RequestBody BuildingDTO buildingDTO) throws Exception {
        buildingService.createBuilding(buildingDTO);
        return buildingService.listBuildingsInKingdom();
    }
}
