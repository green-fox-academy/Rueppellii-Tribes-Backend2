package rueppellii.backend2.tribes.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
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
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import rueppellii.backend2.tribes.TribesApplication;
import rueppellii.backend2.tribes.kingdom.Kingdom;
import rueppellii.backend2.tribes.security.auth.ajax.LoginRequest;
import rueppellii.backend2.tribes.security.model.UserContext;
import rueppellii.backend2.tribes.security.model.token.AccessJwtToken;
import rueppellii.backend2.tribes.security.model.token.JwtTokenFactory;
import rueppellii.backend2.tribes.user.persistence.dao.ApplicationUserRepository;
import rueppellii.backend2.tribes.user.persistence.model.ApplicationUser;
import rueppellii.backend2.tribes.user.persistence.model.ApplicationUserDTO;
import rueppellii.backend2.tribes.user.persistence.model.ApplicationUserRole;
import rueppellii.backend2.tribes.user.service.ApplicationUserService;
import rueppellii.backend2.tribes.user.web.RegisterResponse;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("Test")
@SpringBootTest
public class LoginTest {

    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));


    private AccessJwtToken token;
    List<ApplicationUserRole> userRoles = new ArrayList<>();
    private Kingdom kingdomTest;
    private ApplicationUser applicationUser;

    @Autowired
    private WebApplicationContext webApplicationContext;


    @MockBean
    JwtTokenFactory jwtTokenFactory;

    @MockBean
    ApplicationUserService applicationUserService;


    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {

        kingdomTest = new Kingdom();
        kingdomTest.setName("KingdomTest");
        mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .apply(springSecurity())
                .build();
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        UserContext userContext = UserContext.create("TestUser", authorities);
        token = jwtTokenFactory.createAccessJwtToken(userContext);
        applicationUser = new ApplicationUser();
        applicationUser.setId(1L);
        applicationUser.setUsername("TestUser");
        applicationUser.setPassword("12345");  //don't know it needs or not
        applicationUser.setKingdom(kingdomTest);
    }

    @Test
    public void successfulLogin () throws Exception {
        LoginRequest jwtAuthenticationRequest = new LoginRequest("TestUser", "12345");
        mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/login")
                .header("X-Requested-With", "XMLHttpRequest")
                .contentType(contentType)
                .content(new ObjectMapper().writeValueAsString(jwtAuthenticationRequest)))
//                .content("{\n"
//                + "\"username\" : \" TestUser \",\n"
//                + "\"password\" : \" 12345 \"\n"
//                + "}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").exists())
                .andExpect(jsonPath("$.token").isNotEmpty())
                .andExpect(jsonPath("$.tokenExpiration").exists())
                .andExpect(jsonPath("$.tokenExpiration").isNumber())
                .andExpect(jsonPath("$.refreshToken").exists())
                .andExpect(jsonPath("$.refreshToken").isNotEmpty())
                .andExpect(jsonPath("$.refreshTokenExpiration").exists())
                .andExpect(jsonPath("$.refreshTokenExpiration").isNumber());
    }

}