package rueppellii.backend2.tribes.user.util;

import lombok.Getter;

public enum Role {
    ADMIN, USER;

    public String authority() {
        return "ROLE_" + this.name();
    }
}
