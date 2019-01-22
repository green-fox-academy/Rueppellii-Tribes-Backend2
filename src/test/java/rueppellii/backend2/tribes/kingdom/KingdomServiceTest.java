package rueppellii.backend2.tribes.kingdom;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.Charset;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
//@DataJpaTest
@ActiveProfiles("Test")
@SpringBootTest
class KingdomServiceTest {

    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));


//    @Autowired
//    private TestEntityManager entityManager;

    @MockBean
    private KingdomRepository kingdomRepository;

//    @MockBean
//    private KingdomService kingdomService;

    @Autowired
    private KingdomService kingdomService;

//    @Test
//    void saveKingdomWorks() {
//        Kingdom kingdom = new Kingdom();
//        kingdom.setName("Starbucks");
//        entityManager.persist(kingdom);
//        entityManager.flush();
//        Kingdom found = kingdomRepository.findByName(kingdom.getName());
//        assertThat(found.getName())
//                .isEqualTo(kingdom.getName());
//    }

    @Test
    void saveKingdomWithNull() {
        Kingdom kingdom = new Kingdom();
        ResponseEntity r = new ResponseEntity(HttpStatus.I_AM_A_TEAPOT);
        assertThat(kingdomService.saveKingdom(kingdom)).isEqualTo(r);
    }

    @Test
    void saveKingdomWithEmptyName() {
        Kingdom kingdom = new Kingdom();
        kingdom.setName("");
        ResponseEntity r = new ResponseEntity(HttpStatus.I_AM_A_TEAPOT);
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