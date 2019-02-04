package rueppellii.backend2.tribes.security.model.token;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.jsonwebtoken.Claims;

<<<<<<< HEAD
/**
 * Raw representation of JWT Token.
 *
 * @author vladimir.stankovic
 *
 *         May 31, 2016
 */
public final class AccessJwtToken extends TokenDTO implements JwtToken {
=======
public final class AccessJwtToken implements JwtToken {
>>>>>>> 4fba8a431eccdaf170bf5c224994b9ad42154acf
    private final String rawToken;
    @JsonIgnore private Claims claims;

    protected AccessJwtToken(final String token, Claims claims) {
        this.rawToken = token;
        this.claims = claims;
    }

    public String getToken() {
        return this.rawToken;
    }

    public Claims getClaims() {
        return claims;
    }
}