package rueppellii.backend2.tribes.user.persistence.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rueppellii.backend2.tribes.user.persistence.model.ApplicationUserRole;

import java.util.Optional;

@Repository
public interface ApplicationUserRoleRepository extends JpaRepository<ApplicationUserRole, Long> {

    Optional<ApplicationUserRole> findById(Long id);
}
