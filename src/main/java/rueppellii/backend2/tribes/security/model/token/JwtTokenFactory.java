package rueppellii.backend2.tribes.security.model.token;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Date;
import java.util.UUID;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import rueppellii.backend2.tribes.security.model.Scopes;
import rueppellii.backend2.tribes.security.model.UserContext;
import rueppellii.backend2.tribes.user.persistence.dao.ApplicationUserRepository;

import static rueppellii.backend2.tribes.security.SecurityConstants.*;

@Component
public class JwtTokenFactory {

    @Autowired
    ApplicationUserRepository applicationUserRepository;

    public AccessJwtToken createAccessJwtToken(UserContext userContext) {
        if (StringUtils.isBlank(userContext.getUsername()))
            throw new IllegalArgumentException("Cannot create JWT Token without username");

        if (userContext.getAuthorities() == null || userContext.getAuthorities().isEmpty())
            throw new IllegalArgumentException("User doesn't have any privileges");

        Claims claims = Jwts.claims().setSubject(userContext.getUsername());
        claims.put("scopes", userContext
                .getAuthorities()
                .stream()
                .map(s -> s.toString())
                .collect(Collectors.toList()));

        LocalDateTime currentTime = LocalDateTime.now();

        String token = Jwts.builder()
                .setClaims(claims)
                .setIssuer(TOKEN_ISSUER)
                .setIssuedAt(Date.from(currentTime.atZone(ZoneId.systemDefault()).toInstant()))
                .setExpiration(Date.from(currentTime
                        .plusMinutes(ACCESS_TOKEN_EXP_TIME)
                        .atZone(ZoneId.systemDefault()).toInstant()))
                .signWith(SignatureAlgorithm.HS512, TOKEN_SIGNING_KEY)
                .compact();

        return new AccessJwtToken(token, claims);
    }

    public JwtToken createRefreshToken(UserContext userContext) {
        if (StringUtils.isBlank(userContext.getUsername())) {
            throw new IllegalArgumentException("Cannot create JWT Token without username");
        }

        LocalDateTime currentTime = LocalDateTime.now();

        Claims claims = Jwts.claims().setSubject(userContext.getUsername());
        claims.put("scopes", Arrays.asList(Scopes.REFRESH_TOKEN.authority()));

        String token = Jwts.builder()
                .setClaims(claims)
                .setIssuer(TOKEN_ISSUER)
                .setId(UUID.randomUUID().toString())
                .setIssuedAt(Date.from(currentTime.atZone(ZoneId.systemDefault()).toInstant()))
                .setExpiration(Date.from(currentTime
                        .plusMinutes(REFRESH_TOKEN_EXP_TIME)
                        .atZone(ZoneId.systemDefault()).toInstant()))
                .signWith(SignatureAlgorithm.HS512, TOKEN_SIGNING_KEY)
                .compact();

        return new AccessJwtToken(token, claims);
    }

    public JwtToken createTestRefreshToken(UserContext userContext, Long tokenLifetimeInMilliseconds) {
        if (StringUtils.isBlank(userContext.getUsername())) {
            throw new IllegalArgumentException("Cannot create JWT Token without username");
        }

        LocalDateTime currentTime = LocalDateTime.now();

        Date refreshTokenExpirationTime = Date.from(currentTime.atZone(ZoneId.systemDefault())
                .plus(tokenLifetimeInMilliseconds, ChronoUnit.MILLIS).toInstant());

        return setupTokenDetails(userContext, currentTime, refreshTokenExpirationTime);
    }


    private JwtToken setupTokenDetails(UserContext userContext, LocalDateTime currentTime, Date refreshTokenExpirationTime) {

        Claims claims = Jwts.claims().setSubject(userContext.getUsername());
        claims.put("scopes", Arrays.asList(Scopes.REFRESH_TOKEN.authority()));

        int userIdForToken = applicationUserRepository.findById(userContext.getUsername());
        StringBuilder userIdForTokenString = new StringBuilder();
        userIdForTokenString.append(userIdForToken);
        String token = Jwts.builder()
                .setClaims(claims)
                .setId(userIdForTokenString.toString())
                .setIssuer(TOKEN_ISSUER)
                .setId(UUID.randomUUID().toString())
                .setIssuedAt(Date.from(currentTime.atZone(ZoneId.systemDefault()).toInstant()))
                .setExpiration(refreshTokenExpirationTime)
                .signWith(SignatureAlgorithm.HS512, TOKEN_SIGNING_KEY)
                .compact();

        return new AccessJwtToken(token, claims);
    }

}