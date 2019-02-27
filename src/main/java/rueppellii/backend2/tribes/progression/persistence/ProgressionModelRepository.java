package rueppellii.backend2.tribes.progression.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import rueppellii.backend2.tribes.kingdom.persistence.model.Kingdom;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProgressionModelRepository extends JpaRepository<ProgressionModel, Long> {

    List<ProgressionModel> findAllByProgressKingdom(Kingdom kingdom);
}
