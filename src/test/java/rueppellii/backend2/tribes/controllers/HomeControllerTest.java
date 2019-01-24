package rueppellii.backend2.tribes.controllers;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
class HomeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getHello_SuccessWithOutName() throws Exception {

        this.mockMvc.perform(get("/")
                .accept(MediaType.TEXT_PLAIN))
                .andExpect(content().string("Hello"));
    }

    @Test
    void getHello_SuccessWithName() throws Exception {

        this.mockMvc.perform(get("/")
                .accept(MediaType.TEXT_PLAIN)
                .param("name", "Archie"))
                .andExpect(content().string("Hello Archie"));
    }
}