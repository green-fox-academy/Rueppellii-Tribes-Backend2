package rueppellii.backend2.tribes.resource.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import rueppellii.backend2.tribes.gameUtility.timeService.TimeService;
import rueppellii.backend2.tribes.gameUtility.timeService.TimeServiceImpl;
import rueppellii.backend2.tribes.kingdom.persistence.model.Kingdom;
import rueppellii.backend2.tribes.kingdom.persistence.repository.KingdomRepository;
import rueppellii.backend2.tribes.kingdom.service.KingdomService;
import rueppellii.backend2.tribes.resource.exception.NoResourceException;
import rueppellii.backend2.tribes.resource.presistence.model.Resource;
import rueppellii.backend2.tribes.resource.presistence.repository.ResourceRepository;
import rueppellii.backend2.tribes.resource.utility.ResourceType;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static rueppellii.backend2.tribes.resource.utility.ResourceFactory.makeResource;


@Service
public class ResourceServiceImp implements ResourceService {
    private ResourceRepository resourceRepository;
    private TimeServiceImpl timeServiceImpl;

    @Autowired
    public ResourceServiceImp(ResourceRepository resourceRepository, TimeServiceImpl timeServiceImpl) {
        this.resourceRepository = resourceRepository;
        this.timeServiceImpl = timeServiceImpl;
    }

    public Resource provideResource(ResourceType type) {
        return type.produceResource();
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

    @Override
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

    public static List<Resource> starterKit() {
        List<Resource> starterResources = new ArrayList<>();
        for (ResourceType t : ResourceType.values()) {
            starterResources.add(makeResource(t));
        }
        return starterResources;
    }

    public Timestamp currentTime() {
        return new Timestamp(System.currentTimeMillis());
    }

    public Resource findResourceById(Long id) {
        Optional<Resource> findById = resourceRepository.findResourceById(id);
        if (findById.isPresent()) {
            return findById.get();
        }
        return null;
    }

    public Timestamp timestampOfResource (Resource resource) {
        return currentTime();
    }

    public long timeDifferenceInMinutes(Long id) {
        return timeServiceImpl.timeDifference(currentTime(), timestampOfResource(findResourceById(id)));
    }

    public void goldAmountUpdate(Long kingdomId, Resource resource, Long id) throws NoResourceException {
        Resource gold = returnResource(ResourceType.GOLD, kingdomId);
        Long basicGoldAmount = resource.getAmount();
        Long updatedGoldAmount = basicGoldAmount + (timeDifferenceInMinutes(id) * gold.getResourcePerMinute());
        gold.setAmount(updatedGoldAmount);
        gold.setUpdatedAt(currentTime().getTime());
        saveResource(gold);
    }

    public void updateFoodPerMinuteBasedOnTroop(Kingdom kingdom, Long kingdomId, Long id) throws NoResourceException {
        Resource food = returnResource(ResourceType.FOOD, kingdomId);
        Long foodPerMinute = food.getResourcePerMinute();
        int numberOfTroops = kingdom.getKingdomsTroops().size();
        Long basicFoodAmount = food.getAmount();
        Long updatedFoodAmount = basicFoodAmount + (timeDifferenceInMinutes(id) * foodPerMinute);
        food.setAmount(updatedFoodAmount);
        if (numberOfTroops == numberOfTroops + 1) {
            foodPerMinute = - 1L;
        }
    }


}

