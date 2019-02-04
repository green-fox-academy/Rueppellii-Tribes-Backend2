package rueppellii.backend2.tribes.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import rueppellii.backend2.tribes.security.model.UserContext;
import rueppellii.backend2.tribes.security.model.token.JwtToken;
import rueppellii.backend2.tribes.security.model.token.JwtTokenFactory;
import rueppellii.backend2.tribes.user.persistence.dao.ApplicationUserRoleRepository;
import rueppellii.backend2.tribes.user.persistence.model.ApplicationUser;
import rueppellii.backend2.tribes.user.service.ApplicationUserService;

import java.util.List;
import java.util.stream.Collectors;

public class TestTokenProvider {

    @Autowired
    ApplicationUserRoleRepository applicationUserRoleRepository;

    private UserContext getUserContext(String username, ApplicationUserService applicationUserService) {
        ApplicationUser applicationUser = applicationUserService.findByUserName(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        List<GrantedAuthority> authorities = applicationUser.getRoles().stream().map(authority -> new SimpleGrantedAuthority(authority.getRoleEnum().authority())).collect(Collectors.toList());
        return UserContext.create(applicationUser.getUsername(), authorities);
    }

    public String getTestRefreshToken(String username, ApplicationUserService applicationUserService, JwtTokenFactory tokenFactory) {
        UserContext userContext = getUserContext(username, applicationUserService);
        JwtToken jwtToken = tokenFactory.createRefreshToken(userContext);
        String token = "Bearer " + jwtToken.getToken();
        return token;
    }

    public String getTestExpiredRefreshToken(String username, ApplicationUserService applicationUserService, JwtTokenFactory tokenFactory) {
        UserContext userContext = getUserContext(username, applicationUserService);
        long tokenLifetimeInMilliseconds = 1L;
        JwtToken jwtToken = tokenFactory.createTestRefreshToken(userContext, tokenLifetimeInMilliseconds);
        String token = "Bearer " + jwtToken.getToken();
        return token;
    }

    public String getTestAccessToken(String username, ApplicationUserService applicationUserService, JwtTokenFactory tokenFactory) {
        UserContext userContext = getUserContext(username, applicationUserService);
        JwtToken jwtToken = tokenFactory.createAccessJwtToken(userContext);
        String token = "Bearer " + jwtToken.getToken();
        return token;
    }
}
