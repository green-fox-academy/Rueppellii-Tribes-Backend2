package rueppellii.backend2.tribes.resource;

import org.hibernate.loader.custom.CustomQuery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ResourceRepository extends JpaRepository<Resource, Long> {

    Resource findResourceBy (ResourceType resourceType, long kingdomId);

    Resource findResourceByResource_type(ResourceType resourceType);

//    @Query(value = "SELECT type FROM resource INNER JOIN kingdoms ON resource_id = kingdom_id INNER JOIN kingdom_resource ON , nativeQuery = true)
//    List<Object> findMostOrdered();

}
