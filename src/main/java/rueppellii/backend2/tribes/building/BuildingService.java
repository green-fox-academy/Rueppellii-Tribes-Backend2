package rueppellii.backend2.tribes.building;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rueppellii.backend2.tribes.kingdom.Kingdom;
import rueppellii.backend2.tribes.kingdom.KingdomRepository;
import rueppellii.backend2.tribes.kingdom.exception.KingdomNotValidException;
import rueppellii.backend2.tribes.user.service.ApplicationUserService;

import java.security.Principal;

import static rueppellii.backend2.tribes.building.BuildingFactory.makeBuilding;

@Service
public class BuildingService {

    private BuildingRepository buildingRepository;
    private KingdomRepository kingdomRepository;
    private ApplicationUserService applicationUserService;

    @Autowired
    public BuildingService(BuildingRepository buildingRepository, KingdomRepository kingdomRepository, ApplicationUserService applicationUserService) {
        this.buildingRepository = buildingRepository;
        this.kingdomRepository = kingdomRepository;
        this.applicationUserService = applicationUserService;
    }

    public Building createBuilding(BuildingDTO buildingDTO, Principal principal) throws Exception {
        Building building;
        String loggedInUser = applicationUserService.getUsernameByPrincipal(principal);
        Kingdom userKingdom = kingdomRepository.findByApplicationUser_Username(loggedInUser).orElseThrow(() -> new KingdomNotValidException("You don't have a kingdom!"));
        for (BuildingType t : BuildingType.values()) {
            if (BuildingType.valueOf(buildingDTO.getType().toUpperCase()).equals(t)) {
                building = makeBuilding(t);
                building.setBuildingsKingdom(userKingdom);
                userKingdom.getKingdomsBuildings().add(building);
                buildingRepository.save(building);
                kingdomRepository.save(userKingdom);
                return building;
            }
        }
        throw new IllegalArgumentException("No such building type!");
    }

    public Iterable<Building> listBuildingsInKingdom() {
        return buildingRepository.findAll();
    }
}
