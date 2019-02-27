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
import rueppellii.backend2.tribes.kingdom.service.KingdomService;
import rueppellii.backend2.tribes.troop.exception.TroopNotFoundException;
import rueppellii.backend2.tribes.troop.persistence.model.Troop;
import rueppellii.backend2.tribes.troop.service.TroopService;


@RunWith(SpringRunner.class)
@ActiveProfiles("Test")
@SpringBootTest
@SqlGroup({
        @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:clear.sql"),
        @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:data.sql"),
        @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = "classpath:clear.sql"),
})
public class TroopServiceTest {

    @Autowired
    private TroopService troopService;

    @Autowired
    private KingdomService kingdomService;

    @Test
    public void findTroopByIdTest() throws TroopNotFoundException {
        Long troopId = 1L;
        Troop testTroop = troopService.findById(troopId);
        int troopLevel = testTroop.getLevel();
        Assertions.assertEquals(troopLevel, 1);
    }

    @Test
    public void createTroopTest() throws TroopNotFoundException {
//        Kingdom testKingdom = new Kingdom();
//        testKingdom.setId(3L);
//        testKingdom.setName("IntegrationTestKingdom");
        Long troopId = 1L;
        Troop testTroop = troopService.findById(troopId);
        Kingdom testKingdom = testTroop.getTroopsKingdom();
        troopService.createTroop(testKingdom);
        Long expectedTroopId = 7L;
        Troop createdTroop = troopService.findById(expectedTroopId);
        Assertions.assertNotNull(createdTroop);
    }
}
