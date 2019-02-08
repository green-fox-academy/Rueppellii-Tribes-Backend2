package rueppellii.backend2.tribes.timeService;

import rueppellii.backend2.tribes.user.persistence.model.ApplicationUser;

public interface TimeService {

    public Long calculateTimeOfBuildingCreation(ApplicationUser applicationUser);
    public Long calculateTimeOfBuildingUpgrade(ApplicationUser applicationUser);
    public Long calculateTimeOfTroopCreation(ApplicationUser applicationUser);
    public Long calculateTimeOfTroopUpgrade(ApplicationUser applicationUser);

}
