package rueppellii.backend2.tribes.upgrade.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UpgradeModelRepository extends JpaRepository<UpgradeModel, Long> {
}
