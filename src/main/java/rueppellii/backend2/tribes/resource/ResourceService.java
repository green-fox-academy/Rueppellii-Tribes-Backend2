package rueppellii.backend2.tribes.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import rueppellii.backend2.tribes.kingdom.Kingdom;
import rueppellii.backend2.tribes.kingdom.KingdomRepository;
import rueppellii.backend2.tribes.kingdom.KingdomService;
import rueppellii.backend2.tribes.troop.TroopService;

import java.util.List;
import java.util.Optional;

@Service
public class ResourceService {
    private ResourceRepository resourceRepository;
    private KingdomService kingdomService;
    private KingdomRepository kingdomRepository;
    private TroopService troopService;

    @Autowired
    public ResourceService(ResourceRepository resourceRepository, TroopService troopService) {
        this.resourceRepository = resourceRepository;
        this.troopService = troopService;
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
        if ((resource.getResource_type() != null) && validateType(resource)) {
            resourceRepository.save(resource);
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    public boolean validateType(Resource resource) {
        return resource.getResource_type() == ResourceType.RESOURCE_FOOD || resource.getResource_type() == ResourceType.RESOURCE_GOLD;
    }

    public Integer usingResource(Resource resource) {
        if (validateType(resource)) {
            return resource.getAmount() - 10;           //amount added only for example
        }
        return null;
    }

//    public boolean hasEnoughResource(ResourceType resourceType, Kingdom kingdom, int amountNeeded) {
//        Resource resourceToCheck = resourceRepository.findByResource_typeAndKingdom_Id(resourceType, kingdom.getId());
//        if (resourceToCheck.getAmount() >= amountNeeded) {
//            return true;
//        }
//        return false;
//    }


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

    public int goldAmountPerMinute(Kingdom kingdom) {
        Resource gold = resourceRepository.findByResource_typeAndResourcesKingdom_Id(ResourceType.RESOURCE_GOLD, kingdom.getId());
        int goldAmount = gold.getAmount();
        int timepassed = 10;        //TODO add Timeservice method
        int goldamountPerMinute = goldAmount + goldAmount / timepassed;
        return goldamountPerMinute;
    }

    //TODO set the amount of the resource, and the timestamp, and update the database

    public void setAmountOfGold(Kingdom kingdom, int goldAmount) {
        Resource gold = resourceRepository.findByResource_typeAndResourcesKingdom_Id(ResourceType.RESOURCE_FOOD, kingdom.getId());
        gold.setAmount(goldAmount);
        kingdomRepository.save(kingdom);
    }

    public void setAmountOfFood(Kingdom kingdom, int foodAmount) {
        Resource food = resourceRepository.findByResource_typeAndResourcesKingdom_Id(ResourceType.RESOURCE_GOLD, kingdom.getId());
        food.setAmount(foodAmount);
        kingdomRepository.save(kingdom);
    }

    /**
     * if one troop is created, set food amount / minutes to actual -1
     */
    public void updateFoodPerMinuteBasedOnTroop(Kingdom kingdom, ResourceType resourceType) {
        Resource food = resourceRepository.findByResource_typeAndResourcesKingdom_Id(resourceType, kingdom.getId());
        int numberOfTroops = kingdom.getTroops().size();
        int foodAmount = food.getAmount();
        int timepassed = 10;        //TODO add Timeservice method
        int foodAmountPerMinute = foodAmount + foodAmount / timepassed;
        if (numberOfTroops == numberOfTroops + 1){
            foodAmount -= 1;
        }
    }



}
