package rueppellii.backend2.tribes.common;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "upgradeables")
public abstract class Upgradeable<T> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public abstract void upgrade(T t);
    public abstract void create();
}
