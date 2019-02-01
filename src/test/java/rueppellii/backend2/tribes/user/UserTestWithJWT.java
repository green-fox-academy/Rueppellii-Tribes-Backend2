package rueppellii.backend2.tribes.user;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("Test")
@SpringBootTest
public class UserTestWithJWT {

    private ApplicationUserService applicationUserService;

}
