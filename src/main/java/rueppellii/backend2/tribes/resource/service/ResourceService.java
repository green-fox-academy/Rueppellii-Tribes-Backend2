package rueppellii.backend2.tribes.resource.service;

import org.springframework.http.ResponseEntity;
import rueppellii.backend2.tribes.kingdom.persistence.model.Kingdom;
import rueppellii.backend2.tribes.resource.exception.NoResourceException;
import rueppellii.backend2.tribes.resource.presistence.model.Resource;
import rueppellii.backend2.tribes.resource.utility.ResourceType;

import java.sql.Timestamp;
import java.util.Optional;

public interface ResourceService {
    ResponseEntity saveResource(Resource resource);
    boolean validateType(Resource resource);
    Resource returnResource(ResourceType type, Long id) throws NoResourceException;
    void minusGoldAmount(Integer gold, Long kingdomId) throws NoResourceException;
    Timestamp currentTime();

    Timestamp timestampOfResource(Optional<Resource> resource);

    long timeDifferenceInMinutes(Kingdom kingdom);

    void goldAmountUpdate(Kingdom kingdom) throws NoResourceException;

    void foodAmountUpdate(Kingdom kingdom) throws NoResourceException;
}
