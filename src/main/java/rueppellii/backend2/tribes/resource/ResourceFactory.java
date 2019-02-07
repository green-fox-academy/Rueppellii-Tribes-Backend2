package rueppellii.backend2.tribes.resource;

public class ResourceFactory {

    public static Resource provideResource(ResourceType resourceType) {
        return resourceType.addResource();
    }

}
