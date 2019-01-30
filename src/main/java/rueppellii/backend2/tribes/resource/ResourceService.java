package rueppellii.backend2.tribes.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ResourceService {
   private ResourceRepository resourceRepository;

    @Autowired
    public ResourceService(ResourceRepository resourceRepository) {
        this.resourceRepository = resourceRepository;
    }

    public Resource getResourceById(Long id) {
        Optional<Resource> resource = resourceRepository.findById(id);
        if (resource.isPresent()) {
            return resource.get();
        }
        throw new IllegalArgumentException();
    }

    public List<Resource> findAll() {
        return resourceRepository.findAll();
    }

    public ResponseEntity saveResource(Resource resource) {
        if ((resource.getResource_type() != null) && validateType(resource)) {
            resourceRepository.save(resource);
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    public boolean validateType(Resource resource) {
        return resource.getResource_type() == ResourceType.RESOURCE_FOOD || resource.getResource_type() == ResourceType.RESOURCE_GOLD;
    }

}
