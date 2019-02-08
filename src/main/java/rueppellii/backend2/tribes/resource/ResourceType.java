package rueppellii.backend2.tribes.resource;


public enum ResourceType {
    GOLD {
        public Resource produceResource() {
            return new Gold();
        }
    },
    FOOD {
        public Resource produceResource() {
            return new Food();
        }
    };

    public Resource produceResource() {
        return null;
    }

}
