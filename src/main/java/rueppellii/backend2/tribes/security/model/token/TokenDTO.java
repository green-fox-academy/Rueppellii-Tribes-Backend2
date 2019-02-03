package rueppellii.backend2.tribes.security.model.token;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class TokenDTO {
    private JwtToken jwtToken;
    private Date expiration;

    public TokenDTO(JwtToken jwtToken, Date expiration) {
        this.jwtToken = jwtToken;
        this.expiration = expiration;
    }
}
