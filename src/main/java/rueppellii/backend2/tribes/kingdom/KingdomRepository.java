package rueppellii.backend2.tribes.kingdom;

import org.springframework.data.jpa.repository.JpaRepository;

public interface KingdomRepository extends JpaRepository<Kingdom, Long> {

    Kingdom findByName(String name);
}
