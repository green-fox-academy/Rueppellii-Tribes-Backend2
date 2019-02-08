package rueppellii.backend2.tribes.resource;

public class ResourceFactory {

    public static Resource produceResource(ResourceType resourceType) {
        return resourceType.produceResource();
    }
}
