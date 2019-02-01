package rueppellii.backend2.tribes.security.auth.jwt.extractor;

public interface TokenExtractor {
    String extract(String payload);
}
