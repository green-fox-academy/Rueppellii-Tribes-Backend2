package rueppellii.backend2.tribes.progression.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import rueppellii.backend2.tribes.kingdom.persistence.model.Kingdom;

import java.util.List;

@Repository
public interface ProgressionModelRepository extends JpaRepository<ProgressionModel, Long> {

    List<ProgressionModel> findAllByProgressKingdom(Kingdom kingdom);

}
