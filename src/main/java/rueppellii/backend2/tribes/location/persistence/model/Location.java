package rueppellii.backend2.tribes.location.persistence.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;
import rueppellii.backend2.tribes.kingdom.persistence.model.Kingdom;

import javax.persistence.*;

@Setter
@Getter
@Entity
@Table(name = "location")
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String country;

    @JsonBackReference
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "kingdom_id")
    private Kingdom locationsKingdom;

}
