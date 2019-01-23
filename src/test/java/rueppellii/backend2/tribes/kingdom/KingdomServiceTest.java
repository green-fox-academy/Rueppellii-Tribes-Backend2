package rueppellii.backend2.tribes.kingdom;


import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@ActiveProfiles("Test")
@SpringBootTest
class KingdomServiceTest {

    @Autowired
    private KingdomService kingdomService;

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