package rueppellii.backend2.tribes.building.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rueppellii.backend2.tribes.building.persistence.model.Building;

@Repository
public interface BuildingRepository extends JpaRepository<Building, Long> {
}
