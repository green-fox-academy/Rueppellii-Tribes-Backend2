package rueppellii.backend2.tribes.building.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rueppellii.backend2.tribes.building.persistence.model.Building;

public interface BuildingRepository extends JpaRepository<Building, Long> {
}
