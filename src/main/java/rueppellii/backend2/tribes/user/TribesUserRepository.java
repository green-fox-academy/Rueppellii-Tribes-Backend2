package rueppellii.backend2.tribes.user;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TribesUserRepository extends JpaRepository<TribesUser, Long> {
  TribesUser findByUsername(String username);
}
