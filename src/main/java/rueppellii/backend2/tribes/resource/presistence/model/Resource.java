package rueppellii.backend2.tribes.resource.presistence.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;
import rueppellii.backend2.tribes.kingdom.persistence.model.Kingdom;
import rueppellii.backend2.tribes.resource.utility.ResourceType;

import javax.persistence.*;
import javax.validation.constraints.Min;

@Getter
@Setter
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
//@DiscriminatorColumn(name = "type")
@Table(name = "resources")
public abstract class Resource {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    //  @Column(insertable = false, updatable = false)
    private ResourceType type;
    @Min(0L)
    private Long amount;
    private Long updatedAt;
    private Long resourcePerMinute;


    @JsonBackReference
    @ManyToOne
    @JoinTable(name = "kingdom_resources", joinColumns = {
            @JoinColumn(name = "resource_id", referencedColumnName = "id")}, inverseJoinColumns = {
            @JoinColumn(name = "kingdom_id", referencedColumnName = "id")})
    private Kingdom resourcesKingdom;

}
