package rueppellii.backend2.tribes.resource;

import lombok.Getter;
import lombok.Setter;
import rueppellii.backend2.tribes.kingdom.Kingdom;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.sql.Timestamp;


@Getter
@Setter
@Entity
@Table(name = "resource")
public class Resource {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long resource_id;

    @Enumerated(EnumType.STRING)
    private ResourceType resource_type;

    @NotBlank
    private Integer amount;

    private Timestamp updated_at;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinTable(name = "kingdomresource", joinColumns = {
            @JoinColumn(name = "resource_id", referencedColumnName = "resource_id")}, inverseJoinColumns = {
            @JoinColumn(name = "kingdom_id", referencedColumnName = "id")})
    private Kingdom kingdom;
}
