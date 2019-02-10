package rueppellii.backend2.tribes.resource.presistence.model;


import rueppellii.backend2.tribes.resource.utility.ResourceType;

import javax.persistence.Entity;
import java.sql.Timestamp;

@Entity
public class Gold extends Resource {

    public Gold() {
        setType(ResourceType.GOLD);
        setAmount(100);
        setResourcePerMinute(5);
        setUpdatedAt(System.currentTimeMillis());
    }
}
