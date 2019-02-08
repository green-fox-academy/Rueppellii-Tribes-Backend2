package rueppellii.backend2.tribes.gameUtility.timeService;

import rueppellii.backend2.tribes.kingdom.persistence.model.Kingdom;
import rueppellii.backend2.tribes.progression.exception.BuildingNotFoundException;
import rueppellii.backend2.tribes.troop.exception.TroopNotFoundException;

public interface TimeService {

    Long calculateTimeOfBuildingCreation(Kingdom kingdom);
    Long calculateTimeOfBuildingUpgrade(Kingdom kingdom);
    Long calculateTimeOfTroopCreation(Kingdom kingdom);
    Long calculateTimeOfTroopUpgrade(Kingdom kingdom);
    void refreshProgression(Kingdom kingdom) throws TroopNotFoundException, BuildingNotFoundException;

}
