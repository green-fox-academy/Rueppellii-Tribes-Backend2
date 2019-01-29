package rueppellii.backend2.tribes.resource;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
public class ResourceServiceTest {

    private ResourceService resourceService;
    private Resource resource;
    private ResponseEntity r;

    @Mock
    private ResourceRepository resourceRepository;

    @BeforeEach
    void init() {
        resource = new Resource();
        MockitoAnnotations.initMocks(this);
        resourceService = new ResourceService(resourceRepository);
    }

    @Test
    void saveResourceWithNull() {
        r = new ResponseEntity(HttpStatus.BAD_REQUEST);
        assertThat(resourceService.saveResource(resource)).isEqualTo(r);
    }

    @Test
    void saveResourceWithValidType() {
        resource.setResource_type(ResourceType.RESOURCE_FOOD);
        r = new ResponseEntity(HttpStatus.OK);
        assertThat(resourceService.saveResource(resource)).isEqualTo(r);
    }

    @Test
    void saveResourceWithWrongType() {
        resource.setResource_type(ResourceType.RESOURCE_WOOD);
        r = new ResponseEntity(HttpStatus.BAD_REQUEST);
        assertThat(resourceService.saveResource(resource)).isEqualTo(r);
    }
}