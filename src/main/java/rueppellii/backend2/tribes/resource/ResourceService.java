package rueppellii.backend2.tribes.resource;

import rueppellii.backend2.tribes.kingdom.Kingdom;

public interface ResourceService {

    Resource getResourceById(Long id);

    Resource returnResource(ResourceType type, Long id);

    void minusGoldAmount(Integer gold, Long kingdomId);

    void plusGoldAmount(Integer gold, Long kingdomId);

    int resourceAmountPerMinute(ResourceType resourceType, Resource resource);

    void setAmountOfGold(Kingdom kingdom, Integer goldAmount, Long kingdomId);

    void setAmountOfFood(Kingdom kingdom, int foodAmount, Long kingdomId);

    void updateFoodPerMinuteBasedOnTroop(Kingdom kingdom, ResourceType resourceType, Long kingdomId);

}
