package rueppellii.backend2.tribes.user.util;

import org.hibernate.annotations.Tuplizer;
import rueppellii.backend2.tribes.enumToTable.EnumTuplizer;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "roles")
@Tuplizer(impl = EnumTuplizer.class)
public enum Role {
    @Column(name = "role_enum")
    ADMIN, USER;

    public String authority() {
        return "ROLE_" + this.name();
    }
}
