package rueppellii.backend2.tribes.progression.persistence;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;
import rueppellii.backend2.tribes.kingdom.persistence.model.Kingdom;

import javax.persistence.*;

@Setter
@Getter
@Entity
@Table(name = "progression")
public class ProgressionModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long progression_id;

    @JsonBackReference
    @ManyToOne
    @JoinTable(name = "kingdom_progress")
    private Kingdom progressKingdom;

    private String objectToProgress;
    private Long timeToCreate;
    private Long gameObjectId;
}
