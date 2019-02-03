package rueppellii.backend2.tribes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import rueppellii.backend2.tribes.security.UserService;
import rueppellii.backend2.tribes.security.model.UserContext;
import rueppellii.backend2.tribes.security.model.token.JwtTokenFactory;
import rueppellii.backend2.tribes.security.model.token.TokenDTO;
import rueppellii.backend2.tribes.user.persistence.dao.ApplicationUserRepository;
import rueppellii.backend2.tribes.user.persistence.model.ApplicationUser;
import rueppellii.backend2.tribes.user.service.ApplicationUserService;

import java.util.List;
import java.util.stream.Collectors;

public class TestTokenProvider {

    @Autowired
    private ApplicationUserRepository userRepository;

    public TestTokenProvider() {

    }

    private UserContext getUserContext(String username, UserService userService) {
        ApplicationUser user = userService.getByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found waze"));
        List<GrantedAuthority> authorities = user.getRoles().stream()
                .map(authority -> new SimpleGrantedAuthority(authority.getRole().authority()))
                .collect(Collectors.toList());
        return UserContext.create(user.getUsername(), authorities);
    }

    public String getTestRefreshToken(String username, UserService userService, JwtTokenFactory tokenFactory) {
        UserContext userContext = getUserContext(username, userService);
        TokenDTO tokenDTO = (TokenDTO) tokenFactory.createRefreshToken(userContext);
        String token = "Bearer " + tokenDTO.getJwtToken().getToken();
        return token;
    }

    public String getTestExpiredRefreshToken(String username, UserService userService, JwtTokenFactory tokenFactory) {
        UserContext userContext = getUserContext(username, userService);
        Long tokenLifetimeInMilliseconds = 1L;
        TokenDTO tokenDTO = tokenFactory.createTestRefreshToken(userContext, tokenLifetimeInMilliseconds);
        String token = "Bearer " + tokenDTO.getJwtToken().getToken();
        return token;
    }

    public String getTestAccessToken(String username, UserService userService, JwtTokenFactory tokenFactory) {
        UserContext userContext = getUserContext(username, userService);
        TokenDTO tokenDTO = tokenFactory.createAccessJwtToken(userContext);
        String token = "Bearer " + tokenDTO.getJwtToken().getToken();
        return token;
    }
}
