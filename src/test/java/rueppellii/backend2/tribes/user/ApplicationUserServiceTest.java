package rueppellii.backend2.tribes.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import rueppellii.backend2.tribes.kingdom.KingdomService;
import rueppellii.backend2.tribes.security.jwt.JwtProvider;


@ExtendWith(SpringExtension.class)
@ActiveProfiles("Test")
@SpringBootTest
class ApplicationUserServiceTest {

    private ApplicationUserService applicationUserService;
    private ApplicationUser applicationUser;

    @Mock
    private PasswordEncoder encoder;
    @Mock
    private KingdomService kingdomService;
    @Mock
    private JwtProvider jwtProvider;
    @Mock
    private AuthenticationManager authenticationManager;


    @Mock
    private ApplicationUserRepository applicationUserRepository;

    @BeforeEach
    public void init() {
        applicationUser = new ApplicationUser();
        applicationUser.setUsername("TestUser");
        applicationUser.setPassword("password");
        MockitoAnnotations.initMocks(this);
        applicationUserService = new ApplicationUserService(applicationUserRepository, encoder, kingdomService, jwtProvider, authenticationManager);
    }

}