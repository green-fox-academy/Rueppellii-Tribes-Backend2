package rueppellii.backend2.tribes.building;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface BuildingRepository extends CrudRepository<Building, Long> {

    Optional<Building> findByIdAndBuildingsKingdom_Id(Long id, Long kingdomId);
}
