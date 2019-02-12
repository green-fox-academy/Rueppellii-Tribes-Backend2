package rueppellii.backend2.tribes.resource.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import rueppellii.backend2.tribes.gameUtility.timeService.TimeServiceImpl;
import rueppellii.backend2.tribes.kingdom.persistence.model.Kingdom;
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

    public Timestamp timestampOfResource(Optional<Resource> resource) {
        return currentTime();
    }

    public long timeDifferenceInMinutes(Kingdom kingdom) {
        return timeServiceImpl.timeDifference(currentTime(), timestampOfResource(resourceRepository.findByTypeAndResourcesKingdom_Id(ResourceType.GOLD, kingdom.getId())));
    }

    public void refreshResources(Kingdom kingdom) throws NoResourceException {
        goldAmountUpdate(kingdom);
        foodAmountUpdate(kingdom);
    }

    public void goldAmountUpdate(Kingdom kingdom) throws NoResourceException {
        Resource resource = makeResource(ResourceType.GOLD);
        Optional<Resource> gold = resourceRepository.findByTypeAndResourcesKingdom_Id(ResourceType.GOLD, kingdom.getId());
        Integer basicGoldAmount = gold.get().getAmount();
        Integer goldPerMinute = gold.get().getResourcePerMinute();
        Integer updatedGoldAmount = basicGoldAmount + ((int) timeDifferenceInMinutes(kingdom) * goldPerMinute);
        gold.get().setAmount(updatedGoldAmount);
        gold.get().setUpdatedAt(currentTime().getTime());
        resource.setResourcesKingdom(kingdom);
        resourceRepository.save(resource);
    }

    public void foodAmountUpdate(Kingdom kingdom) throws NoResourceException {
        Resource resource = makeResource(ResourceType.FOOD);
        Optional<Resource> food = resourceRepository.findByTypeAndResourcesKingdom_Id(ResourceType.FOOD, kingdom.getId());
        int numberOfTroops = kingdom.getKingdomsTroops().size();
        Integer basicFoodAmount = food.get().getAmount();
        Integer foodPerMinute = (food.get().getResourcePerMinute()) - numberOfTroops;
        Integer updatedFoodAmount = basicFoodAmount + ((int) timeDifferenceInMinutes(kingdom) * foodPerMinute);
        food.get().setAmount(updatedFoodAmount);
        resource.setResourcesKingdom(kingdom);
        resourceRepository.save(resource);
    }


}

