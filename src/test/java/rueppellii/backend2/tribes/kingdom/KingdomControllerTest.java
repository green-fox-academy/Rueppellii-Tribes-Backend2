package rueppellii.backend2.tribes.kingdom;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;
import rueppellii.backend2.tribes.TestTokenProvider;
import rueppellii.backend2.tribes.TribesApplication;
import rueppellii.backend2.tribes.kingdom.service.KingdomService;
import rueppellii.backend2.tribes.security.model.token.JwtTokenFactory;

import java.nio.charset.Charset;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("Test")
@SpringBootTest(classes = TribesApplication.class)
public class KingdomControllerTest {

    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));

    private MockMvc mockMvc;
    private String token;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private JwtTokenFactory jwtTokenFactory;

    @MockBean
    KingdomService kingdomService;

    @BeforeEach
    public void setup() {
        mockMvc = webAppContextSetup(webApplicationContext)
                .apply(springSecurity())
                .build();
        TestTokenProvider testTokenProvider = new TestTokenProvider(jwtTokenFactory);
        token = testTokenProvider.createMockToken("test", "ROLE_USER");
    }

    @Test
    public void accessGetKingdom() throws Exception {
        mockMvc.perform(get("/api/kingdom")
                .contentType(contentType)
                .header("Authorization", token))
                .andExpect(status().isOk());
    }

    @Test
    public void accessGetKingdomWithFakeToken() throws Exception {
        mockMvc.perform(get("/api/kingdom")
                .contentType(contentType)
                .header("Authorization", "fakeToken"))
                .andExpect(status().isUnauthorized());
    }
}