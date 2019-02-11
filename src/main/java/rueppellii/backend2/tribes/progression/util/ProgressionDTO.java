package rueppellii.backend2.tribes.progression.util;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class ProgressionDTO {

    @NotBlank
    private String type;
}
