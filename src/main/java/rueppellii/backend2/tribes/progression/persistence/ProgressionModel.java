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
    private Long id;

    private String type;
    private Long timeToProgress;
    private Long gameObjectId;

    @JsonBackReference
    @ManyToOne
    @JoinTable(name = "kingdom_progresses", joinColumns = {@JoinColumn(name = "progress_id", referencedColumnName = "id")}, inverseJoinColumns = {@JoinColumn(name = "kingdom_id", referencedColumnName = "id")})
    private Kingdom progressKingdom;
}
