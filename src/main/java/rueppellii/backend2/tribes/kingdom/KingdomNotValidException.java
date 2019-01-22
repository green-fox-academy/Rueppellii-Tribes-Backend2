package rueppellii.backend2.tribes.kingdom;

import javax.naming.AuthenticationException;

public class KingdomNotValidException extends AuthenticationException {

    public KingdomNotValidException(String message) {
        super(message);
    }
}
