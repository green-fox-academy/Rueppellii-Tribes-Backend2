package rueppellii.backend2.tribes.location.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rueppellii.backend2.tribes.location.persistence.model.Location;

@Repository
public interface LocationRepository extends JpaRepository<Location, String> {
}
