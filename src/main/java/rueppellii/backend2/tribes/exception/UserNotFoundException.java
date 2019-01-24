package rueppellii.backend2.tribes.exception;

public class UserNotFoundException extends Exception {

    public UserNotFoundException(String user) {
        super(user);
    }
}
