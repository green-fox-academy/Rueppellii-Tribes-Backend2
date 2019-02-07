package rueppellii.backend2.tribes.resource;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ResourceRepository extends JpaRepository<Resource, Long> {

//    Resource findByResource_type (ResourceType resourceType, long kingdomId);
}
