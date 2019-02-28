package rueppellii.backend2.tribes.user.utility;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorResponse {

    private String status;
    private String message;

    public ErrorResponse() {
    }

    public ErrorResponse(String message) {
        this.status = "error";
        this.message = message;
    }
}