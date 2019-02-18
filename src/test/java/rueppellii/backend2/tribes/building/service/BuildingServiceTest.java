package rueppellii.backend2.tribes.building.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import rueppellii.backend2.tribes.building.exception.BuildingNotFoundException;
import rueppellii.backend2.tribes.building.exception.UpgradeFailedException;
import rueppellii.backend2.tribes.building.persistence.model.Building;
import rueppellii.backend2.tribes.building.persistence.repository.BuildingRepository;
import rueppellii.backend2.tribes.building.utility.BuildingFactory;
import rueppellii.backend2.tribes.building.utility.BuildingType;
import rueppellii.backend2.tribes.kingdom.persistence.model.Kingdom;
import rueppellii.backend2.tribes.kingdom.persistence.repository.KingdomRepository;
import rueppellii.backend2.tribes.progression.persistence.ProgressionModel;

import static org.junit.jupiter.api.Assertions.*;
import static rueppellii.backend2.tribes.building.utility.BuildingFactory.makeBuilding;

class BuildingServiceTest {

    private Building townhall;
    private Kingdom kingdom;
    private Building farm;
    private ProgressionModel progressionModel;

    @InjectMocks
    BuildingService buildingService;

    @Mock
    BuildingRepository buildingRepository;

    @Mock
    KingdomRepository kingdomRepository;

    @Mock
    BuildingFactory buildingFactory;

    @Mock
    BuildingType buildingType;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        kingdom = new Kingdom();
        kingdom.setKingdomsBuildings(BuildingService.starterKit(kingdom));
        townhall = makeBuilding(BuildingType.TOWNHALL);
        farm = makeBuilding(BuildingType.FARM);
        kingdom.getKingdomsBuildings().add(farm);
    }

    @Test
    void findBuildingInKingdom() {
    }

    @Test
    void isTownhall() {
        assertTrue(buildingService.isTownhall(townhall));
    }

    @Test
    void isNotTownhall() {
        assertFalse(buildingService.isTownhall(farm));
    }

    @Test
    void buildingIsSameLevelAsTownhall() {
        assertThrows(UpgradeFailedException.class, () -> buildingService.checkIfBuildingIsUnderTownhallLevel(kingdom, farm));
    }

    @Test
    void buildingIsBelowTownhallLevel() {
        farm.setLevel(0);
        assertDoesNotThrow(() -> buildingService.checkIfBuildingIsUnderTownhallLevel(kingdom, farm));
    }

    @Test
    void buildingIsUnderMaxLevel() {
    }

    @Test
    void validateBuildingUpgrade() {
    }

    @Test
    void upgradeBuilding() throws BuildingNotFoundException {
        Integer expectedLevel = farm.getLevel() + 1;
        progressionModel.setType("FARM");
        progressionModel.setProgressKingdom(kingdom);
        progressionModel.setGameObjectId(farm.getId());
        Mockito.when(buildingService.findById(progressionModel.getGameObjectId())).thenReturn(farm);
        buildingService.upgradeBuilding(progressionModel);
        Integer upgradedBuildingLevel = farm.getLevel();
        assertEquals(expectedLevel, upgradedBuildingLevel);
    }

    @Test
    void levelOfTownHallIsOne() {
        Integer expectedLevel = 1;
        Integer townhallLevel = buildingService.getLevelOfTownHall(kingdom.getKingdomsBuildings());
        assertEquals(expectedLevel, townhallLevel);
    }

    @Test
    void sumOfLevelsOfUpgradedBarracks() {
    }

    @Test
    void getTroopUpgradeTimeMultiplier() {
    }

    @Test
    void starterKit() {
    }
}