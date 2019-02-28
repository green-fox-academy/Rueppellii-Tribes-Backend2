package rueppellii.backend2.tribes.progression.util;

import lombok.Getter;
import lombok.Setter;
import rueppellii.backend2.tribes.progression.persistence.ProgressionModel;

import java.util.List;

@Getter
@Setter
public class ProgressionListDTO {
    List<ProgressionModel> progressionModels;
}
