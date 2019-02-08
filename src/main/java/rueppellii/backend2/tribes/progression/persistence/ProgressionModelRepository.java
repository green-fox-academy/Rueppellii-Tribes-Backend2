package rueppellii.backend2.tribes.progression.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProgressionModelRepository extends JpaRepository<ProgressionModel, Long> {
}
