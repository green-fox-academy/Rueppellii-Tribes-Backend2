package rueppellii.backend2.tribes.kingdom;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KingdomRepository extends JpaRepository<Kingdom, Long> {

    Kingdom findByName(String name);
}
