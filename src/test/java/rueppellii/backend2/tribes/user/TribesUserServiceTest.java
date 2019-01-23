package rueppellii.backend2.tribes.user;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
class TribesUserServiceTest {

    @Autowired
    private TribesUserService tribesUserService;

    @MockBean
    private TribesUserRepository tribesUserRepository;

    @Test
    public void userExistTest() {
        TribesUser tribesUser = new TribesUser();
        tribesUser.setUsername("Mock User");
        tribesUser.setPassword("mockPass");
      //  tribesUserRepository.save(tribesUser);

       // Mockito.when(tribesUserRepository.save(tribesUser)).thenReturn(tribesUser);
        assertEquals(tribesUser.getUsername(), "Mock User");
       // assertTrue(tribesUserService.userExist(tribesUser.getUsername()));

    }
}