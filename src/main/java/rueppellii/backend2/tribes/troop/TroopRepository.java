package rueppellii.backend2.tribes.troop;

import org.springframework.data.jpa.repository.JpaRepository;
import rueppellii.backend2.tribes.troop.models.Troop;

public interface TroopRepository extends JpaRepository<Troop, Long> {

}
