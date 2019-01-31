package rueppellii.backend2.tribes.troops;

import org.springframework.data.jpa.repository.JpaRepository;
import rueppellii.backend2.tribes.troops.models.Troop;

public interface TroopRepository extends JpaRepository<Troop, Long> {
    Troop findByTroopId(Integer troopId);
}
