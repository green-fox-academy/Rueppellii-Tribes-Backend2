package rueppellii.backend2.tribes.integrationTests.troop.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rueppellii.backend2.tribes.kingdom.persistence.model.Kingdom;
import rueppellii.backend2.tribes.integrationTests.troop.persistence.model.Troop;

import java.util.List;


@Repository
public interface TroopRepository extends JpaRepository<Troop, Long> {
    List<Troop> findAllByLevelAndAndTroopsKingdom(Integer level, Kingdom kingdom);
}
