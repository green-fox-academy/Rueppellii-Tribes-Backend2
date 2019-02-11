package rueppellii.backend2.tribes.resource.presistence.model;

import rueppellii.backend2.tribes.resource.utility.ResourceType;

import javax.persistence.Entity;
import java.sql.Timestamp;

@Entity
public class Food extends Resource {

    public Food() {
        setType(ResourceType.FOOD);
        setAmount(0);
        setResourcePerMinute(5);
        setUpdatedAt(System.currentTimeMillis());
    }
}
