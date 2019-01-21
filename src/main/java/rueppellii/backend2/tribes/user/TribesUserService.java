package rueppellii.backend2.tribes.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TribesUserService {

    private TribesUserRepository tribesUserRepository;

    @Autowired
    public TribesUserService(TribesUserRepository tribesUserRepository) {
        this.tribesUserRepository = tribesUserRepository;
    }
}