package rueppellii.backend2.tribes.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import rueppellii.backend2.tribes.TribesApplication;
import rueppellii.backend2.tribes.kingdom.Kingdom;
import rueppellii.backend2.tribes.security.model.UserContext;
import rueppellii.backend2.tribes.security.model.token.AccessJwtToken;
import rueppellii.backend2.tribes.security.model.token.JwtTokenFactory;
import rueppellii.backend2.tribes.user.persistence.model.ApplicationUser;
import rueppellii.backend2.tribes.user.persistence.model.ApplicationUserDTO;
import rueppellii.backend2.tribes.user.persistence.model.ApplicationUserRole;
import rueppellii.backend2.tribes.user.service.ApplicationUserService;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("Test")
@SpringBootTest(classes = TribesApplication.class)
public class LoginTest {

    private MockMvc mockMvc;
    private AccessJwtToken token;
    private ApplicationUserDTO applicationUserDTO;
    private PasswordEncoder encoder;
    List<ApplicationUserRole> userRoles = new ArrayList<>();
    Kingdom kingdomTest;
    ApplicationUser applicationUser;
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private WebApplicationContext webApplicationContext;

//    @Autowired
//    private WebSecurityConfig webSecurityConfig;
//
//    @Autowired
//    private RestAuthenticationEntryPoint restAuthenticationEntryPoint;

    @Autowired
    JwtTokenFactory jwtTokenFactory;

    @MockBean
    ApplicationUserService applicationUserService;

    @BeforeEach
    public void setup() {
        kingdomTest = new Kingdom();
        kingdomTest.setName("KingdomTest");
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext) //something is not good
                .apply(springSecurity())
                .build();
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        UserContext userContext = UserContext.create("test", authorities);
        token = jwtTokenFactory.createAccessJwtToken(userContext);
        applicationUser = new ApplicationUser();
        applicationUser.setId(1L);
        applicationUser.setUsername(userContext.getUsername());
        applicationUser.setPassword("password");  //don't know it needs or not
        applicationUser.setKingdom(kingdomTest);
    }

    @Test
    @WithMockUser
    public void successfulLogin () throws Exception {
        Mockito.when(applicationUserService.findByUserName("test"))
                .thenReturn(applicationUser);
        mockMvc.perform(get("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content("{\n"
                        + " \"username\":\"test\",\n"
                        + " \"password\":\"password\",\n"
                        + "}")
                .header("X-Requested-With", "XMLHttpRequest")
                .header("Content-Type", "application/json"))
                .andExpect(status().isOk());
    }
}