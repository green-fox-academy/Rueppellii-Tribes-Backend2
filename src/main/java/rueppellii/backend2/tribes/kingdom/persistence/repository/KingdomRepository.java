package rueppellii.backend2.tribes.kingdom.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import rueppellii.backend2.tribes.kingdom.persistence.model.Kingdom;

import java.util.List;
import java.util.Optional;

@Repository
public interface KingdomRepository extends JpaRepository<Kingdom, Long> {

    Kingdom findByName(String name);

    Optional<Kingdom> findByApplicationUser_Username(String name);

    List<Kingdom> findAll();
}
