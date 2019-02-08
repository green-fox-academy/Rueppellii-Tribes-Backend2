package rueppellii.backend2.tribes.resource.utility;


import rueppellii.backend2.tribes.resource.presistence.model.Food;
import rueppellii.backend2.tribes.resource.presistence.model.Gold;
import rueppellii.backend2.tribes.resource.presistence.model.Resource;

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
