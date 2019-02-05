package rueppellii.backend2.tribes;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import rueppellii.backend2.tribes.security.model.UserContext;
import rueppellii.backend2.tribes.security.model.token.AccessJwtToken;
import rueppellii.backend2.tribes.security.model.token.JwtTokenFactory;

import java.util.ArrayList;
import java.util.List;

public class TestTokenProvider {

    private JwtTokenFactory jwtTokenFactory;

    public TestTokenProvider(JwtTokenFactory jwtTokenFactory) {
        this.jwtTokenFactory = jwtTokenFactory;
    }

    public String createMockToken(String username, String role) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(role));
        UserContext userContext = UserContext.create(username, authorities);
        AccessJwtToken token = jwtTokenFactory.createAccessJwtToken(userContext);
        return "Bearer " + token.getToken();
    }
}