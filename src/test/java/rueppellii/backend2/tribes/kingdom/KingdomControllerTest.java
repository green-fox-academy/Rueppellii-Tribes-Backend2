package rueppellii.backend2.tribes.kingdom;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;
import rueppellii.backend2.tribes.TribesApplication;
import rueppellii.backend2.tribes.security.model.UserContext;
import rueppellii.backend2.tribes.security.model.token.AccessJwtToken;
import rueppellii.backend2.tribes.security.model.token.JwtTokenFactory;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

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
    private AccessJwtToken token;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockBean
    KingdomService kingdomService;

    @Autowired
    JwtTokenFactory jwtTokenFactory;

    @BeforeEach
    public void setup() {
        mockMvc = webAppContextSetup(webApplicationContext)
                .apply(springSecurity())
                .build();
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        UserContext userContext = UserContext.create("test", authorities);
        token = jwtTokenFactory.createAccessJwtToken(userContext);
    }

    @Test
    public void accessGetKingdom() throws Exception {
        mockMvc.perform(get("/api/kingdom")
                .contentType(contentType)
                .header("Authorization", "Bearer " + token.getToken()))
                .andExpect(status().isOk());
    }

    @Test
    public void accessGetKingdomWithFakeToken() throws Exception {
        mockMvc.perform(get("/api/kingdom")
                .contentType(contentType)
                .header("Authorization", "Bearer " + "fakeToken"))
                .andExpect(status().isUnauthorized());
    }
}