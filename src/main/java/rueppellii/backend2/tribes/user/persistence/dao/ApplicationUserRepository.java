package rueppellii.backend2.tribes.user.persistence.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import rueppellii.backend2.tribes.user.persistence.model.ApplicationUser;

import java.util.Optional;

@Repository
public interface ApplicationUserRepository extends JpaRepository<ApplicationUser, Long> {
    Optional<ApplicationUser> findByUsername(String username);

    Boolean existsByUsername(String username);

    @Query("select id from ApplicationUser where username= :username")
    int getIdFromDb(@Param("username") String username);

    int findById (String username);

}
