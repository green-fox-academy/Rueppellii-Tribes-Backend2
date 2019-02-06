package rueppellii.backend2.tribes.gameUtility;

import org.springframework.stereotype.Service;
import rueppellii.backend2.tribes.building.persistence.model.Building;
import rueppellii.backend2.tribes.troop.models.Troop;
import rueppellii.backend2.tribes.user.persistence.model.ApplicationUser;

import java.util.List;
import java.util.stream.Collectors;

import static rueppellii.backend2.tribes.gameUtility.TimeConstants.*;

@Service
public class TimeServiceImpl implements TimeService {

    private TimeServiceDTO timeServiceDTO;

    @Override
    public TimeServiceDTO calculateDuration(ApplicationUser applicationUser, Integer actionCode, String gameObjectToProgress) {
        timeServiceDTO = new TimeServiceDTO();
        if (actionCode > 0) {
            //TODO this is gonna be upgrade
            if (gameObjectToProgress.equals("TOWNHALL")) {
                timeServiceDTO.setDuration(TOWNHALL_UPGRADE_TIME / getLevelOfTownHall(applicationUser));
                timeServiceDTO.setGameObjectToProgressId(getBuildingIdToProgress(applicationUser, actionCode, gameObjectToProgress));
            } else if (gameObjectToProgress.equals("BARACKS")) {
                timeServiceDTO.setDuration(BARACKS_UPGRADE_TIME / getLevelOfTownHall(applicationUser));
                timeServiceDTO.setGameObjectToProgressId(getBuildingIdToProgress(applicationUser, actionCode, gameObjectToProgress));
            } else if (gameObjectToProgress.equals("FARM")) {
                timeServiceDTO.setDuration(FARM_UPGRADE_TIME / getLevelOfTownHall(applicationUser));
                timeServiceDTO.setGameObjectToProgressId(getBuildingIdToProgress(applicationUser, actionCode, gameObjectToProgress));
            } else if (gameObjectToProgress.equals("MINE")) {
                timeServiceDTO.setDuration(FARM_UPGRADE_TIME / getLevelOfTownHall(applicationUser));
                timeServiceDTO.setGameObjectToProgressId(getBuildingIdToProgress(applicationUser, actionCode, gameObjectToProgress));
            } else if (gameObjectToProgress.equals("TROOP")) {
                timeServiceDTO.setDuration(TROOP_CREATION_TIME / actionCode);
                timeServiceDTO.setGameObjectToProgressId(getTroopIdToProgress(applicationUser, actionCode, gameObjectToProgress));
            }
        } else {
            //TODO this is gonna be create
            if (gameObjectToProgress.equals("TOWNHALL")) {
                timeServiceDTO.setDuration(TOWNHALL_UPGRADE_TIME / getLevelOfTownHall(applicationUser));
            } else if (gameObjectToProgress.equals("BARACKS")) {
                timeServiceDTO.setDuration(BARACKS_UPGRADE_TIME / getLevelOfTownHall(applicationUser));
            } else if (gameObjectToProgress.equals("FARM")) {
                timeServiceDTO.setDuration(FARM_UPGRADE_TIME / getLevelOfTownHall(applicationUser));
            } else if (gameObjectToProgress.equals("MINE")) {
                timeServiceDTO.setDuration(FARM_UPGRADE_TIME / getLevelOfTownHall(applicationUser));
            } else if (gameObjectToProgress.equals("TROOP")) {
                timeServiceDTO.setDuration(TROOP_CREATION_TIME / actionCode);
            }
        }
        return timeServiceDTO;

    }
    public Integer getLevelOfTownHall(ApplicationUser applicationUser) {
        List<Building> townhall = applicationUser
                .getKingdom()
                .getKingdomsBuildings()
                .stream()
                .filter(building -> building.getType().getName().matches("TOWNHALL"))
                .collect(Collectors.toList());
        return townhall.get(1).getLevel();
    }

    public Long getBuildingIdToProgress(ApplicationUser applicationUser, Integer actionCode, String gameObjectToProgress) {
        List<Building> buildingsToProgress = applicationUser.getKingdom().getKingdomsBuildings()
                .stream()
                .filter(building -> building.getType().getName().matches(gameObjectToProgress.toUpperCase()))
                .filter(building -> building.getLevel() == actionCode)
                .collect(Collectors.toList());
        return buildingsToProgress.get(1).getBuilding_id();
    }

    public Long getTroopIdToProgress(ApplicationUser applicationUser, Integer actionCode, String gameObjectToProgress) {
        List<Troop> troopsToProgress = applicationUser.getKingdom().getTroops()
                .stream()
                .filter(troop -> troop.getLevel() == actionCode)
                .collect(Collectors.toList());
        return troopsToProgress.get(1).getTroop_id();
    }


}
