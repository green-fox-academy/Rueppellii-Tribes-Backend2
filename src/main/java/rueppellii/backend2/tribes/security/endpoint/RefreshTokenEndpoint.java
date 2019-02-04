package rueppellii.backend2.tribes.security.endpoint;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.ServletException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import rueppellii.backend2.tribes.security.exceptions.InvalidJwtToken;
import rueppellii.backend2.tribes.security.auth.jwt.extractor.TokenExtractor;
import rueppellii.backend2.tribes.security.auth.jwt.verifier.TokenVerifier;
import rueppellii.backend2.tribes.security.model.UserContext;
import rueppellii.backend2.tribes.security.model.token.JwtToken;
import rueppellii.backend2.tribes.security.model.token.JwtTokenFactory;
import rueppellii.backend2.tribes.security.model.token.RawAccessJwtToken;
import rueppellii.backend2.tribes.security.model.token.RefreshToken;
import rueppellii.backend2.tribes.user.persistence.model.ApplicationUser;
import rueppellii.backend2.tribes.user.service.ApplicationUserService;

import static rueppellii.backend2.tribes.security.SecurityConstants.AUTHENTICATION_HEADER_NAME;
import static rueppellii.backend2.tribes.security.SecurityConstants.TOKEN_SIGNING_KEY;

@RestController
public class RefreshTokenEndpoint {

    private JwtTokenFactory tokenFactory;
    private ApplicationUserService applicationUserService;
    private TokenVerifier tokenVerifier;
    @Qualifier("jwtHeaderTokenExtractor")
    private TokenExtractor tokenExtractor;

    @Autowired
    public RefreshTokenEndpoint(JwtTokenFactory tokenFactory, ApplicationUserService applicationUserService, TokenVerifier tokenVerifier, TokenExtractor tokenExtractor) {
        this.tokenFactory = tokenFactory;
        this.applicationUserService = applicationUserService;
        this.tokenVerifier = tokenVerifier;
        this.tokenExtractor = tokenExtractor;
    }

    @RequestMapping(value = "/api/auth/token", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody
    JwtToken refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String tokenPayload = tokenExtractor.extract(request.getHeader(AUTHENTICATION_HEADER_NAME));

        RawAccessJwtToken rawToken = new RawAccessJwtToken(tokenPayload);
        RefreshToken refreshToken = null;
        try {
            refreshToken = RefreshToken.create(rawToken, TOKEN_SIGNING_KEY).orElseThrow(Exception::new);
        } catch (Exception e) {
            e.printStackTrace();
        }

        String jti = refreshToken.getJti();
        if (!tokenVerifier.verify(jti)) {
            throw new InvalidJwtToken();
        }

        String subject = refreshToken.getSubject();
        ApplicationUser applicationUser = applicationUserService.findByUserName(subject).orElseThrow(() -> new UsernameNotFoundException("User not found: " + subject));

        if (applicationUser.getRoles() == null) throw new InsufficientAuthenticationException("User has no roles assigned");
        List<GrantedAuthority> authorities = applicationUser.getRoles().stream()
                .map(authority -> new SimpleGrantedAuthority(authority.getRoleEnum().authority()))
                .collect(Collectors.toList());

        UserContext userContext = UserContext.create(applicationUser.getUsername(), authorities);

        return (JwtToken) tokenFactory.createAccessJwtToken(userContext);
    }
}

