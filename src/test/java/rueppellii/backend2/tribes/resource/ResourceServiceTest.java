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
    private ResponseEntity response;
    @Mock
    private ResourceRepository resourceRepository;

    @BeforeEach
    void init() {
        //resource = new Resource();
        MockitoAnnotations.initMocks(this);
        resourceService = new ResourceService(resourceRepository);
    }

    @Test
    void saveResourceWithNull() {
        response = new ResponseEntity(HttpStatus.BAD_REQUEST);
        assertThat(resourceService.saveResource(resource)).isEqualTo(response);
    }

    @Test
    void saveResourceWithValidType() {
        resource.setType(ResourceType.FOOD);
        response = new ResponseEntity(HttpStatus.OK);
        assertThat(resourceService.saveResource(resource)).isEqualTo(response);
    }
}