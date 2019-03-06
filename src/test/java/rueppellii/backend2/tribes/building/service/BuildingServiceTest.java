package rueppellii.backend2.tribes.building.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.junit4.SpringRunner;
import rueppellii.backend2.tribes.building.exception.BuildingNotFoundException;
import rueppellii.backend2.tribes.building.exception.UpgradeFailedException;
import rueppellii.backend2.tribes.building.persistence.model.Building;
import rueppellii.backend2.tribes.kingdom.persistence.model.Kingdom;
import rueppellii.backend2.tribes.progression.persistence.ProgressionModel;

import javax.transaction.Transactional;

import static rueppellii.backend2.tribes.gameUtility.purchaseService.UpgradeConstants.BUILDING_MAX_LEVEL;

@RunWith(SpringRunner.class)
@ActiveProfiles("Test")
@SpringBootTest
@SqlGroup({@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:clear.sql"), @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:data.sql"), @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = "classpath:clear.sql")})
class BuildingServiceTest {

    @Autowired
    private BuildingService buildingService;

    private Kingdom testKingdom;
    private Building testBuilding;
    private Long buildingId;
    private String buildingType;
    private int buildingLevel;


    @Test
    public void findBuildingById_Test() throws BuildingNotFoundException {
        buildingId = 1L;
        testBuilding = buildingService.findById(buildingId);
        buildingLevel = testBuilding.getLevel();

        Assertions.assertEquals(buildingLevel, 1);
    }

    @Test
    @Transactional
    void findBuildingInKingdom_Test() throws BuildingNotFoundException {
        buildingId = 1L;
        testBuilding = buildingService.findById(buildingId);
        testKingdom = testBuilding.getBuildingsKingdom();
        buildingService.findBuildingInKingdom(testKingdom, buildingId);

        Assertions.assertEquals(testBuilding, testKingdom.getKingdomsBuildings().get(0));
    }

    @Test
    @Transactional
    void getLevelOfTownHall_Test() throws BuildingNotFoundException {
        buildingId = 2L;
        testBuilding = buildingService.findById(buildingId);
        buildingType = testBuilding.getType().getName();
        buildingLevel = testBuilding.getLevel();

        Assertions.assertEquals(buildingLevel, 1);
    }

    @Test
    @Transactional
    void createBuilding_Test() throws BuildingNotFoundException, IllegalArgumentException {
        ProgressionModel progressionModel = new ProgressionModel();
        progressionModel.setId(1L);
        progressionModel.setType("Farm");
        progressionModel.setGameObjectId(9L);
        progressionModel.setTimeToProgress(1551488461000L);
        buildingService.createBuilding(progressionModel, testKingdom);
        Long expectedBuildingId = 9L;
        Building createdBuilding = buildingService.findById(expectedBuildingId);

        Assertions.assertNotNull(createdBuilding);
    }

    @Test
    void isTownhall_Test() throws BuildingNotFoundException {
        testBuilding = buildingService.findById(1L);
        buildingType = testBuilding.getType().getName();

        Assertions.assertEquals(buildingType, "TOWNHALL");
    }

    @Test
    void checkIfBuildingIsUnderTownhallLevel() {
    }

    @Test
    @Transactional
    void checkIfBuildingIsUnderMaxLevel_Test() throws BuildingNotFoundException {
        buildingId = 3L;
        testBuilding = buildingService.findById(buildingId);
        buildingLevel = testBuilding.getLevel();

        Assertions.assertTrue(buildingLevel <= BUILDING_MAX_LEVEL);
    }

    @Test
    @Transactional
    void validateBuildingUpgrade_Test() throws BuildingNotFoundException, UpgradeFailedException {
        buildingId = 1L;
        testKingdom = buildingService.findById(buildingId).getBuildingsKingdom();
        testBuilding = testKingdom.getKingdomsBuildings().get(1);

        buildingService.validateBuildingUpgrade(testKingdom, buildingId);
    }

    @Test
    @Transactional
    public void upgradeBuilding_Test() throws BuildingNotFoundException {
        ProgressionModel progressionModel = new ProgressionModel();
        progressionModel.setId(2L);
        progressionModel.setGameObjectId(2L);
        progressionModel.setTimeToProgress(1551488461000L);
        buildingService.upgradeBuilding(progressionModel, testKingdom);

        int buildingLevel = buildingService.findById(2L).getLevel();

        Assertions.assertEquals(java.util.Optional.ofNullable(2), java.util.Optional.ofNullable(buildingLevel));
    }
}