package rueppellii.backend2.tribes.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import rueppellii.backend2.tribes.kingdom.Kingdom;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

//TODO TimeService and it's methods are still missing

@Service
public class ResourceServiceImp {
    private ResourceRepository resourceRepository;


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
        return resource.getType() == ResourceType.FOOD || resource.getType() == ResourceType.GOLD;
    }


    public Resource returnResource(ResourceType type, Long id) throws Exception {
        return resourceRepository.findByTypeAndResourcesKingdom_Id(type, id).orElseThrow(() -> new Exception());
    }

    public void minusGoldAmount(Integer gold, Long kingdomId) throws Exception {
        Resource resource = returnResource(ResourceType.GOLD, kingdomId);
        resource.setAmount(resource.getAmount() - gold);
    }

    public void plusGoldAmount(Integer gold, Long kingdomId) throws Exception {
        Resource resource = returnResource(ResourceType.GOLD, kingdomId);
        resource.setAmount(resource.getAmount() + gold);
    }

    //TODO "timepassed" only hardcoded, until TimeService will be ready

    public void goldAmountUpdate(Long kingdomId, Resource resource) throws Exception {
        Resource gold  = returnResource(ResourceType.GOLD, kingdomId);
        Integer goldAmount = resource.getAmount();
        Integer timepassed = 1;
        Integer goldAmountPerMinute = goldAmount + (timepassed * gold.getAmountPerMinute());
        gold.setAmount(goldAmountPerMinute);
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        gold.setUpdated_at(timestamp);
        saveResource(gold);
    }

    public void updateFoodPerMinuteBasedOnTroop(Kingdom kingdom, ResourceType resourceType, Long kingdomId) throws Exception {
        Resource food = returnResource(ResourceType.GOLD, kingdomId);
        int numberOfTroops = kingdom.getTroops().size();
        int foodAmount = food.getAmount();
        int timepassed = 1;
        Integer foodAmountPerMinute = foodAmount + (timepassed * food.getAmountPerMinute());
        food.setAmount(foodAmountPerMinute);
        if (numberOfTroops == numberOfTroops + 1) {
            food.setAmount(food.getAmountPerMinute()-1);
        }
    }
}
