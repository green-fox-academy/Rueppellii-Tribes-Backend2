package rueppellii.backend2.tribes.security.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import rueppellii.backend2.tribes.user.ApplicationUser;
import rueppellii.backend2.tribes.user.ApplicationUserService;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private ApplicationUserService applicationUserService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        ApplicationUser applicationUser = applicationUserService.findByUsername(username).orElseThrow(() ->
                new UsernameNotFoundException("User not found with -> username : " + username));

        return UserPrinciple.build(applicationUser);
    }
}
