package rueppellii.backend2.tribes.testController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rueppellii.backend2.tribes.building.service.BuildingService;
import rueppellii.backend2.tribes.user.service.ApplicationUserService;

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
}
