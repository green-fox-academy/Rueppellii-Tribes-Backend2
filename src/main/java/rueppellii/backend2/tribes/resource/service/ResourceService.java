package rueppellii.backend2.tribes.resource.service;

import org.springframework.http.ResponseEntity;
import rueppellii.backend2.tribes.kingdom.persistence.model.Kingdom;
import rueppellii.backend2.tribes.resource.exception.NoResourceException;
import rueppellii.backend2.tribes.resource.presistence.model.Resource;
import rueppellii.backend2.tribes.resource.utility.ResourceType;

import java.sql.Timestamp;

public interface ResourceService {
    ResponseEntity saveResource(Resource resource);
    boolean validateType(Resource resource);
    Resource returnResource(ResourceType type, Long id) throws NoResourceException;
    void minusGoldAmount(Integer gold, Long kingdomId) throws NoResourceException;
    Timestamp currentTime();
    Timestamp timestampOfResource (Resource resource);
    long timeDifferenceInMinutes(Long id);
    void goldAmountUpdate(Long kingdomId, Resource resource, Long id) throws NoResourceException;
    void updateFoodPerMinuteBasedOnTroop(Kingdom kingdom, Long kingdomId, Long id) throws NoResourceException;
}
