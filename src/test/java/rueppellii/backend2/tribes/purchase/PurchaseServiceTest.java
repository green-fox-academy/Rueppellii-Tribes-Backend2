package rueppellii.backend2.tribes.purchase;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import rueppellii.backend2.tribes.building.BuildingService;
import rueppellii.backend2.tribes.kingdom.Kingdom;
import rueppellii.backend2.tribes.kingdom.KingdomService;
import rueppellii.backend2.tribes.resource.ResourceService;
import rueppellii.backend2.tribes.troop.TroopServiceImp;
import rueppellii.backend2.tribes.upgrade.PurchaseService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("Test")
public class PurchaseServiceTest {

    private Kingdom kingdom;

    private PurchaseService purchaseService;

    @Mock
    KingdomService kingdomService;

    @Mock
    ResourceService resourceService;

    @Mock
    TroopServiceImp troopService;

    @Mock
    BuildingService buildingService;

    @BeforeEach
    void setUp() {
        purchaseService = new PurchaseService(kingdomService, resourceService, troopService, buildingService);
        kingdom = new Kingdom();
    }

    @Test
    void returnsKingdomsGold() throws Exception {
        Integer gold = 100;
        assertEquals(gold, purchaseService.getKingdomsGoldAmount(kingdom.getId()));
    }
}
