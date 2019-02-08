package rueppellii.backend2.tribes.resource.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import rueppellii.backend2.tribes.gameUtility.timeService.TimeService;
import rueppellii.backend2.tribes.kingdom.persistence.model.Kingdom;
import rueppellii.backend2.tribes.kingdom.persistence.repository.KingdomRepository;
import rueppellii.backend2.tribes.kingdom.service.KingdomService;
import rueppellii.backend2.tribes.resource.exception.NoResourceException;
import rueppellii.backend2.tribes.resource.presistence.model.Resource;
import rueppellii.backend2.tribes.resource.presistence.repository.ResourceRepository;
import rueppellii.backend2.tribes.resource.utility.ResourceType;

import java.util.List;
import java.util.Optional;


@Service
public class ResourceServiceImp {
    private ResourceRepository resourceRepository;
    private KingdomService kingdomService;
    private KingdomRepository kingdomRepository;
    private TimeService timeService;


    @Autowired
    public ResourceServiceImp(ResourceRepository resourceRepository, KingdomService kingdomService, KingdomRepository kingdomRepository, TimeService timeService) {
        this.resourceRepository = resourceRepository;
        this.kingdomService = kingdomService;
        this.kingdomRepository = kingdomRepository;
        this.timeService = timeService;
    }

    public Resource provideResource(ResourceType type) {
        return type.produceResource();
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

    public void goldAmountUpdate(Long kingdomId, Resource resource, Kingdom kingdom) throws NoResourceException {
        Resource gold = returnResource(ResourceType.GOLD, kingdomId);
        Long basicGoldAmount = resource.getAmount();
        Long updatedGoldAmount = basicGoldAmount + (timeService.refreshGold(kingdom) * gold.getResourcePerMinute());
        gold.setAmount(updatedGoldAmount);
        saveResource(gold);
    }

    public void updateFoodPerMinuteBasedOnTroop(Kingdom kingdom, Long kingdomId) throws NoResourceException {
        Resource food = returnResource(ResourceType.FOOD, kingdomId);
        Long foodPerMinute = food.getResourcePerMinute();
        int numberOfTroops = kingdom.getKingdomsTroops().size();
        Long basicFoodAmount = food.getAmount();
        Long updatedFoodAmount = basicFoodAmount + (timeService.refreshFood(kingdom) * foodPerMinute);
        food.setAmount(updatedFoodAmount);
        if (numberOfTroops == numberOfTroops + 1) {
            foodPerMinute =- 1L;
        }
    }
}
