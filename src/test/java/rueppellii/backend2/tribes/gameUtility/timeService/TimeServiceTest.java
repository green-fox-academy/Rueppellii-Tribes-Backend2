package rueppellii.backend2.tribes.gameUtility.timeService;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import rueppellii.backend2.tribes.gameUtility.timeService.TimeService;
import rueppellii.backend2.tribes.progression.persistence.ProgressionModel;

import static rueppellii.backend2.tribes.gameUtility.timeService.TimeConstants.*;

class TimeServiceTest {

    @InjectMocks
    private TimeService timeService;

    private Integer buildingLevel;
    private Integer townhallLevel;
    private Integer troopLevel;
    private Double troopUpgradeTimeMultiplier;
    private Double troopCreationTimeMultiplier;
    private ProgressionModel progressionModel;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        buildingLevel = 1;
        townhallLevel = 1;
        troopLevel = 1;
        troopCreationTimeMultiplier = 1.0;
        troopUpgradeTimeMultiplier = 1.0;
    }

    @Test
    void calculateTimeOfBuildingCreationTest() {
        Long buildingCreationTime = timeService.calculateTimeOfBuildingCreation();
        Long actualCreatingTime = System.currentTimeMillis() + 30000L;
        Assertions.assertEquals(buildingCreationTime, actualCreatingTime);
    }

    @Test
    void calculateTimeDifferenceOfBuildingUpgradeFromLevelOneAndTwoTest() {
        Long TownHallLevelOneAndBuildingLevelOne = timeService.calculateTimeOfBuildingUpgrade(buildingLevel, townhallLevel);

        buildingLevel = 2;
        Long TownHallLevelOneAndBuildingLevelTwo = timeService.calculateTimeOfBuildingUpgrade(buildingLevel, townhallLevel);

        Long actualDifference = TownHallLevelOneAndBuildingLevelTwo - TownHallLevelOneAndBuildingLevelOne;

        Assertions.assertEquals(BUILDING_PROGRESSION_TIME, actualDifference);
    }

    @Test
    void calculateTimeDifferenceOfBuildingUpgradeFromLevelOneAndTwoAtTownhallLevelTwoTest() {
        townhallLevel = 2;
        Long TownHallLevelTwoAndBuildingLevelOne = timeService.calculateTimeOfBuildingUpgrade(buildingLevel, townhallLevel);

        buildingLevel = 2;
        Long TownHallLevelTwoAndBuildingLevelTwo = timeService.calculateTimeOfBuildingUpgrade(buildingLevel, townhallLevel);

        Long actualDifference = TownHallLevelTwoAndBuildingLevelTwo - TownHallLevelTwoAndBuildingLevelOne;
        Long expectedDifference = BUILDING_PROGRESSION_TIME / 2;

        Assertions.assertEquals(expectedDifference, actualDifference);
    }

    @Test
    void calculateTimeDifferenceOfTroopCreationAtBarrackLevelOneTest() {
        Long timeWithMultiplierOne = timeService.calculateTimeOfTroopCreation(troopCreationTimeMultiplier);

        troopCreationTimeMultiplier = 2.0;
        Long timeWithMultiplierTwo = timeService.calculateTimeOfTroopCreation(troopCreationTimeMultiplier);

        Long actualTimeDifference = timeWithMultiplierTwo - timeWithMultiplierOne -1; // -1 comes from the rounding value difference

        Assertions.assertEquals(TROOP_PROGRESSION_TIME, actualTimeDifference);
    }

    @Test
    void calculateTimeDifferenceOfTroopUpgradeAtLevelOneAndTwoTest() {
        Long timeAtLevelOne = timeService.calculateTimeOfTroopUpgrade(troopUpgradeTimeMultiplier, troopLevel);

        troopLevel = 2;
        Long timeAtLevelTwo = timeService.calculateTimeOfTroopUpgrade(troopUpgradeTimeMultiplier, troopLevel);

        Long actualTimeDifference = timeAtLevelTwo - timeAtLevelOne;

        Assertions.assertEquals(TROOP_PROGRESSION_TIME, actualTimeDifference);
    }

    @Test
    void timeIsUpAssertFalseTest() {
        progressionModel = new ProgressionModel();
        progressionModel.setId(1L);
        progressionModel.setGameObjectId(7L);
        progressionModel.setTimeToProgress(timeService.calculateTimeOfTroopCreation(troopCreationTimeMultiplier));

        Assertions.assertFalse(timeService.timeIsUp(progressionModel), "It cannot be true as the test finishes before time is up");
    }

    @Test
    void timeIsUpAssertTrueTest() {
        progressionModel = new ProgressionModel();
        progressionModel.setId(1L);
        progressionModel.setGameObjectId(7L);
        progressionModel.setTimeToProgress(System.currentTimeMillis() - BUILDING_PROGRESSION_TIME);

        Assertions.assertTrue(timeService.timeIsUp(progressionModel), "Deduct progression time in order to get time is up before the function ends");
    }
}
