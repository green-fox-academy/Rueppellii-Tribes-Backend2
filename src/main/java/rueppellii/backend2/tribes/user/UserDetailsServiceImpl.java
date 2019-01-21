package rueppellii.backend2.tribes.user;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static java.util.Collections.emptyList;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
  private TribesUserRepository tribesUserRepository;

  public UserDetailsServiceImpl(TribesUserRepository tribesUserRepository) {
    this.tribesUserRepository = tribesUserRepository;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    TribesUser tribesUser = tribesUserRepository.findByUsername(username);
    if(tribesUser != null) {
      return new User(tribesUser.getUsername(), tribesUser.getPassword(), emptyList());
    } else {
      throw new UsernameNotFoundException("user " + username + " not found!");
    }
  }
}
