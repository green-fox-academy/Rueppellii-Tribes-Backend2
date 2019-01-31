package rueppellii.backend2.tribes.kingdom;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.Charset;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("Test")
@WebMvcTest(KingdomController.class)
public class KingdomControllerTest {

    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));

    @Autowired
    public MockMvc mockMvc;

    @Mock
    public KingdomService kingdomService;

    @Mock
    public KingdomRepository kingdomRepository;



}
