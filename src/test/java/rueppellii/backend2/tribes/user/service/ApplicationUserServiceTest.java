package rueppellii.backend2.tribes.user.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.MethodArgumentNotValidException;
import rueppellii.backend2.tribes.kingdom.persistence.model.Kingdom;
import rueppellii.backend2.tribes.kingdom.service.KingdomService;
import rueppellii.backend2.tribes.user.exceptions.UserNameIsTakenException;
import rueppellii.backend2.tribes.user.exceptions.UserRoleNotFoundException;
import rueppellii.backend2.tribes.user.persistence.model.ApplicationUser;
import rueppellii.backend2.tribes.user.persistence.repository.ApplicationUserRepository;
import rueppellii.backend2.tribes.user.util.ApplicationUserDTO;
import rueppellii.backend2.tribes.user.persistence.model.ApplicationUserRole;
import rueppellii.backend2.tribes.user.util.Role;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


class ApplicationUserServiceTest {

    @InjectMocks
    private ApplicationUserService applicationUserService;

    private final String USER_NAME = "TestUser";
    private final String PASSWORD = "TestPW";

    @Mock
    private KingdomService kingdomService;
    @Mock
    private ApplicationUserRepository applicationUserRepository;
    @Mock
    private PasswordEncoder encoder;
    @Mock
    private RoleService roleService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testRegisterApplicationUserSuccess() throws MethodArgumentNotValidException, UserNameIsTakenException, UserRoleNotFoundException, IllegalArgumentException, IOException {
        ApplicationUserDTO applicationUserDTO = new ApplicationUserDTO();
        applicationUserDTO.setUsername(USER_NAME);
        applicationUserDTO.setPassword(PASSWORD);
        Kingdom kingdom = new Kingdom();
        kingdom.setName("TestUser's Kingdom");
        ApplicationUserRole applicationUserRole = new ApplicationUserRole();
        applicationUserRole.setRoleEnum(Role.USER);
        when(roleService.findById(1L)).thenReturn(applicationUserRole);
        when(kingdomService.createNewKingdomAndSetNameIfNotExists(applicationUserDTO)).thenReturn(kingdom);
        applicationUserService.registerApplicationUser(applicationUserDTO);

        assertEquals(kingdom.getName(), applicationUserDTO.getUsername() + "'s Kingdom");

        verify(applicationUserRepository, times(1)).existsByUsername(applicationUserDTO.getUsername());
        verify(applicationUserRepository, times(1)).save(any(ApplicationUser.class));
        verify(roleService, times(1)).findById(1L);
    }

    @Test
    void testRegisterApplicationUserSuccessWithKingdomName() throws MethodArgumentNotValidException, UserNameIsTakenException, UserRoleNotFoundException, IllegalArgumentException, IOException {
        ApplicationUserDTO applicationUserDTO = new ApplicationUserDTO();
        applicationUserDTO.setUsername(USER_NAME);
        applicationUserDTO.setPassword(PASSWORD);
        applicationUserDTO.setKingdomName("TestKingdom");
        Kingdom kingdom = new Kingdom();
        kingdom.setName("TestKingdom");
        ApplicationUserRole applicationUserRole = new ApplicationUserRole();
        applicationUserRole.setRoleEnum(Role.USER);
        when(roleService.findById(1L)).thenReturn(applicationUserRole);
        when(kingdomService.createNewKingdomAndSetNameIfNotExists(applicationUserDTO)).thenReturn(kingdom);
        applicationUserService.registerApplicationUser(applicationUserDTO);

        assertEquals(kingdom.getName(), applicationUserDTO.getKingdomName());

        verify(applicationUserRepository, times(1)).existsByUsername(applicationUserDTO.getUsername());
        verify(applicationUserRepository, times(1)).save(any(ApplicationUser.class));
        verify(roleService, times(1)).findById(1L);
    }

    @Test
    void testRegisterApplicationUserFailUsernameIsTaken() throws UserRoleNotFoundException, MethodArgumentNotValidException, UserNameIsTakenException {
        ApplicationUserDTO applicationUserDTO = new ApplicationUserDTO();
        applicationUserDTO.setUsername(USER_NAME);
        applicationUserDTO.setPassword(PASSWORD);
        Kingdom kingdom = new Kingdom();
        kingdom.setName("TestUser's Kingdom");
        ApplicationUserRole applicationUserRole = new ApplicationUserRole();
        applicationUserRole.setRoleEnum(Role.USER);
        when(applicationUserRepository.existsByUsername(applicationUserDTO.getUsername())).thenReturn(true);

        assertThrows(UserNameIsTakenException.class, () -> applicationUserService.registerApplicationUser(applicationUserDTO));
    }


}