package rueppellii.backend2.tribes.user.util;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "roles")
public enum Role {
    ADMIN, USER;

    public String authority() {
        return "ROLE_" + this.name();
    }
}
