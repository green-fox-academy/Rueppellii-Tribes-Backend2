package rueppellii.backend2.tribes.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import rueppellii.backend2.tribes.kingdom.KingdomService;
import rueppellii.backend2.tribes.security.jwt.JwtProvider;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("Test")
@SpringBootTest
class ApplicationUserServiceTest {

    private ApplicationUserService applicationUserService;
    private PasswordEncoder encoder;
    private KingdomService kingdomService;
    private JwtProvider jwtProvider;
    private AuthenticationManager authenticationManager;
    private ApplicationUser applicationUser;

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

    @Test
    void findByUsername() {
    }

    @Test
    void existsByUsername() {
    }

    @Test
    void saveApplicationUser() {
    }

    @Test
    void authenticateApplicationUser() {
    }
}