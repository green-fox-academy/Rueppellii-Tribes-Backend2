package rueppellii.backend2.tribes.timeService;

import org.springframework.stereotype.Service;
import rueppellii.backend2.tribes.building.service.BuildingService;
import rueppellii.backend2.tribes.user.persistence.model.ApplicationUser;


import static rueppellii.backend2.tribes.timeService.TimeConstants.*;

@Service
public class TimeServiceImpl implements TimeService {

    private BuildingService buildingService;

    public TimeServiceImpl(BuildingService buildingServic) {
        this.buildingService = buildingServic;
    }

    @Override
    public Long calculateTimeOfBuildingCreation(ApplicationUser applicationUser) {
        return System.currentTimeMillis() + (BUILDING_CREATION_TIME / buildingService.getLevelOfTownHall(applicationUser));
    }

    @Override
    public Long calculateTimeOfBuildingUpgrade(ApplicationUser applicationUser) {
        return System.currentTimeMillis() + (BUILDING_UPGRADE_TIME / buildingService.getLevelOfTownHall(applicationUser));
    }

    @Override
    public Long calculateTimeOfTroopCreation(ApplicationUser applicationUser) {
        return System.currentTimeMillis() + (TROOP_CREATION_TIME / buildingService.getLevelOfTownHall(applicationUser));
    }

    @Override
    public Long calculateTimeOfTroopUpgrade(ApplicationUser applicationUser) {
        return System.currentTimeMillis() + (TROOP_UPGRADE_TIME / buildingService.getLevelOfTownHall(applicationUser));
    }


    //TODO: Might need Later
//    public Long getBuildingIdToProgress(ApplicationUser applicationUser, Integer actionCode, String gameObjectToProgress) {
//        List<Building> buildingsToProgress = applicationUser.getKingdom().getKingdomsBuildings()
//                .stream()
//                .filter(building -> building.getType().getName().matches(gameObjectToProgress.toUpperCase()))
//                .filter(building -> Objects.equals(building.getLevel(), actionCode))
//                .collect(Collectors.toList());
//        return buildingsToProgress.get(0).getBuilding_id();
//    }
//
//    public Long getTroopIdToProgress(ApplicationUser applicationUser, Integer actionCode, String gameObjectToProgress) {
//        List<Troop> troopsToProgress = applicationUser.getKingdom().getTroops()
//                .stream()
//                .filter(troop -> Objects.equals(troop.getLevel(), actionCode))
//                .collect(Collectors.toList());
//        return troopsToProgress.get(0).getTroop_id();
//    }


}
