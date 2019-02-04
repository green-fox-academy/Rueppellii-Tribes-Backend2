package rueppellii.backend2.tribes.kingdom.exception;

import javax.naming.AuthenticationException;

public class KingdomNotValidException extends AuthenticationException {

    public KingdomNotValidException(String message) {
        super(message);
    }
}
