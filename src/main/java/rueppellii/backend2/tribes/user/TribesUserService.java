package rueppellii.backend2.tribes.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class TribesUserService {

    private TribesUserRepository userRepository;

    @Autowired
    public TribesUserService(TribesUserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public void addUser(TribesUser tribesUser) throws IOException {
        if(userExist(tribesUser.getUsername())) {
            throw new IOException();
        } else {
            userRepository.save(tribesUser);
        }
    }

    public boolean userExist(String username) {
        return (userRepository.findByUsername(username) != null);
    }

    public TribesUser findUser(String username) {
        return userRepository.findByUsername(username);
    }
}
