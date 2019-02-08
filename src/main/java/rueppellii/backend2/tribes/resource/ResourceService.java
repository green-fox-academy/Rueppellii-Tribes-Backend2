package rueppellii.backend2.tribes.resource;

import org.springframework.http.ResponseEntity;
<<<<<<< HEAD
import rueppellii.backend2.tribes.kingdom.Kingdom;

public interface ResourceService {
=======
import org.springframework.stereotype.Service;
import rueppellii.backend2.tribes.kingdom.KingdomRepository;
import rueppellii.backend2.tribes.kingdom.KingdomService;
import rueppellii.backend2.tribes.resource.exception.NoResourceException;

import java.util.List;
>>>>>>> fcb1b8ab498ab5e5c146e360ae43de2a5c15ef55

    Resource getResourceById(Long id);

    ResponseEntity saveResource(Resource resource);

<<<<<<< HEAD
    boolean validateType(Resource resource);
=======
    public Resource provideResource(ResourceType type) {
        return type.produceResource();
    }
>>>>>>> fcb1b8ab498ab5e5c146e360ae43de2a5c15ef55

    Resource returnResource(ResourceType type, Long id);

    void minusGoldAmount(Integer gold, Long kingdomId);

<<<<<<< HEAD
    void plusGoldAmount(Integer gold, Long kingdomId);

    void goldAmountUpdate(Long kingdomId, Resource resource);

    void setAmountOfGold(Kingdom kingdom, Integer goldAmount, Long kingdomId);

    void setAmountOfFood(Kingdom kingdom, int foodAmount, Long kingdomId);

    void updateFoodPerMinuteBasedOnTroop(Kingdom kingdom, ResourceType resourceType, Long kingdomId);

=======
    public boolean validateType(Resource resource) {
        return resource.getType() == ResourceType.FOOD || resource.getType() == ResourceType.GOLD;
    }

    public Resource returnResource(ResourceType type, Long id) throws NoResourceException {
        return resourceRepository.findByTypeAndResourcesKingdom_Id(type, id).orElseThrow(() -> new NoResourceException("No resource found!"));
    }

    public void minusGoldAmount(Integer gold, Long kingdomId) throws NoResourceException {
        Resource resource = returnResource(ResourceType.GOLD, kingdomId);
        resource.setAmount(resource.getAmount() - gold);
        saveResource(resource);
    }

    public void plusGoldAmount(Integer gold, Long kingdomId) throws NoResourceException {
        Resource resource = returnResource(ResourceType.GOLD, kingdomId);
        resource.setAmount(resource.getAmount() + gold);
        saveResource(resource);
    }
>>>>>>> fcb1b8ab498ab5e5c146e360ae43de2a5c15ef55
}
