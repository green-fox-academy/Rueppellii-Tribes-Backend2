package rueppellii.backend2.tribes.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import rueppellii.backend2.tribes.kingdom.KingdomService;
import rueppellii.backend2.tribes.message.request.SignUpForm;
import rueppellii.backend2.tribes.message.response.SignUpResponse;
import rueppellii.backend2.tribes.user.persistence.dao.ApplicationUserRepository;
import rueppellii.backend2.tribes.user.persistence.model.ApplicationUser;
import rueppellii.backend2.tribes.user.service.ApplicationUserService;

import static org.mockito.Mockito.when;


@ExtendWith(SpringExtension.class)
@ActiveProfiles("Test")
@SpringBootTest
class ApplicationUserServiceTest {


    private ApplicationUserService applicationUserService;

    @Mock
    private ApplicationUser applicationUser;

    private ResponseEntity response;

    private SignUpForm signUpForm;

    private SignUpResponse signUpResponse;


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
        signUpForm = new SignUpForm();
        signUpResponse = new SignUpResponse();
        applicationUser = new ApplicationUser();
        MockitoAnnotations.initMocks(this);
        applicationUserService = new ApplicationUserService(applicationUserRepository, encoder, kingdomService, jwtProvider, authenticationManager);
    }


    @Test
    void saveApplicationUserTest_WithKingdomName() throws Exception {

        signUpForm.setUsername("TestUser");
        signUpForm.setPassword("TestPW");
        signUpForm.setKingdom("TestKingdom");
        signUpResponse.setId(1L);
        signUpResponse.setUsername("TestUser");
        signUpResponse.setKingdomId(1L);
        when(applicationUserService.saveApplicationUser(signUpForm)).thenReturn(signUpResponse);
    }

    @Test
    void saveApplicationUserTest_WithOUT_KingdomName() throws Exception {

        signUpForm.setUsername("TestUser");
        signUpForm.setPassword("TestPW");
        signUpForm.setKingdom("");
        signUpResponse.setId(1L);
        signUpResponse.setUsername("TestUser");
        signUpResponse.setKingdomId(1L);
        when(applicationUserService.saveApplicationUser(signUpForm)).thenReturn(signUpResponse);

    }

}