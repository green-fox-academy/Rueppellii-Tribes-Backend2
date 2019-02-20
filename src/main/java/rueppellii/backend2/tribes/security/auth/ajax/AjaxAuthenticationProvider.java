package rueppellii.backend2.tribes.security.auth.ajax;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import rueppellii.backend2.tribes.security.model.UserContext;
import rueppellii.backend2.tribes.user.persistence.model.ApplicationUser;
import rueppellii.backend2.tribes.user.service.ApplicationUserService;

@Component
public class AjaxAuthenticationProvider implements AuthenticationProvider {

    private BCryptPasswordEncoder encoder;
    private ApplicationUserService applicationUserService;

    @Autowired
    public AjaxAuthenticationProvider(BCryptPasswordEncoder encoder, ApplicationUserService applicationUserService) {
        this.encoder = encoder;
        this.applicationUserService = applicationUserService;
    }

    @Override
        public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        Assert.notNull(authentication, "No authentication data provided");

        String username = (String) authentication.getPrincipal();
        String password = (String) authentication.getCredentials();

        ApplicationUser applicationUser = applicationUserService.findByUserName(username);

        if (!encoder.matches(password, applicationUser.getPassword())) {
            throw new BadCredentialsException("Wrong Password");
        }

        UserContext userContext = applicationUserService.createUserContext(username);

        return new UsernamePasswordAuthenticationToken(userContext, null, userContext.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
    }
}

