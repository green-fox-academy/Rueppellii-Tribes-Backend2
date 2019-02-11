package rueppellii.backend2.tribes.kingdom.exception;

import javax.naming.AuthenticationException;

public class KingdomNotFoundException extends AuthenticationException {

    public KingdomNotFoundException(String message) {
        super(message);
    }
}
