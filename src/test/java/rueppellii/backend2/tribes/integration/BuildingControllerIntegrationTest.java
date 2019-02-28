package rueppellii.backend2.tribes.integration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;
import rueppellii.backend2.tribes.TestTokenProvider;
import rueppellii.backend2.tribes.security.model.token.JwtTokenFactory;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("Test")
@SpringBootTest
@SqlGroup({

        @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:data.sql")

})
public class BuildingControllerIntegrationTest {


    private MockMvc mockMvc;
    private String token;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private JwtTokenFactory jwtTokenFactory;

    @BeforeEach
    public void setUp(){
        mockMvc = webAppContextSetup(webApplicationContext)
                .apply(springSecurity())
                .build();
        TestTokenProvider testTokenProvider = new TestTokenProvider(jwtTokenFactory);
        token = testTokenProvider.createMockToken("test", "ROLE_USER");
    }

    @Test
    public void testCreateBuildingSuccess() throws Exception {
        String inputJson = "{\"type\":\"FARM\"}";
        mockMvc.perform(post("/api/kingdom/building")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", token)
                .content(inputJson))
                .andExpect(status().isOk());

    }

}
