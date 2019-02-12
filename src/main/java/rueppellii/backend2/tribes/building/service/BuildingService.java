package rueppellii.backend2.tribes.building.service;

import com.google.common.collect.Iterables;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rueppellii.backend2.tribes.building.persistence.model.TownHall;
import rueppellii.backend2.tribes.building.utility.BuildingType;
import rueppellii.backend2.tribes.building.persistence.repository.BuildingRepository;
import rueppellii.backend2.tribes.building.persistence.model.Building;
import rueppellii.backend2.tribes.kingdom.persistence.model.Kingdom;
import rueppellii.backend2.tribes.building.exception.BuildingNotFoundException;
import rueppellii.backend2.tribes.progression.persistence.ProgressionModel;
import rueppellii.backend2.tribes.resource.presistence.model.Food;
import rueppellii.backend2.tribes.resource.presistence.model.Gold;
import rueppellii.backend2.tribes.resource.presistence.model.Resource;
import rueppellii.backend2.tribes.resource.utility.ResourceType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.*;
import static rueppellii.backend2.tribes.building.utility.BuildingFactory.makeBuilding;
import static rueppellii.backend2.tribes.resource.utility.ResourceConstants.RESOURCE_PER_MINUTE_BUILDING_LEVEL_MULTIPLIER;
import static rueppellii.backend2.tribes.resource.utility.ResourceConstants.RESOURCE_PER_MINUTE_BUILDING_LEVEL_ONE;

@Service
public class BuildingService {

    private BuildingRepository buildingRepository;

    @Autowired
    public BuildingService(BuildingRepository buildingRepository) {
        this.buildingRepository = buildingRepository;
    }

    public void createBuilding(ProgressionModel progressionModel, Kingdom kingdom) throws IllegalArgumentException {
        Building building;
        for (BuildingType t : BuildingType.values()) {
            if (BuildingType.valueOf(progressionModel.getType().toUpperCase()).equals(t)) {
                building = makeBuilding(t);
                building.setBuildingsKingdom(kingdom);
                buildingRepository.save(building);
                return;
            }
        }
        throw new IllegalArgumentException("No such building type!");
    }

    public void upgradeBuilding(ProgressionModel progressionModel) throws BuildingNotFoundException {
        Building building = findById(progressionModel.getGameObjectId());
        building.setLevel(building.getLevel() + 1);
        buildingRepository.save(building);
    }

    public void saveBuilding(Building building) {
        buildingRepository.save(building);
    }

    public Building findById(Long id) throws BuildingNotFoundException {
        return buildingRepository.findById(id).orElseThrow(() -> new BuildingNotFoundException("Building not found by id: " + id));
    }

    public Integer getLevelOfTownHall(List<Building> kingdomsBuildings) {
        return ((TownHall) Iterables.getOnlyElement(kingdomsBuildings
                .stream()
                .filter(building -> building instanceof TownHall)
                .collect(Collectors.toList()))).getLevel();
    }

    public static List<Building> starterKit(Kingdom kingdom) {
        List<BuildingType> starterBuildingTypes = Arrays.stream(BuildingType.values()).limit(4).collect(Collectors.toList());
        List<Building> starterBuildings = new ArrayList<>();
        for (BuildingType t : starterBuildingTypes) {
            starterBuildings.add(makeBuilding(t));
        }
        starterBuildings.forEach(building -> building.setBuildingsKingdom(kingdom));
        return starterBuildings;
    }
}
