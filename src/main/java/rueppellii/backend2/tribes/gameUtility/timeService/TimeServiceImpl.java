package rueppellii.backend2.tribes.gameUtility.timeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rueppellii.backend2.tribes.building.service.BuildingService;
import rueppellii.backend2.tribes.kingdom.persistence.model.Kingdom;
import rueppellii.backend2.tribes.progression.persistence.ProgressionModel;
import rueppellii.backend2.tribes.resource.presistence.model.Food;
import rueppellii.backend2.tribes.resource.presistence.model.Gold;
import rueppellii.backend2.tribes.resource.service.ResourceServiceImp;

import java.sql.Timestamp;

import static rueppellii.backend2.tribes.gameUtility.timeService.TimeConstants.*;

@Service
public class TimeServiceImpl implements TimeService {

    private BuildingService buildingService;
    private ResourceServiceImp resourceServiceImp;


    @Autowired
    public TimeServiceImpl(BuildingService buildingService,ResourceServiceImp resourceServiceImp) {
        this.buildingService = buildingService;
        this.resourceServiceImp = resourceServiceImp;
    }

    @Override
    public Long calculateTimeOfBuildingCreation(Kingdom kingdom) {
        return System.currentTimeMillis() + (BUILDING_CREATION_TIME / buildingService.getLevelOfTownHall(kingdom));
    }

    @Override
    public Long calculateTimeOfBuildingUpgrade(Kingdom kingdom) {
        return System.currentTimeMillis() + (BUILDING_UPGRADE_TIME / buildingService.getLevelOfTownHall(kingdom));
    }

    @Override
    public Long calculateTimeOfTroopCreation(Kingdom kingdom) {
        return System.currentTimeMillis() + (TROOP_CREATION_TIME / buildingService.getLevelOfTownHall(kingdom));
    }

    @Override
    public Long calculateTimeOfTroopUpgrade(Kingdom kingdom) {
        return System.currentTimeMillis() + (TROOP_UPGRADE_TIME / buildingService.getLevelOfTownHall(kingdom));
    }

    @Override
    public Boolean timeIsUp(ProgressionModel progressionModel) {
        return progressionModel.getTimeToProgress() <= System.currentTimeMillis();
    }

    @Override
    public Long timeDifference(Timestamp currentTime, Timestamp updatedAtTime) {
        Long elapsedTime = currentTime.getTime() - updatedAtTime.getTime();
        Long elapsedMinutes = elapsedTime / 60000L;
        return elapsedMinutes;
    }


    //TODO: Might need Later
//    public Long getBuildingIdToProgress(ApplicationUser applicationUser, Integer actionCode, String gameObjectToProgress) {
//        List<Building> buildingsToProgress = applicationUser.getTroopsKingdom().getKingdomsBuildings()
//                .stream()
//                .filter(building -> building.getType().getName().matches(gameObjectToProgress.toUpperCase()))
//                .filter(building -> Objects.equals(building.getLevel(), actionCode))
//                .collect(Collectors.toList());
//        return buildingsToProgress.get(0).getBuilding_id();
//    }
//
//    public Long getTroopIdToProgress(ApplicationUser applicationUser, Integer actionCode, String gameObjectToProgress) {
//        List<Troop> troopsToProgress = applicationUser.getTroopsKingdom().getTroops()
//                .stream()
//                .filter(troop -> Objects.equals(troop.getLevel(), actionCode))
//                .collect(Collectors.toList());
//        return troopsToProgress.get(0).getTroop_id();
//    }


}
