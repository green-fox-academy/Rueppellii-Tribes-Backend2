package rueppellii.backend2.tribes.resource.utility;

import rueppellii.backend2.tribes.resource.presistence.model.Resource;

public class ResourceFactory {

    public static Resource makeResource(ResourceType resourceType) {
        return resourceType.produceResource();
    }
}
