package rueppellii.backend2.tribes.resource;

import javax.persistence.Entity;
import java.sql.Timestamp;

@Entity
public class Food extends Resource {

    public Food() {
        setType(ResourceType.FOOD);
        setAmount(0);
        setUpdated_at(new Timestamp(System.currentTimeMillis()));
    }
}
