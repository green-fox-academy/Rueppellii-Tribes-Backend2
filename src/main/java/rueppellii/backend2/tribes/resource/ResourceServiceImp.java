package rueppellii.backend2.tribes.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import rueppellii.backend2.tribes.kingdom.Kingdom;
import rueppellii.backend2.tribes.kingdom.KingdomRepository;
import rueppellii.backend2.tribes.kingdom.KingdomService;
import rueppellii.backend2.tribes.troops.TroopService;

import java.util.List;
import java.util.Optional;

@Service
public class ResourceServiceImp {
    private ResourceRepository resourceRepository;
    private KingdomService kingdomService;
    private KingdomRepository kingdomRepository;
    private TroopService troopService;

    @Autowired
    public ResourceServiceImp(ResourceRepository resourceRepository) {
        this.resourceRepository = resourceRepository;
    }

    public Resource getResourceById(Long id) {
        Optional<Resource> resource = resourceRepository.findById(id);
        if (resource.isPresent()) {
            return resource.get();
        }
        throw new IllegalArgumentException();
    }

    public List<Resource> findAll() {
        return resourceRepository.findAll();
    }

    public ResponseEntity saveResource(Resource resource) {
        if ((resource.getType() != null) && validateType(resource)) {
            resourceRepository.save(resource);
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    public boolean validateType(Resource resource) {
        return resource.getType() == ResourceType.RESOURCE_FOOD || resource.getType() == ResourceType.RESOURCE_GOLD;
    }


    public boolean hasEnoughResource(ResourceType resourceType, Kingdom kingdom, int amountNeeded, Long kingdomId) throws Exception {
        Resource resourceToCheck = returnResource(ResourceType.RESOURCE_GOLD, kingdomId);
        int resourceAmount = resourceToCheck.getAmount();
        if (resourceAmount >= amountNeeded) {
            return true;
        }
        return false;
    }

    public Resource returnResource(ResourceType type, Long id) throws Exception {
        return resourceRepository.findByTypeAndResourcesKingdom_Id(type, id).orElseThrow(() -> new Exception());
    }

    public void minusGoldAmount(Integer gold, Long kingdomId) throws Exception {
        Resource resource = returnResource(ResourceType.RESOURCE_GOLD, kingdomId);
        resource.setAmount(resource.getAmount() - gold);
    }

    public void plusGoldAmount(Integer gold, Long kingdomId) throws Exception {
        Resource resource = returnResource(ResourceType.RESOURCE_GOLD, kingdomId);
        resource.setAmount(resource.getAmount() + gold);
    }

    
    /**
     * Use the TimeService method to calculate the resource model's timestamp and the current time difference
     */
//    public Timestamp getTimeStamp(Resource resource) {
//        return new Timestamp(resource.getUpdated_at());
//    }
    public long currentTimeDifference(long id) {
        //return TimeService's method
        return id;
    }

    //TODO add a Timeservice method for amount / minute

    /**
     * And you can multiply the difference with your model's +amount / minutes field
     */

    public int resourceAmountPerMinute(ResourceType resourceType, Resource resource) {
        Resource resourceUpdate = resourceRepository.findResourceByType(resourceType);
        int resourceAmount = resource.getAmount();
        int timepassed = 1;        //TODO add Timeservice method
        int resourceAmountPerMinute = (int) (resourceAmount * (timepassed / 10));
        return resourceAmountPerMinute;
    }

    //TODO set the amount of the resource, and the timestamp, and update the database

    public void setAmountOfGold(Kingdom kingdom, Integer goldAmount, Long kingdomId) throws Exception {
        Resource gold = returnResource(ResourceType.RESOURCE_GOLD, kingdomId);
        gold.setAmount(goldAmount);
    }

    public void setAmountOfFood(Kingdom kingdom, int foodAmount, Long kingdomId) throws Exception {
        Resource food = returnResource(ResourceType.RESOURCE_FOOD, kingdomId);
        food.setAmount(foodAmount);
    }


    //TODO if one troop is created, set food amount / minutes to actual -1

    public void updateFoodPerMinuteBasedOnTroop(Kingdom kingdom, ResourceType resourceType, Long kingdomId) throws Exception {
        Resource food = resourceRepository.findResourceByType(resourceType);
        int numberOfTroops = kingdom.getTroops().size();
        int foodAmount = food.getAmount();
        int timepassed = 10;
        int foodAmountPerMinute = foodAmount + (foodAmount / timepassed);
        if (numberOfTroops == numberOfTroops + 1) {
            foodAmount -= 1;
        }
    }


}
