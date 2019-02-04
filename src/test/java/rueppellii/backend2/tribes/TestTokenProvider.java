package rueppellii.backend2.tribes;

import org.springframework.beans.factory.annotation.Autowired;
import rueppellii.backend2.tribes.user.persistence.dao.ApplicationUserRepository;

public class TestTokenProvider {

    @Autowired
    private ApplicationUserRepository userRepository;

    public TestTokenProvider() {

    }

//    private UserContext getUserContext(String username, UserService userService) {
//        ApplicationUser user = userService.getByUsername(username)
//                .orElseThrow(() -> new UsernameNotFoundException("User not found waze"));
//        List<GrantedAuthority> authorities = user.getRoles().stream()
//                .map(authority -> new SimpleGrantedAuthority(authority.getRole().authority()))
//                .collect(Collectors.toList());
//        return UserContext.create(user.getUsername(), authorities);
//    }
//
//    public String getTestRefreshToken(String username, UserService userService, JwtTokenFactory tokenFactory) {
//        UserContext userContext = getUserContext(username, userService);
//        TokenDTO tokenDTO = (TokenDTO) tokenFactory.createRefreshToken(userContext);
//        String token = "Bearer " + tokenDTO.getJwtToken().getToken();
//        return token;
//    }
//
//    public String getTestExpiredRefreshToken(String username, UserService userService, JwtTokenFactory tokenFactory) {
//        UserContext userContext = getUserContext(username, userService);
//        Long tokenLifetimeInMilliseconds = 1L;
//        TokenDTO tokenDTO = tokenFactory.createTestRefreshToken(userContext, tokenLifetimeInMilliseconds);
//        String token = "Bearer " + tokenDTO.getJwtToken().getToken();
//        return token;
//    }
//
//    public String getTestAccessToken(String username, UserService userService, JwtTokenFactory tokenFactory) {
//        UserContext userContext = getUserContext(username, userService);
//        TokenDTO tokenDTO = tokenFactory.createAccessJwtToken(userContext);
//        String token = "Bearer " + tokenDTO.getJwtToken().getToken();
//        return token;
//    }
}
