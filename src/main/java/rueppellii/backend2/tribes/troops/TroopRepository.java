package rueppellii.backend2.tribes.kingdom.troops;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TroopRepository extends JpaRepository<Troop, Long> {
    Troop findByTroopId(Integer troopId);
}
