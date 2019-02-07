package rueppellii.backend2.tribes.gameUtility;

import org.springframework.stereotype.Service;
import rueppellii.backend2.tribes.building.persistence.model.Building;
import rueppellii.backend2.tribes.troop.models.Troop;
import rueppellii.backend2.tribes.user.persistence.model.ApplicationUser;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static rueppellii.backend2.tribes.gameUtility.TimeConstants.*;

@Service
public class TimeServiceImpl implements TimeService {

    private TimeServiceDTO timeServiceDTO;

    @Override
    public TimeServiceDTO calculateDuration(ApplicationUser applicationUser, Integer actionCode, String gameObjectToProgress) {
        timeServiceDTO = new TimeServiceDTO();

        for (GameObjectsToCreate g : GameObjectsToCreate.values()) {
            if(GameObjectsToCreate.valueOf(gameObjectToProgress).equals(g)){
                timeServiceDTO.setDuration(g.calculateTime());

                timeServiceDTO.setGameObjectToProgressId();
            }

        }
        if (actionCode > 0) {

            //TODO this is gonna be upgrade
            switch (gameObjectToProgress) {
                case "TOWNHALL":
                    timeServiceDTO.setDuration(System.currentTimeMillis() + (TOWNHALL_UPGRADE_TIME / getLevelOfTownHall(applicationUser)));
                    timeServiceDTO.setGameObjectToProgressId(getBuildingIdToProgress(applicationUser, actionCode, gameObjectToProgress));
                    break;
                case "BARRACKS":
                    timeServiceDTO.setDuration(System.currentTimeMillis() + (BARRACKS_UPGRADE_TIME / getLevelOfTownHall(applicationUser)));
                    timeServiceDTO.setGameObjectToProgressId(getBuildingIdToProgress(applicationUser, actionCode, gameObjectToProgress));
                    break;
                case "FARM":
                    timeServiceDTO.setDuration(System.currentTimeMillis() + (FARM_UPGRADE_TIME / getLevelOfTownHall(applicationUser)));
                    timeServiceDTO.setGameObjectToProgressId(getBuildingIdToProgress(applicationUser, actionCode, gameObjectToProgress));
                    break;
                case "MINE":
                    timeServiceDTO.setDuration(System.currentTimeMillis() + (FARM_UPGRADE_TIME / getLevelOfTownHall(applicationUser)));
                    timeServiceDTO.setGameObjectToProgressId(getBuildingIdToProgress(applicationUser, actionCode, gameObjectToProgress));
                    break;
                case "TROOP":
                    timeServiceDTO.setDuration(System.currentTimeMillis() + (TROOP_CREATION_TIME / actionCode));
                    timeServiceDTO.setGameObjectToProgressId(getTroopIdToProgress(applicationUser, actionCode, gameObjectToProgress));
                    break;
            }
        } else {
            //TODO this is gonna be create
            if (gameObjectToProgress.equals("TOWNHALL")) {
                timeServiceDTO.setDuration(System.currentTimeMillis() + (TOWNHALL_UPGRADE_TIME / getLevelOfTownHall(applicationUser)));
            } else if (gameObjectToProgress.equals("BARRACKS")) {
                timeServiceDTO.setDuration(System.currentTimeMillis() + (BARRACKS_UPGRADE_TIME / getLevelOfTownHall(applicationUser)));
            } else if (gameObjectToProgress.equals("FARM")) {
                timeServiceDTO.setDuration(System.currentTimeMillis() + (FARM_UPGRADE_TIME / getLevelOfTownHall(applicationUser)));
            } else if (gameObjectToProgress.equals("MINE")) {
                timeServiceDTO.setDuration(System.currentTimeMillis() + (FARM_UPGRADE_TIME / getLevelOfTownHall(applicationUser)));
            } else if (gameObjectToProgress.equals("TROOP")) {
                timeServiceDTO.setDuration(System.currentTimeMillis() + (TROOP_CREATION_TIME / actionCode));
            }
        }
        return timeServiceDTO;

    }

    public Long getBuildingIdToProgress(ApplicationUser applicationUser, Integer actionCode, String gameObjectToProgress) {
        List<Building> buildingsToProgress = applicationUser.getKingdom().getKingdomsBuildings()
                .stream()
                .filter(building -> building.getType().getName().matches(gameObjectToProgress.toUpperCase()))
                .filter(building -> Objects.equals(building.getLevel(), actionCode))
                .collect(Collectors.toList());
        return buildingsToProgress.get(0).getBuilding_id();
    }

    public Long getTroopIdToProgress(ApplicationUser applicationUser, Integer actionCode, String gameObjectToProgress) {
        List<Troop> troopsToProgress = applicationUser.getKingdom().getTroops()
                .stream()
                .filter(troop -> Objects.equals(troop.getLevel(), actionCode))
                .collect(Collectors.toList());
        return troopsToProgress.get(0).getTroop_id();
    }

}
