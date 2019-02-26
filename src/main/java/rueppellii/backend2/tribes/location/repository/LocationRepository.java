package rueppellii.backend2.tribes.location.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rueppellii.backend2.tribes.location.persistence.model.Location;



public interface LocationRepository extends JpaRepository<Location, Long> {
}
