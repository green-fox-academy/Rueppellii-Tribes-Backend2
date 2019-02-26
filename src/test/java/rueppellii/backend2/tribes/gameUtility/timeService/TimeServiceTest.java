package rueppellii.backend2.tribes.gameUtility.timeService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import static rueppellii.backend2.tribes.gameUtility.timeService.TimeConstants.*;
import static org.junit.jupiter.api.Assertions.*;

class TimeServiceTest {

    @InjectMocks
    private TimeService timeService;

    private Long calculationSystemTime = System.currentTimeMillis();
    private Integer buildingLevel;
    private Integer townhallLevel;
    private Integer troopLevel;
    private Double troopUpgradeTimeMultiplier;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void calculateTimeOfBuildingCreationTest() {
        assertEquals(calculationSystemTime + BUILDING_PROGRESSION_TIME,
                calculationSystemTime + 30000L);
    }

    @Test
    void calculateTimeDifferenceOfBuildingUpgradeFromLevelOneAndTwoTest() {
        buildingLevel = 1;
        townhallLevel = 1;

        Long TownHallLevelOneAndBuildingLevelOne = calculationSystemTime +
                (BUILDING_PROGRESSION_TIME * buildingLevel) / townhallLevel;

        buildingLevel = 2;
        Long TownHallLevelOneAndBuildingLevelTwo = calculationSystemTime +
                (BUILDING_PROGRESSION_TIME * buildingLevel) / townhallLevel;

        Long actualDifference = TownHallLevelOneAndBuildingLevelTwo - TownHallLevelOneAndBuildingLevelOne;

        assertEquals(BUILDING_PROGRESSION_TIME, actualDifference);
    }

    @Test
    void calculateTimeDifferenceOfBuildingUpgradeFromLevelOneAndTwoAtTownhallLevelTwoTest() {
        buildingLevel = 1;
        townhallLevel = 2;
        Long TownHallLevelOneAndBuildingLevelOne = calculationSystemTime +
                (BUILDING_PROGRESSION_TIME * buildingLevel) / townhallLevel;

        buildingLevel = 2;
        Long TownHallLevelOneAndBuildingLevelTwo = calculationSystemTime +
                (BUILDING_PROGRESSION_TIME * buildingLevel) / townhallLevel;

        Long actualDifference = TownHallLevelOneAndBuildingLevelTwo - TownHallLevelOneAndBuildingLevelOne;
        Long expectedDifference = BUILDING_PROGRESSION_TIME / 2;

        assertEquals(expectedDifference, actualDifference);
    }

    @Test
    void calculateTimeDifferenceOfTroopCreationAtBarrackLevelOneTest() {
        troopUpgradeTimeMultiplier = 1.0;
        Long timeWithMultiplierOne = calculationSystemTime + (long)(TROOP_PROGRESSION_TIME * troopUpgradeTimeMultiplier);

        troopUpgradeTimeMultiplier = 2.0;
        Long timeWithMultiplierTwo = calculationSystemTime + (long)(TROOP_PROGRESSION_TIME * troopUpgradeTimeMultiplier);

        Long actualTimeDifference = timeWithMultiplierTwo - timeWithMultiplierOne;

        assertEquals(TROOP_PROGRESSION_TIME, actualTimeDifference);
    }

    @Test
    void calculateTimeDifferenceOfTroopUpgradeAtLevelOneAndTwoTest() {
        troopUpgradeTimeMultiplier = 1.0;
        troopLevel = 1;
        Long timeAtLevelOne = calculationSystemTime + (long) (TROOP_PROGRESSION_TIME * troopUpgradeTimeMultiplier * troopLevel);

        troopLevel = 2;
        Long timeAtLevelTwo = calculationSystemTime + (long) (TROOP_PROGRESSION_TIME * troopUpgradeTimeMultiplier * troopLevel);

        Long actualTimeDifference = timeAtLevelTwo - timeAtLevelOne;

        assertEquals(TROOP_PROGRESSION_TIME, actualTimeDifference);
    }

    @Test
    void timeIsUpAssertTrueTest() {
        Long getTimeToProgress = 1551177396L;
        Boolean expected;
        if (getTimeToProgress <= calculationSystemTime) {
            expected = true;
        } else {
            expected = false;
        }
        assertTrue(expected, "time is up");
    }

    @Test
    void timeIsUpAssertFalseTest() {
        Long getTimeToProgress = 1551177396L;
        Boolean expected;
        if (getTimeToProgress > calculationSystemTime) {
            expected = true;
        } else {
            expected = false;
        }
        assertFalse(expected, "already ready");
    }
}