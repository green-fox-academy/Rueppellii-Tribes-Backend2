package rueppellii.backend2.tribes.message.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JwtResponse {

    private String status;
    private String token;

    public JwtResponse(String accessToken) {
        this.status = "ok";
        this.token = accessToken;
    }
}