package rueppellii.backend2.tribes.gameUtility.timeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rueppellii.backend2.tribes.building.service.BuildingService;
import rueppellii.backend2.tribes.kingdom.persistence.model.Kingdom;
import rueppellii.backend2.tribes.resource.presistence.model.Food;
import rueppellii.backend2.tribes.resource.presistence.model.Gold;
import rueppellii.backend2.tribes.resource.service.ResourceServiceImp;
import rueppellii.backend2.tribes.user.persistence.model.ApplicationUser;

import static rueppellii.backend2.tribes.gameUtility.timeService.TimeConstants.*;

@Service
public class TimeServiceImpl implements TimeService {

    private BuildingService buildingService;
    private ResourceServiceImp resourceServiceImp;


    @Autowired
    public TimeServiceImpl(BuildingService buildingService, ResourceServiceImp resourceServiceImp) {
        this.buildingService = buildingService;
        this.resourceServiceImp = resourceServiceImp;
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

    @Override
    public Long refreshGold(Kingdom kingdom) {
        Gold gold = new Gold();
        Long currentTime = System.currentTimeMillis();
        Long elapsedTime = currentTime - gold.getUpdatedAt();
        Long elapsedMinutes = elapsedTime / 60000L;
        return elapsedMinutes;
    }

    @Override
    public Long refreshFood(Kingdom kingdom) {
        Food food = new Food();
        Long currentTime = System.currentTimeMillis();
        Long elapsedTime = currentTime - food.getUpdatedAt();
        Long elapsedMinutes = elapsedTime / 60000L;
        return elapsedMinutes;
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
