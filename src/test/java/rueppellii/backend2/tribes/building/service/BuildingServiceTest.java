package rueppellii.backend2.tribes.building.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import rueppellii.backend2.tribes.building.exception.BuildingNotFoundException;
import rueppellii.backend2.tribes.building.persistence.model.Building;
import rueppellii.backend2.tribes.building.utility.BuildingType;
import rueppellii.backend2.tribes.kingdom.persistence.model.Kingdom;
import rueppellii.backend2.tribes.progression.persistence.ProgressionModel;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.*;
import static rueppellii.backend2.tribes.building.utility.BuildingFactory.makeBuilding;
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

    @BeforeEach
    void setUp() {
    }

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
    void isTownhall() {

    }

//    @Test
//    void validateBuildingUpgrade() {
//    }
//

    @Test
    @Transactional
    void getLevelOfTownHall() throws BuildingNotFoundException {
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
    void checkIfBuildingIsUnderTownhallLevel() {
    }

    @Test
    void checkIfBuildingIsUnderMaxLevel() throws BuildingNotFoundException {
        buildingId = 3L;
        testBuilding = buildingService.findById(buildingId);
        buildingLevel = testBuilding.getLevel();

        Assertions.assertTrue(buildingLevel <= BUILDING_MAX_LEVEL);
    }

    @Test
    void validateBuildingUpgrade() {
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