package rueppellii.backend2.tribes.building.service;

import com.google.common.collect.Iterables;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rueppellii.backend2.tribes.building.exception.UpgradeFailedException;
import rueppellii.backend2.tribes.building.persistence.model.Barracks;
import rueppellii.backend2.tribes.building.persistence.model.TownHall;
import rueppellii.backend2.tribes.building.utility.BuildingType;
import rueppellii.backend2.tribes.building.persistence.repository.BuildingRepository;
import rueppellii.backend2.tribes.building.persistence.model.Building;
import rueppellii.backend2.tribes.kingdom.persistence.model.Kingdom;
import rueppellii.backend2.tribes.building.exception.BuildingNotFoundException;
import rueppellii.backend2.tribes.progression.persistence.ProgressionModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static rueppellii.backend2.tribes.building.utility.BuildingFactory.makeBuilding;
import static rueppellii.backend2.tribes.gameUtility.purchaseService.UpgradeConstants.BUILDING_MAX_LEVEL;

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

    public Building findBuildingInKingdom(Kingdom kingdom, Long buildingId) throws BuildingNotFoundException {
        for (Building kingdomsBuilding : kingdom.getKingdomsBuildings()) {
            if (kingdomsBuilding.getId().equals(buildingId)) {
                return kingdomsBuilding;
            }
        }
        throw new BuildingNotFoundException("Building not found");
    }

    public boolean isTownhall(Building building) {
        return building.getType().getName().toUpperCase().equals("TOWNHALL");
        }

    public void checkIfBuildingIsUnderTownhallLevel(Kingdom kingdom, Building building) throws UpgradeFailedException {
        if (!isTownhall(building)) {
            if (building.getLevel() >= getLevelOfTownHall(kingdom.getKingdomsBuildings())) {
                throw new UpgradeFailedException("Upgrade Townhall first");
            }
        }
    }

    public void checkIfBuildingIsUnderMaxLevel(Building building) throws UpgradeFailedException {
        if (building.getLevel().equals(BUILDING_MAX_LEVEL)) {
            throw new UpgradeFailedException("Building has reached MAX level");
        }
    }

    public Building validateBuildingUpgrade(Kingdom kingdom, Long buildingId) throws BuildingNotFoundException, UpgradeFailedException {
        Building building = findBuildingInKingdom(kingdom, buildingId);
        checkIfBuildingIsUnderMaxLevel(building);
        checkIfBuildingIsUnderTownhallLevel(kingdom, building);
        return building;
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

    public Integer sumOfLevelsOfUpgradedBarracks(Kingdom kingdom) {
        Integer numberOfLevelOneBarracks = 0;
        Integer sumofLevelOfBarracks = 0;
        for (Building barracks : kingdom.getKingdomsBuildings()) {
            if (barracks.getType().getName().toUpperCase().equals("BARRACKS")) {
                sumofLevelOfBarracks += barracks.getLevel();
                if (barracks.getLevel() == 1) {
                    numberOfLevelOneBarracks++;
                }
            }
        }
        return sumofLevelOfBarracks - numberOfLevelOneBarracks;
    }

    public Double getTroopUpgradeTimeMultiplier(Kingdom kingdom) {
        return Math.pow(0.95, sumOfLevelsOfUpgradedBarracks(kingdom));
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
