package rueppellii.backend2.tribes.resource.presistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rueppellii.backend2.tribes.resource.utility.ResourceType;
import rueppellii.backend2.tribes.resource.presistence.model.Resource;

import java.util.Optional;

public interface ResourceRepository extends JpaRepository<Resource, Long> {

    Resource findResourceByType (ResourceType resourceType);

    Optional<Resource> findByTypeAndResourcesKingdom_Id(ResourceType type, Long id);
}
