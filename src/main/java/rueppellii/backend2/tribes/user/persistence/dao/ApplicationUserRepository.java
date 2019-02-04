package rueppellii.backend2.tribes.user.persistence.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rueppellii.backend2.tribes.user.persistence.model.ApplicationUser;

import java.util.Optional;

@Repository
public interface ApplicationUserRepository extends JpaRepository<ApplicationUser, Long> {
    Optional<ApplicationUser> findByUsername(String username);

    Boolean existsByUsername(String username);
<<<<<<< HEAD
    Long getByUsername(String username);
=======

>>>>>>> 4fba8a431eccdaf170bf5c224994b9ad42154acf
}
