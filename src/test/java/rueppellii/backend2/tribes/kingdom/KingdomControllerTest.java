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
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;
import rueppellii.backend2.tribes.TribesApplication;
import rueppellii.backend2.tribes.controller.AuthController;
import rueppellii.backend2.tribes.exception.KingdomNotValidException;
import rueppellii.backend2.tribes.security.WebSecurityConfig;
import rueppellii.backend2.tribes.security.jwt.JwtAuthEntryPoint;
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
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;
import static rueppellii.backend2.tribes.security.SecurityConstants.EXPIRATION_TIME;
import static rueppellii.backend2.tribes.security.SecurityConstants.SECRET;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("Test")
//@AutoConfigureMockMvc
//@ContextConfiguration
@WebMvcTest({KingdomController.class, TribesApplication.class})
public class KingdomControllerTest {

    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));

    private String jwtSecret = SECRET;
    private Long jwtExpiration = EXPIRATION_TIME;

    @Autowired
    private MockMvc mockMvc;
    private String userToken;
    private Kingdom kingdom;
    private ApplicationUser testUser;
    private Kingdom testKingdom;
    private KingdomDTO testKingdomDTO;

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

    @MockBean
    JwtAuthEntryPoint unauthorizedHandler;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @BeforeEach
    public void setup() {
        mockMvc = webAppContextSetup(webApplicationContext)
                .apply(springSecurity())
                .build();
        testKingdom = new Kingdom();
        testKingdom.setName("testkingdom");
        testUser = new ApplicationUser();
        testUser.setUsername("test");
        testUser.setRole("ROLE_USER");
        testKingdom.setApplicationUser(testUser);
        testUser.setKingdom(testKingdom);
        userToken = Jwts.builder()
                .setSubject("test")
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpiration))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
        testKingdomDTO = new KingdomDTO();
        testKingdomDTO.setId(testKingdom.getId());
        testKingdomDTO.setName(testKingdom.getName());
    }

    @Test
    public void accessGetKingdom() throws Exception {
        UserPrinciple userPrinciple = UserPrinciple.build(testUser);
        Mockito.when(userDetailsService.loadUserByUsername("test")).thenReturn(userPrinciple);
        Mockito.when(kingdomService.getKingdomByUsername()).thenReturn(testKingdomDTO);
        mockMvc.perform(get("/kingdom")
                .contentType(contentType)
                .header("Authorization", "Bearer " + userToken))
                .andExpect(status().isOk());
    }

    @Test
    public void notAccessGetKingdom() throws Exception {
        mockMvc.perform(get("/kingdom")
                .contentType(contentType))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }
}