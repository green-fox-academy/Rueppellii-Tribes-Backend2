package rueppellii.backend2.tribes.resource;

import org.springframework.http.ResponseEntity;
import rueppellii.backend2.tribes.kingdom.Kingdom;

public interface ResourceService {

    Resource getResourceById(Long id);

    ResponseEntity saveResource(Resource resource);

    boolean validateType(Resource resource);

    Resource returnResource(ResourceType type, Long id);

    void minusGoldAmount(Integer gold, Long kingdomId);

    void plusGoldAmount(Integer gold, Long kingdomId);

    void goldAmountUpdate(Long kingdomId, Resource resource);

    void setAmountOfGold(Kingdom kingdom, Integer goldAmount, Long kingdomId);

    void setAmountOfFood(Kingdom kingdom, int foodAmount, Long kingdomId);

    void updateFoodPerMinuteBasedOnTroop(Kingdom kingdom, ResourceType resourceType, Long kingdomId);

}
