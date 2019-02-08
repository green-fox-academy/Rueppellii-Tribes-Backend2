package rueppellii.backend2.tribes.gameUtility.timeService;

import rueppellii.backend2.tribes.kingdom.persistence.model.Kingdom;
import rueppellii.backend2.tribes.user.persistence.model.ApplicationUser;


public interface TimeService {

    Long calculateTimeOfBuildingCreation(ApplicationUser applicationUser);
    Long calculateTimeOfBuildingUpgrade(ApplicationUser applicationUser);
    Long calculateTimeOfTroopCreation(ApplicationUser applicationUser);
    Long calculateTimeOfTroopUpgrade(ApplicationUser applicationUser);
    Long refreshGold(Kingdom kingdom);
    Long refreshFood(Kingdom kingdom);

}
