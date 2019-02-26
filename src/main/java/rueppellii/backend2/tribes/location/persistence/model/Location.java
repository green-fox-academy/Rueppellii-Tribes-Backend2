package rueppellii.backend2.tribes.location.persistence.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;
import rueppellii.backend2.tribes.kingdom.persistence.model.Kingdom;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "locations")
public class Location {

    @Id
    private String countryCode;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "kingdom_id")
    private Kingdom locationsKingdom;
}
