package rueppellii.backend2.tribes.building.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rueppellii.backend2.tribes.building.utility.BuildingDTO;
import rueppellii.backend2.tribes.building.utility.BuildingType;
import rueppellii.backend2.tribes.building.persistence.repository.BuildingRepository;
import rueppellii.backend2.tribes.building.persistence.model.Building;
import rueppellii.backend2.tribes.kingdom.persistence.model.Kingdom;
import rueppellii.backend2.tribes.kingdom.persistence.repository.KingdomRepository;
import rueppellii.backend2.tribes.kingdom.exception.KingdomNotValidException;
import rueppellii.backend2.tribes.user.persistence.model.ApplicationUser;
import rueppellii.backend2.tribes.user.service.ApplicationUserService;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

import static rueppellii.backend2.tribes.building.utility.BuildingFactory.makeBuilding;

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

    public Building createBuilding(BuildingDTO buildingDTO, String loggedInUserName) throws Exception {
        Building building;
        Kingdom userKingdom = kingdomRepository.findByApplicationUser_Username(loggedInUserName).orElseThrow(() -> new KingdomNotValidException("You don't have a kingdomName!"));
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

    //TODO: this should work by only building id and should throw BuildingNotFoundException
    public Building findByIds(Long id, Long kingdomId) throws Exception {
        return buildingRepository.findByIdAndBuildingsKingdom_Id(id, kingdomId).orElseThrow(() -> new Exception());
    }

    public Integer getLevelOfTownHall(ApplicationUser applicationUser) {
        List<Building> townhall = applicationUser
                .getKingdom()
                .getKingdomsBuildings()
                .stream()
                .filter(building -> building.getType().getName().matches("TOWNHALL"))
                .collect(Collectors.toList());
        return townhall.get(0).getLevel();
    }
}
