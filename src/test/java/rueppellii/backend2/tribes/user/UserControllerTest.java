package rueppellii.backend2.tribes.user;

import org.hamcrest.core.Is;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import rueppellii.backend2.tribes.kingdom.persistence.model.Kingdom;
import rueppellii.backend2.tribes.user.persistence.model.ApplicationUser;
import rueppellii.backend2.tribes.user.persistence.model.ApplicationUserDTO;
import rueppellii.backend2.tribes.user.service.ApplicationUserService;
import rueppellii.backend2.tribes.user.web.RegisterResponse;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("Test")
@SpringBootTest
public class UserControllerTest {

    private MockMvc mvc;
    private ApplicationUser applicationUser;
    private Kingdom kingdom;
    private RegisterResponse registerResponse;

    @MockBean
    ApplicationUserService applicationUserService;

    @Autowired
    private WebApplicationContext context;

    @BeforeEach
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(this.context)
                .alwaysDo(print())
                .apply(SecurityMockMvcConfigurers.springSecurity())
                .build();
        kingdom = new Kingdom();
        kingdom.setName("TestKingdom");
        applicationUser = new ApplicationUser();
        applicationUser.setUsername("TestUser");
        applicationUser.setPassword("12345");
        applicationUser.setKingdom(kingdom);

    }

    @Test
    public void successfulRegistrationWithKingdomName() throws Exception {
        registerResponse = new RegisterResponse();
        registerResponse.setId(1L);
        registerResponse.setUsername("TestUser");
        registerResponse.setKingdomId(1L);
        Mockito.when(applicationUserService.registerApplicationUser(Mockito.any(ApplicationUserDTO.class))).thenReturn(registerResponse);
        mvc.perform(post("/api/user/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n"
                        + " \"username\":\"TestUser\",\n"
                        + " \"password\":\"12345\",\n"
                        + " \"kingdomName\":\"TestKingdom\"\n"
                        + "}"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.id", Is.is(1)))
                .andExpect(jsonPath("$.username", Is.is("TestUser")))
                .andExpect(jsonPath("$.kingdomId", Is.is(1)))
                .andExpect(status().isOk())
                .andDo(print());

    }

    @Test
    public void successfulRegistrationWithOUTKingdomName() throws Exception {
        mvc.perform(post("/api/user/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n"
                        + " \"username\":\"TestUser\",\n"
                        + " \"password\":\"12345\",\n"
                        + " \"kingdomName\":\"\"\n"
                        + "}"))
                .andExpect(status().isOk());
    }

    @Test
    public void unSuccessfulRegistrationUserNameIsTaken() throws Exception {
        mvc.perform(post("/api/user/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n"
                        + " \"username\":\"TestUser\",\n"
                        + " \"password\":\"12345\",\n"
                        + " \"kingdomName\":\"\"\n"
                        + "}"))
                .andExpect(status().isOk());
    }


}
