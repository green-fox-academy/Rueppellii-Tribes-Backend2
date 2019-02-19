package rueppellii.backend2.tribes.resource.presistence.model;

import rueppellii.backend2.tribes.resource.utility.ResourceType;

import javax.persistence.Entity;

@Entity
public class Gold extends Resource {

    public Gold() {
        setType(ResourceType.GOLD);
        setResourcePerMinute(5);
        setUpdatedAt(System.currentTimeMillis());
        setAmount(10000);
    }
}
