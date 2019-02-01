package rueppellii.backend2.tribes.kingdom;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;
import rueppellii.backend2.tribes.TestTokenProvider;
import rueppellii.backend2.tribes.exception.KingdomNotValidException;
import rueppellii.backend2.tribes.security.jwt.JwtProvider;
import rueppellii.backend2.tribes.security.services.UserDetailsServiceImpl;
import rueppellii.backend2.tribes.security.services.UserPrinciple;
import rueppellii.backend2.tribes.user.ApplicationUser;
import rueppellii.backend2.tribes.user.ApplicationUserRepository;
import rueppellii.backend2.tribes.user.ApplicationUserService;

import java.nio.charset.Charset;
import java.util.Collections;
import java.util.Date;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;
import static rueppellii.backend2.tribes.security.SecurityConstants.EXPIRATION_TIME;
import static rueppellii.backend2.tribes.security.SecurityConstants.SECRET;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("Test")
@AutoConfigureMockMvc
@WebMvcTest(KingdomController.class)
public class KingdomControllerTest {

    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));

    private String jwtSecret = SECRET;
    private Long jwtExpiration = EXPIRATION_TIME;

    @Autowired
    private MockMvc mockMvc;
    private String userToken;
    private TestTokenProvider testTokenProvider;
    private Kingdom kingdom;
    private ApplicationUser testUser;

    @Mock
    ApplicationUserService applicationUserService;

    @MockBean
    KingdomService kingdomService;

    @MockBean
    ApplicationUserRepository applicationUserRepository;

    @MockBean
    KingdomRepository kingdomRepository;

    @MockBean
    UserDetailsServiceImpl userDetailsService;

    @MockBean
    JwtProvider jwtProvider;

    @MockBean
    Authentication authentication;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @BeforeEach
    public void setup() {

        mockMvc = webAppContextSetup(webApplicationContext)
                .apply(springSecurity())
                .build();
        Kingdom testKingdom = new Kingdom();
        testUser = new ApplicationUser();
        testUser.setUsername("test");
        testUser.setRole("ROLE_USER");
        testUser.setKingdom(testKingdom);
        userToken = Jwts.builder()
                .setSubject("test")
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpiration))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
 //       userToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWJqZWN0IjoidGVzdHVzZXIiLCJpc3N1ZWRBdCI6Ijc2NF8wMDBfMDAwIiwiZXhwaXJhdGlvbiI6Ijg2NF8wMDBfMDAwIn0.wIkVNNeviL9hHWpE10OqhepGxJcY9yM52G60XjMJoyQ";

//        testTokenProvider = new TestTokenProvider();
//        applicationUser = new ApplicationUser();
//        applicationUser.setUsername("testuser");
//        applicationUser.setPassword("testpassword");
//        applicationUser.setRole("ROLE_USER");
//        applicationUserService.saveApplicationUser(applicationUser);
//        userToken = testTokenProvider.generateJwtToken(applicationUser);
    }

    @Test
    public void accessGetKingdom() throws Exception {
        UserPrinciple userPrinciple = UserPrinciple.build(testUser);
        Mockito.when(userDetailsService.loadUserByUsername("test")).thenReturn(userPrinciple);
        mockMvc.perform(get("/kingdom")
                .contentType(contentType)
                .header("Authorization", "Bearer " + userToken))
                .andExpect(status().isOk());
//                .andExpect(jsonPath("$.id", Matchers.is(kingdom.getId())))
//                .andExpect(jsonPath("$.name", Matchers.is(kingdom.getName())))
//                .andExpect(jsonPath("$.buildings", Matchers.is(kingdom.getKingdomsBuildings())));
    }
}
