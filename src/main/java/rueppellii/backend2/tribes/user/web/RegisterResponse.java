package rueppellii.backend2.tribes.user.web;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RegisterResponse {

    private Long id;
    private String username;
    private Long kingdomId;

    public RegisterResponse(Long id, String username, Long kingdomId) {
        this.id = id;
        this.username = username;
        this.kingdomId = kingdomId;
    }
}
