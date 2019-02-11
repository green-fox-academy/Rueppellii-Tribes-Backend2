package rueppellii.backend2.tribes.troop.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rueppellii.backend2.tribes.troop.persistence.model.Troop;

import java.util.Optional;

public interface TroopRepository extends JpaRepository<Troop, Long> {
}
