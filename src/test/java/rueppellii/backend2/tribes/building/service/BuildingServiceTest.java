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
import rueppellii.backend2.tribes.progression.persistence.ProgressionModelRepository;
import rueppellii.backend2.tribes.progression.service.ProgressionService;

import static org.junit.jupiter.api.Assertions.*;
import static rueppellii.backend2.tribes.building.utility.BuildingFactory.makeBuilding;

class BuildingServiceTest {

    private Building townhall;
    private Kingdom kingdom;
    private Building farm;
    private ProgressionModel progressionModel;
    private Building barracks;

    @InjectMocks
    BuildingService buildingService;

    @Mock
    ProgressionService progressionService;

    @Mock
    ProgressionModelRepository progressionModelRepository;

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
        barracks = makeBuilding(BuildingType.BARRACKS);
        kingdom.getKingdomsBuildings().add(farm);
        progressionModel = new ProgressionModel();
    }

    @Test
    void findBuildingInKingdom() {
    }

    @Test
    void isTownhall() {
        assertTrue(buildingService.isTownhall(townhall));
        assertFalse(buildingService.isTownhall(farm));
    }

    @Test
    void isBelowTownhallLevel() {
        assertThrows(UpgradeFailedException.class, () -> buildingService.checkIfBuildingIsUnderTownhallLevel(kingdom, farm));
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
        Mockito.when(buildingService.findById(progressionModel.getId())).thenReturn(farm);
        farm.setLevel(2);
  //      assertEquals(farm, buildingService.upgradeBuilding(progressionModel));
    }

    @Test
    void getLevelOfTownHall() {
        Integer expectedLevel = 1;
        Integer townhallLevel = buildingService.getLevelOfTownHall(kingdom.getKingdomsBuildings());
        assertEquals(expectedLevel, townhallLevel);
    }

    @Test
    void sumOfLevelsOfUpgradedBarracks() {
        barracks.setLevel(3);
        kingdom.getKingdomsBuildings().add(barracks);
        Integer expected = 3;
        Integer method = buildingService.sumOfLevelsOfUpgradedBarracks(kingdom);
        assertEquals(expected, method);
    }

    @Test
    void getTroopUpgradeTimeMultiplier() {
        barracks.setLevel(2);
        kingdom.getKingdomsBuildings().add(barracks);
        Double multiplier = buildingService.getTroopUpgradeTimeMultiplier(kingdom);
        Double expected = 0.9025;
        assertEquals(expected, multiplier);
    }

    @Test
    void starterKit() {
    }
}