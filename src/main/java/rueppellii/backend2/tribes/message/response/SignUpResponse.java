package rueppellii.backend2.tribes.message.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SignUpResponse {

    private Long id;
    private String username;
    private Long kingdomId;

    public SignUpResponse(Long id, String username, Long kingdomId) {
        this.id = id;
        this.username = username;
        this.kingdomId = kingdomId;
    }
}
