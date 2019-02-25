package rueppellii.backend2.tribes.resource.utility;

import lombok.Getter;
import lombok.Setter;
import rueppellii.backend2.tribes.resource.presistence.model.Resource;

import java.util.List;

@Getter
@Setter
public class ResourceDTO {

    private List<Resource> resources;
}
