package rueppellii.backend2.tribes.kingdom;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("Test")
@SpringBootTest
public class KingdomServiceTest {

    private KingdomService kingdomService;
    private Kingdom kingdom;
    private ResponseEntity r;

    @Mock
    private KingdomRepository kingdomRepository;

    @BeforeEach
    void init(){
        kingdom = new Kingdom();
        MockitoAnnotations.initMocks(this);
        kingdomService = new KingdomService(kingdomRepository);
    }

    @Test
    void saveKingdomWithNull() {
        r = new ResponseEntity(HttpStatus.BAD_REQUEST);
        assertThat(kingdomService.saveKingdom(kingdom)).isEqualTo(r);
    }

    @Test
    void saveKingdomWithEmptyName() {
        kingdom.setName("");
        r = new ResponseEntity(HttpStatus.BAD_REQUEST);
        assertThat(kingdomService.saveKingdom(kingdom)).isEqualTo(r);
    }

    @Test
    void saveKingdomWithValidName() {
        kingdom.setName("Fillory");
        r = new ResponseEntity(HttpStatus.OK);
        assertThat(kingdomService.saveKingdom(kingdom)).isEqualTo(r);
    }
}