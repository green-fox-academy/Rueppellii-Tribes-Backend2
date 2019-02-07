package rueppellii.backend2.tribes.resource;


public enum ResourceType {
    RESOURCE_GOLD("gold"){
    },

    RESOURCE_FOOD("food"),

    //ONLY FOR TEST PURPOSES
    RESOURCE_WOOD("wood");

    public Resource addResource() {
        return null;
    }


    private final String typeName;

    ResourceType(String typeName) {
        this.typeName = typeName;
    }

    public String getTypeName() {
        return typeName;
    }

//TODO add buildresource method like in building

    //TODO add this to the ServiceImpl like in the troops
    //TODO add the resources to classes as well
    //TODO create separate classes for Food and Gold

    public static ResourceType getTypeByName(String typeName) {
        for (ResourceType type : values()) {
            if (typeName.equals(type.getTypeName())) {
                return type;
            }
        }
        return null;
    }
}
