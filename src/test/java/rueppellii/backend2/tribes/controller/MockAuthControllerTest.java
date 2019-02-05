package rueppellii.backend2.tribes.controller;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import rueppellii.backend2.tribes.TribesApplication;
import rueppellii.backend2.tribes.kingdom.KingdomController;
import rueppellii.backend2.tribes.kingdom.KingdomRepository;
import rueppellii.backend2.tribes.security.config.WebSecurityConfig;
import rueppellii.backend2.tribes.security.model.UserContext;
import rueppellii.backend2.tribes.security.model.token.AccessJwtToken;
import rueppellii.backend2.tribes.security.model.token.JwtTokenFactory;
import rueppellii.backend2.tribes.user.persistence.dao.ApplicationUserRepository;
import rueppellii.backend2.tribes.user.persistence.model.ApplicationUser;
import rueppellii.backend2.tribes.user.service.ApplicationUserService;
import rueppellii.backend2.tribes.user.web.UserController;

import javax.transaction.Transactional;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;


@ExtendWith(SpringExtension.class)
@ActiveProfiles("Test")
@SpringBootTest(classes = TribesApplication.class)
public class MockAuthControllerTest {


    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));

    private AccessJwtToken token;


    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;


    @MockBean
    ApplicationUserService applicationUserService;



    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .build();

    }

    @Test
    public void successfulRegistrationTest() throws Exception {
        mockMvc.perform(post("/api/user/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n"
                        + " \"username\":\"TestUser\",\n"
                        + " \"password\":\"pass1234\",\n"
                        + " \"kingdom\":\"TestKingdom\"\n"
                        + "}"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void usernameIsMissingTest() throws Exception {
        mockMvc.perform(post("/api/user/register")
                .contentType(contentType)
                .content("{\n"
                        + " \"username\":\"\",\n"
                        + " \"password\":\"pass1234\",\n"
                        + " \"kingdom\":\"TestKingdom\"\n"
                        + "}"))
                .andExpect(status().isBadRequest());
    }


    @Test
    public void usernameIsTakenTest() throws Exception {
        mockMvc.perform(post("/api/user/register")
                .contentType(contentType)
                .content("{\n"
                        + " \"username\":\"TestUser\",\n"
                        + " \"password\":\"pass1234\",\n"
                        + " \"kingdom\":\"TestKingdom\"\n"
                        + "}"))
                .andExpect(status().isConflict());

    }

    @Test
    public void passwordIsMissingTest() throws Exception {
        mockMvc.perform(post("/api/user/register")
                .contentType(contentType)
                .content("{\n"
                        + " \"username\":\"TestUser\",\n"
                        + " \"password\":\"\",\n"
                        + " \"kingdom\":\"TestKingdom\"\n"
                        + "}"))
                .andExpect(status().isBadRequest());
    }



}