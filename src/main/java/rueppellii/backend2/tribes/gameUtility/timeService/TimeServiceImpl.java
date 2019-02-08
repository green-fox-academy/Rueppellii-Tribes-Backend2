package rueppellii.backend2.tribes.gameUtility.timeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rueppellii.backend2.tribes.building.service.BuildingService;
import rueppellii.backend2.tribes.kingdom.persistence.model.Kingdom;
import rueppellii.backend2.tribes.progression.exception.BuildingNotFoundException;
import rueppellii.backend2.tribes.progression.persistence.ProgressionModel;
import rueppellii.backend2.tribes.progression.service.ProgressionService;
import rueppellii.backend2.tribes.troop.exception.TroopNotFoundException;

import java.util.List;

import static rueppellii.backend2.tribes.gameUtility.timeService.TimeConstants.*;

@Service
public class TimeServiceImpl implements TimeService {

    private BuildingService buildingService;
    private ProgressionService progressionService;

    @Autowired
    public TimeServiceImpl(BuildingService buildingService, ProgressionService progressionService) {
        this.buildingService = buildingService;
        this.progressionService = progressionService;
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
    public void refreshProgression(Kingdom kingdom) throws TroopNotFoundException, BuildingNotFoundException {
        List<ProgressionModel> progressions = progressionService.findAllByKingdom(kingdom);
        for (ProgressionModel p : progressions) {
            if(p.getTimeToCreate() <= System.currentTimeMillis()){
                progressionService.progress(p, kingdom);
            }
        }
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
