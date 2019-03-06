package rueppellii.backend2.tribes.troop;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.junit4.SpringRunner;
import rueppellii.backend2.tribes.kingdom.persistence.model.Kingdom;
import rueppellii.backend2.tribes.progression.exception.InvalidProgressionRequestException;
import rueppellii.backend2.tribes.progression.persistence.ProgressionModel;
import rueppellii.backend2.tribes.troop.exception.TroopNotFoundException;
import rueppellii.backend2.tribes.troop.persistence.model.Troop;
import rueppellii.backend2.tribes.troop.service.TroopService;

import javax.transaction.Transactional;

@RunWith(SpringRunner.class)
@ActiveProfiles("Test")
@SpringBootTest
@SqlGroup({
        @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:clear.sql"),
        @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:data.sql"),
        @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = "classpath:clear.sql"),
})
public class IntegrationTestTroopService {

    @Autowired
    private TroopService troopService;

    private Kingdom kingdom;
    private Troop testTroop;
    private int troopLevel;
    private int troopWithTheGivenLevel;
    private int noTroopWithTheGivenLevel;
    private Long troopId;


    @Test
    public void findTroopById_Test() throws TroopNotFoundException {
        troopId = 1L;
        testTroop = troopService.findById(troopId);
        troopLevel = testTroop.getLevel();

        Assertions.assertEquals(troopLevel, 1);
    }

    @Test
    @Transactional  //this solve the Lazy Initialization Exception
    public void createTroop_Test() throws TroopNotFoundException {
        troopId = 1L;
        testTroop = troopService.findById(troopId);
        kingdom = testTroop.getTroopsKingdom();
        troopService.createTroop(kingdom);
        Long expectedTroopId = 7L;
        Troop createdTroop = troopService.findById(expectedTroopId);

        Assertions.assertNotNull(createdTroop);
    }

    @Test
    @Transactional
    public void upgradeTroop_Test() throws TroopNotFoundException {
        ProgressionModel progressionModel = new ProgressionModel();
        progressionModel.setId(1L);
        progressionModel.setGameObjectId(1L);
        progressionModel.setTimeToProgress(1551488461000L);
        troopService.upgradeTroop(progressionModel);

        troopLevel = troopService.findById(1L).getLevel();

        Assertions.assertEquals(java.util.Optional.ofNullable(2), java.util.Optional.ofNullable(troopLevel));
    }

    @Test
    @Transactional
    public void validateUpgradeTroopRequest_TroopLevelOne_Test() throws TroopNotFoundException, InvalidProgressionRequestException {
        kingdom = troopService.findById(1L).getTroopsKingdom();
        troopWithTheGivenLevel = 1;
        troopService.validateUpgradeTroopRequest(troopWithTheGivenLevel, kingdom);
    }

    @Test
    @Transactional
    public void validateUpgradeTroopRequest_TroopLevelTwo_Test() throws TroopNotFoundException, InvalidProgressionRequestException {
        kingdom = troopService.findById(4L).getTroopsKingdom();
        troopWithTheGivenLevel = 2;
        troopService.validateUpgradeTroopRequest(troopWithTheGivenLevel, kingdom);
    }

    @Test(expected = TroopNotFoundException.class)
    @Transactional
    public void validateUpgradeTroopRequest_TroopNotFoundException_Test() throws TroopNotFoundException, InvalidProgressionRequestException {
        kingdom = troopService.findById(1L).getTroopsKingdom();
        noTroopWithTheGivenLevel = 2;
        troopService.validateUpgradeTroopRequest(noTroopWithTheGivenLevel, kingdom);
    }

    @Test(expected = InvalidProgressionRequestException.class)
    @Transactional
    public void validateUpgradeTroopRequest_InvalidProgressionRequestException_Test() throws TroopNotFoundException, InvalidProgressionRequestException {
        kingdom = troopService.findById(6L).getTroopsKingdom();
        noTroopWithTheGivenLevel = 3;
        troopService.validateUpgradeTroopRequest(noTroopWithTheGivenLevel, kingdom);
    }
}
