package rueppellii.backend2.tribes.resource.utility;


import rueppellii.backend2.tribes.resource.presistence.model.Food;
import rueppellii.backend2.tribes.resource.presistence.model.Gold;
import rueppellii.backend2.tribes.resource.presistence.model.Resource;

public enum ResourceType {
    GOLD {
        @Override
        public Resource produceResource() {
            return new Gold();
        }
    },
    FOOD {
        @Override
        public Resource produceResource() {
            return new Food();
        }
    };

    public abstract Resource produceResource();

    public String getName(){
        return this.name();
    }
}
