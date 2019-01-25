package rueppellii.backend2.tribes.kingdom;

import org.junit.Before;
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
class KingdomServiceTest {


    private KingdomService kingdomService;

    @Mock
    public KingdomRepository kingdomRepository;

    @Before
    public void init(){
        MockitoAnnotations.initMocks(this);
        kingdomService = new KingdomService(kingdomRepository);
    }

    @Test
    void saveKingdomWithNull() {
        Kingdom kingdom = new Kingdom();
        ResponseEntity r = new ResponseEntity(HttpStatus.BAD_REQUEST);
        assertThat(kingdomService.saveKingdom(kingdom)).isEqualTo(r);
    }

    @Test
    void saveKingdomWithEmptyName() {
        Kingdom kingdom = new Kingdom();
        kingdom.setName("");
        ResponseEntity r = new ResponseEntity(HttpStatus.BAD_REQUEST);
        assertThat(kingdomService.saveKingdom(kingdom)).isEqualTo(r);
    }

    @Test
    void saveKingdomWithValidName() {
        Kingdom kingdom = new Kingdom();
        kingdom.setName("Fillory");
        ResponseEntity r = new ResponseEntity(HttpStatus.OK);
        assertThat(kingdomService.saveKingdom(kingdom)).isEqualTo(r);
    }
}