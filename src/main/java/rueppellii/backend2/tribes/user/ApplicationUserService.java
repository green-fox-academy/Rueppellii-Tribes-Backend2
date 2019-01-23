package rueppellii.backend2.tribes.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ApplicationUserService {

    private ApplicationUserRepository userRepository;

    @Autowired
    public ApplicationUserService(ApplicationUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<ApplicationUser> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public Boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    public void save(ApplicationUser applicationUser) {
        userRepository.save(applicationUser);
    }

}
