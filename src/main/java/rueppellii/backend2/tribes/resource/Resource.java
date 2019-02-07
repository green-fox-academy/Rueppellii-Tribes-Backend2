package rueppellii.backend2.tribes.resource;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import rueppellii.backend2.tribes.kingdom.Kingdom;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.sql.Timestamp;

@Getter
@Setter
@Entity
@Table(name = "resource")
public abstract class Resource {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long resource_id;

    @Enumerated(EnumType.STRING)
    private ResourceType type;
    private Integer amount;
    private Timestamp updated_at;

    @JsonBackReference
    @ManyToOne
    @JoinTable(name = "kingdom_resource", joinColumns = {
            @JoinColumn(name = "resource_id", referencedColumnName = "resource_id")}, inverseJoinColumns = {
            @JoinColumn(name = "kingdom_id", referencedColumnName = "id")})
    private Kingdom resourcesKingdom;

}
