package rueppellii.backend2.tribes.user.util;

public enum Role {
    ADMIN, USER;

    public String authority() {
        return "ROLE_" + this.name();
    }
}
