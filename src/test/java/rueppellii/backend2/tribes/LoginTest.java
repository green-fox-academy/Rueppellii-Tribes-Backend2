package rueppellii.backend2.tribes;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import rueppellii.backend2.tribes.security.auth.ajax.LoginRequest;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("Test")
public class LoginTest {

    private MockMvc mvc;

    @Autowired
    private WebApplicationContext context;

    @BeforeEach
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test
    public void unSuccessfulAuthenticationNoUser() throws Exception {
        LoginRequest jwtAuthenticationRequest = new LoginRequest("testuser", "testpassword");
        this.mvc.perform(post("/api/auth/signin")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(jwtAuthenticationRequest)))
                .andExpect(status().isBadRequest());

    }

//    @Test
//    public void unSuccessfulAuthenticationNoUser() throws Exception {
//        LoginRequest jwtAuthenticationRequest = new LoginRequest("testuser", "testpassword");
//        this.mvc.perform(post("/api/auth/signin"))
//                .header("X-requested-With", "XMLHttpRequest")
//                .conentType(MediaType.APPLICATION_JSON)
//                .content(new ObjectMapper().writeValueAsString(jwtAuthenticationRequest))
//                .andExpect(status().isBadRequest())
//                .andExpect(MockMvcResultMatchers.jsonPath("$.message"))
//                .value("username not provided")
//
//    }
}
