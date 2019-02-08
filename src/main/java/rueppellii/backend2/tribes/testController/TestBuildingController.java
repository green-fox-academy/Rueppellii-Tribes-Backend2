package rueppellii.backend2.tribes.testController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rueppellii.backend2.tribes.building.persistence.model.Building;
import rueppellii.backend2.tribes.building.service.BuildingService;
import rueppellii.backend2.tribes.building.utility.BuildingDTO;
import rueppellii.backend2.tribes.user.service.ApplicationUserService;

import java.security.Principal;

@RestController
@RequestMapping("/api/test")
public class TestBuildingController {

    private BuildingService buildingService;
    private ApplicationUserService applicationUserService;

    @Autowired
    public TestBuildingController(BuildingService buildingService, ApplicationUserService applicationUserService) {
        this.buildingService = buildingService;
        this.applicationUserService = applicationUserService;
    }

    @PostMapping("/kingdom/build/")
    public Building buildNewBuilding(@RequestBody BuildingDTO buildingDTO, Principal principal) throws Exception {
        String loggedInUser = applicationUserService.getUsernameByPrincipal(principal);
        return buildingService.createBuilding(buildingDTO, loggedInUser);
    }
}
