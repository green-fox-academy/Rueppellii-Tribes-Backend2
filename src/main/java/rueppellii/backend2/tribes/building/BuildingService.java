package rueppellii.backend2.tribes.building;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import rueppellii.backend2.tribes.exception.KingdomNotValidException;
import rueppellii.backend2.tribes.kingdom.Kingdom;
import rueppellii.backend2.tribes.kingdom.KingdomRepository;

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

//    public Building createBuilding(BuildingDTO buildingDTO) throws Exception {
//        Building building;
//        UserPrinciple loggedInUser = (UserPrinciple) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        Kingdom kingdom = kingdomRepository.findByApplicationUser_Username(loggedInUser.getUsername()).orElseThrow(() -> new KingdomNotValidException("No kingdom"));
//        for (BuildingType t : BuildingType.values()) {
//            if (BuildingType.valueOf(buildingDTO.getType().toUpperCase()).equals(t)) {
//                building = makeBuilding(t);
//                building.setBuildingsKingdom(kingdom);
//                kingdom.getKingdomsBuildings().add(building);
//                buildingRepository.save(building);
//                kingdomRepository.save(kingdom);
//                return building;
//            }
//        }
//        throw new IllegalArgumentException("No such building type!");
//    }

    public Iterable<Building> listBuildingsInKingdom() {
        return buildingRepository.findAll();
    }
}
