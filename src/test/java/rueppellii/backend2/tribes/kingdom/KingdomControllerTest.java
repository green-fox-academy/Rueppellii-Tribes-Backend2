package rueppellii.backend2.tribes.kingdom;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;
import rueppellii.backend2.tribes.TribesApplication;
import rueppellii.backend2.tribes.security.model.token.JwtTokenFactory;
import rueppellii.backend2.tribes.user.persistence.dao.ApplicationUserRepository;
import rueppellii.backend2.tribes.user.persistence.model.ApplicationUser;
import rueppellii.backend2.tribes.user.persistence.model.ApplicationUserRole;
import rueppellii.backend2.tribes.user.util.Role;

import java.nio.charset.Charset;
import java.time.ZoneId;
import java.util.Date;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;
import static rueppellii.backend2.tribes.security.SecurityConstants.ACCESS_TOKEN_EXP_TIME;
import static rueppellii.backend2.tribes.security.SecurityConstants.TOKEN_ISSUER;
import static rueppellii.backend2.tribes.security.SecurityConstants.TOKEN_SIGNING_KEY;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("Test")
//@AutoConfigureMockMvc
//@ContextConfiguration
@WebMvcTest({KingdomController.class, TribesApplication.class})
public class KingdomControllerTest {

    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));

//    private String jwtSecret = SECRET;
//    private Long jwtExpiration = EXPIRATION_TIME;

    @Autowired
    private MockMvc mockMvc;
    private String userToken;
    private ApplicationUser testUser;
    private Kingdom testKingdom;
    private KingdomDTO testKingdomDTO;
    private Claims claims;

    @MockBean
    KingdomService kingdomService;

    @MockBean
    ApplicationUserRepository applicationUserRepository;

    @MockBean
    KingdomRepository kingdomRepository;

    @MockBean
    JwtTokenFactory jwtProvider;

    @MockBean
    Authentication authentication;

//    @MockBean
//    JwtAuthEntryPoint unauthorizedHandler;

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
        ApplicationUserRole role = new ApplicationUserRole();
        role.setRoleEnum(Role.USER);
        testUser.getRoles().add(role);
        testKingdom.setApplicationUser(testUser);
        testUser.setKingdom(testKingdom);
        claims = Jwts.claims().setSubject(testUser.getUsername());
        userToken = Jwts.builder()
                .setClaims(claims)
                .setIssuer(TOKEN_ISSUER)
                .setIssuedAt(Date.from(currentTime.atZone(ZoneId.systemDefault()).toInstant()))
                .setExpiration(Date.from(currentTime
                        .plusMinutes(ACCESS_TOKEN_EXP_TIME)
                        .atZone(ZoneId.systemDefault()).toInstant()))
                .signWith(SignatureAlgorithm.HS512, TOKEN_SIGNING_KEY)
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
        mockMvc.perform(get("/auth/kingdom")
                .contentType(contentType))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }
}