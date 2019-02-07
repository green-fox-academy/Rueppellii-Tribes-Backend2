package rueppellii.backend2.tribes.building.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rueppellii.backend2.tribes.building.utility.BuildingDTO;
import rueppellii.backend2.tribes.building.service.BuildingService;
import rueppellii.backend2.tribes.building.persistence.model.Building;
import rueppellii.backend2.tribes.progression.util.ProgressionDTO;

import java.security.Principal;

@RestController
@RequestMapping("/api/building")
public class BuildingController {

    private BuildingService buildingService;

    @Autowired
    public BuildingController(BuildingService buildingService) {
        this.buildingService = buildingService;
    }

    @PostMapping("/{id}")
    public void createNewBuilding(@RequestBody ProgressionDTO progressionDTO, Principal principal) throws Exception {
        return buildingService.createBuilding(buildingDTO, principal);
    }


}
