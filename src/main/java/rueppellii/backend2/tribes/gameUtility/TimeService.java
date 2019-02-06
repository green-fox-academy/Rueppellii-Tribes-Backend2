package rueppellii.backend2.tribes.gameUtility;

import rueppellii.backend2.tribes.user.persistence.model.ApplicationUser;

public interface TimeService {

    public TimeServiceDTO calculateDuration(ApplicationUser applicationUser, Integer actionCode, String gameObjectToProgress);
}
