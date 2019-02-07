package rueppellii.backend2.tribes.purchase;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import rueppellii.backend2.tribes.TribesApplication;
import rueppellii.backend2.tribes.kingdom.Kingdom;
import rueppellii.backend2.tribes.kingdom.KingdomRepository;
import rueppellii.backend2.tribes.kingdom.KingdomService;
import rueppellii.backend2.tribes.kingdom.exception.KingdomNotValidException;
import rueppellii.backend2.tribes.resource.ResourceService;
import rueppellii.backend2.tribes.upgrade.PurchaseService;
import rueppellii.backend2.tribes.user.persistence.dao.ApplicationUserRepository;
import rueppellii.backend2.tribes.user.persistence.model.ApplicationUser;
import rueppellii.backend2.tribes.user.service.ApplicationUserService;

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

    @BeforeEach
    void setUp() {
        purchaseService = new PurchaseService(kingdomService, resourceService);
        kingdom = new Kingdom();
    }

    @Test
    void returnsKingdomsGold() throws Exception {
        Integer gold = 100;
        assertEquals(gold, purchaseService.getGold(kingdom.getId()));
    }
}
