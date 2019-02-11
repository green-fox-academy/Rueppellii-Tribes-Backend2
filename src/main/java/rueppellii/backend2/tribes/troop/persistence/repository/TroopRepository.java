package rueppellii.backend2.tribes.troop.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rueppellii.backend2.tribes.troop.persistence.model.Troop;


@Repository
public interface TroopRepository extends JpaRepository<Troop, Long> {
}
