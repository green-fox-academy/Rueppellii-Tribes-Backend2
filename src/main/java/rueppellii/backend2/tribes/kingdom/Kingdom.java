package rueppellii.backend2.tribes.kingdom;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import rueppellii.backend2.tribes.user.TribesUser;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Kingdom {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;

    @OneToOne
    private TribesUser tribesUser;

}
