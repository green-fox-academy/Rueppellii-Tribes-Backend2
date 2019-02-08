package rueppellii.backend2.tribes.building;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import rueppellii.backend2.tribes.kingdom.Kingdom;
import rueppellii.backend2.tribes.kingdom.KingdomRepository;
import rueppellii.backend2.tribes.kingdom.exception.KingdomNotValidException;
import rueppellii.backend2.tribes.security.auth.jwt.JwtAuthenticationToken;
import rueppellii.backend2.tribes.security.model.UserContext;

import java.security.Principal;

import static rueppellii.backend2.tribes.building.BuildingFactory.makeBuilding;

@Service
public class BuildingService {

    private BuildingRepository buildingRepository;
    private KingdomRepository kingdomRepository;

    @Autowired
    public BuildingService(BuildingRepository buildingRepository, KingdomRepository kingdomRepository) {
        this.buildingRepository = buildingRepository;
        this.kingdomRepository = kingdomRepository;
    }

    public Building createBuilding(BuildingDTO buildingDTO, String loggedInUserName) throws Exception {
        Building building;
        Kingdom userKingdom = kingdomRepository.findByApplicationUser_Username(loggedInUserName).orElseThrow(() -> new KingdomNotValidException("You don't have a kingdom!"));
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

    public void saveBuilding(Building building) {
        buildingRepository.save(building);
    }

    public Building findByIds(Long id, Long kingdomId) throws Exception {
        return buildingRepository.findByIdAndBuildingsKingdom_Id(id, kingdomId).orElseThrow(() -> new Exception());
    }

    public Iterable<Building> listBuildingsInKingdom() {
        return buildingRepository.findAll();
    }
}
