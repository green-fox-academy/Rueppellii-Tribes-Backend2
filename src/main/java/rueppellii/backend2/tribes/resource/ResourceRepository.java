package rueppellii.backend2.tribes.resource;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ResourceRepository extends JpaRepository<Resource, Long> {

    Resource findByResource_typeAndResourcesKingdom_Id (ResourceType resourceType, long kingdomId);
}
