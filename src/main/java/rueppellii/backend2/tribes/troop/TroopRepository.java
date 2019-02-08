package rueppellii.backend2.tribes.troop;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TroopRepository extends JpaRepository<Troop, Long> {

    Optional<Troop> findByIdAndKingdom_Id(Long id, Long kingdomId);
}
