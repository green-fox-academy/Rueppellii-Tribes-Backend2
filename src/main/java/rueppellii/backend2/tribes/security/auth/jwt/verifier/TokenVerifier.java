package rueppellii.backend2.tribes.security.auth.jwt.verifier;

public interface TokenVerifier {
    public boolean verify(String jti);
}