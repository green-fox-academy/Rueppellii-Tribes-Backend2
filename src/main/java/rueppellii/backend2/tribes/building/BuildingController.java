package rueppellii.backend2.tribes.building;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class BuildingController {

    private BuildingService buildingService;

    @Autowired
    public BuildingController(BuildingService buildingService) {
        this.buildingService = buildingService;
    }

    @PostMapping("/kingdom/build")
    public List<Building> buildNewBuilding(@RequestBody BuildingDTO buildingDTO) {
        buildingService.createBuilding(buildingDTO);
    }
}
