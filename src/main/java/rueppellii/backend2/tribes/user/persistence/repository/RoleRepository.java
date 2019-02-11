package rueppellii.backend2.tribes.user.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rueppellii.backend2.tribes.user.persistence.model.ApplicationUserRole;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<ApplicationUserRole, Long> {

    Optional<ApplicationUserRole> findById(Long id);
}
