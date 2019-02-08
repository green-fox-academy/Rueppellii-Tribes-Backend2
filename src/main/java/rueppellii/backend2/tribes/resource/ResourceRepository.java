package rueppellii.backend2.tribes.resource;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ResourceRepository extends JpaRepository<Resource, Long> {

    Resource findResourceByType (ResourceType resourceType);

    Optional<Resource> findByTypeAndResourcesKingdom_Id(ResourceType type, Long id);
}
