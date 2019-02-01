package rueppellii.backend2.tribes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import rueppellii.backend2.tribes.user.ApplicationUserRepository;

import java.util.List;
import java.util.stream.Collectors;

public class TestTokenProvider {

    @Autowired
    ApplicationUserRepository applicationUserRepository;

    public TestTokenProvider() {

    }

    private UserContext getUserContext(String username, UserService userService) {
        User user = userService.getUserOptional(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        List<GrantedAuthority> authorities = user.getRoles().stream()
                .map(authority -> new SimpleGrantedAuthority(authority.getRole().authority()))
                .collect(Collectors.toList());
        return UserContext.create(user.getEmail(), authorities);
    }
}
