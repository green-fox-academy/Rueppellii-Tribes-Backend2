package rueppellii.backend2.tribes.gameUtility.timeService;

import rueppellii.backend2.tribes.kingdom.persistence.model.Kingdom;
import rueppellii.backend2.tribes.progression.persistence.ProgressionModel;

public interface TimeService {

    Long calculateTimeOfBuildingCreation(Kingdom kingdom);
    Long calculateTimeOfBuildingUpgrade(Kingdom kingdom);
    Long calculateTimeOfTroopCreation(Kingdom kingdom);
    Long calculateTimeOfTroopUpgrade(Kingdom kingdom);
    Boolean timeIsUp(ProgressionModel progressionModel);

}
