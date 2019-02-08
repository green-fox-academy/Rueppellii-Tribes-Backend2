package rueppellii.backend2.tribes.building.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rueppellii.backend2.tribes.building.utility.BuildingType;
import rueppellii.backend2.tribes.building.persistence.repository.BuildingRepository;
import rueppellii.backend2.tribes.building.persistence.model.Building;
import rueppellii.backend2.tribes.kingdom.persistence.model.Kingdom;
import rueppellii.backend2.tribes.kingdom.service.KingdomService;
import rueppellii.backend2.tribes.progression.exception.BuildingNotFoundException;
import rueppellii.backend2.tribes.progression.persistence.ProgressionModel;

import java.util.List;
import java.util.stream.Collectors;

import static rueppellii.backend2.tribes.building.utility.BuildingFactory.makeBuilding;

@Service
public class BuildingService {

    private BuildingRepository buildingRepository;
    private KingdomService kingdomService;

    @Autowired
    public BuildingService(BuildingRepository buildingRepository, KingdomService kingdomService) {
        this.buildingRepository = buildingRepository;
        this.kingdomService = kingdomService;
    }

    public void createBuilding(ProgressionModel progressionModel, Kingdom kingdom) throws IllegalArgumentException {
        Building building;
        for (BuildingType t : BuildingType.values()) {
            if (BuildingType.valueOf(progressionModel.getType().toUpperCase()).equals(t)) {
                building = makeBuilding(t);
                building.setBuildingsKingdom(kingdom);
                kingdom.getKingdomsBuildings().add(building);
                buildingRepository.save(building);
                kingdomService.save(kingdom);
                return;
            }
        }
        throw new IllegalArgumentException("No such building type!");
    }

    public void saveBuilding(Building building) {
        buildingRepository.save(building);
    }

    public Building findById(Long id) throws BuildingNotFoundException {
        return buildingRepository.findById(id).orElseThrow(() -> new BuildingNotFoundException("Building can not be found!"));
    }

    public Integer getLevelOfTownHall(Kingdom kingdom) {
        List<Building> townhall = kingdom
                .getKingdomsBuildings()
                .stream()
                .filter(building -> building.getType().getName().matches("TOWNHALL"))
                .collect(Collectors.toList());
        return townhall.get(0).getLevel();
    }

    public void upgradeBuilding(ProgressionModel progressionModel, Kingdom kingdom) throws BuildingNotFoundException {
        Building building = findById(progressionModel.getGameObjectId());
    }

}
