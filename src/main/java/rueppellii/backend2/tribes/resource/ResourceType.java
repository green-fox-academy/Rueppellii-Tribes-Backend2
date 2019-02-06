package rueppellii.backend2.tribes.resource;


public enum ResourceType {
    RESOURCE_GOLD("gold"), RESOURCE_FOOD("food"),

    //ONLY FOR TEST PURPOSES
    RESOURCE_WOOD("wood");

    private final String typeName;

    ResourceType(String typeName) {
        this.typeName = typeName;
    }

    public String getTypeName() {
        return typeName;
    }

    public static ResourceType getTypeByName(String typeName) {
        for (ResourceType type : values()) {
            if (typeName.equals(type.getTypeName())) {
                return type;
            }
        }
        return null;
    }


}
