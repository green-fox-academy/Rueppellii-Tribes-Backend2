package rueppellii.backend2.tribes.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TribesUserService {

  private TribesUserRepository userRepository;

  @Autowired
  public TribesUserService(TribesUserRepository userRepository) {
    this.userRepository = userRepository;
  }


}
