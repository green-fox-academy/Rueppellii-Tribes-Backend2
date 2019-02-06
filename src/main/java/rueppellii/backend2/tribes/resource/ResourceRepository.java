package rueppellii.backend2.tribes.resource;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ResourceRepository extends JpaRepository<Resource, Long> {

    Resource findByTypeAndKingdomId (ResourceType resourceType, long kingdomId);
}
