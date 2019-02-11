package rueppellii.backend2.tribes.user.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rueppellii.backend2.tribes.user.persistence.model.ApplicationUser;

import java.util.Optional;

@Repository
public interface ApplicationUserRepository extends JpaRepository<ApplicationUser, Long> {
    Optional<ApplicationUser> findByUsername(String username);

    Boolean existsByUsername(String username);

}
