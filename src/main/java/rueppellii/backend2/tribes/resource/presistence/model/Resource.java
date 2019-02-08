package rueppellii.backend2.tribes.resource.presistence.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;
import rueppellii.backend2.tribes.kingdom.persistence.model.Kingdom;
import rueppellii.backend2.tribes.resource.utility.ResourceType;

import javax.persistence.*;
import java.sql.Timestamp;

@Getter
@Setter
@Entity
@Table(name = "resources")
public abstract class Resource {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private ResourceType type;
    private Integer amount;
    private Long updatedAt;
    private Integer resourcePerMinute;

    @JsonBackReference
    @ManyToOne
    @JoinTable(name = "kingdom_resources", joinColumns = {
            @JoinColumn(name = "resource_id", referencedColumnName = "id")}, inverseJoinColumns = {
            @JoinColumn(name = "kingdom_id", referencedColumnName = "id")})
    private Kingdom resourcesKingdom;

}
