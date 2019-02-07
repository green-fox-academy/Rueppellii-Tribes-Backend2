package rueppellii.backend2.tribes.purchase;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;
import rueppellii.backend2.tribes.TribesApplication;
import rueppellii.backend2.tribes.kingdom.Kingdom;
import rueppellii.backend2.tribes.kingdom.KingdomRepository;
import rueppellii.backend2.tribes.kingdom.exception.KingdomNotValidException;
import rueppellii.backend2.tribes.security.auth.jwt.JwtAuthenticationToken;
import rueppellii.backend2.tribes.security.model.UserContext;
import rueppellii.backend2.tribes.upgrade.PurchaseService;
import rueppellii.backend2.tribes.user.persistence.model.ApplicationUser;

import java.nio.charset.Charset;
import java.security.Principal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("Test")
@SpringBootTest(classes = TribesApplication.class)
public class PurchaseServiceTest {

    private Kingdom kingdom;

    private String userName;

    @Autowired
    private PurchaseService purchaseService;

    @Mock
    KingdomRepository kingdomRepository;

    @BeforeEach
    void setUp() throws KingdomNotValidException {
        purchaseService = new PurchaseService(kingdomRepository);


    }

    @Test
    void returnsKingdomsGold() throws KingdomNotValidException {
        userName = "test";
        kingdom = new Kingdom();
        Mockito.when(purchaseService.findUsersKingdom(userName)).thenReturn(kingdom);
        Integer gold = 100;
        assertEquals(gold, purchaseService.getKingdomsGoldAmount(userName));
    }
}
