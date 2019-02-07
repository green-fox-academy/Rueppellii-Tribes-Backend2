package rueppellii.backend2.tribes.resource;


import javax.persistence.Entity;
import java.sql.Timestamp;

@Entity
public class Gold extends Resource {

    public Gold() {
        setType(ResourceType.RESOURCE_GOLD);
        setAmount(100);
        setUpdated_at(new Timestamp(System.currentTimeMillis()));
    }
}
