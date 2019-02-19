package rueppellii.backend2.tribes.progression.service;

import org.apache.commons.lang3.EnumUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rueppellii.backend2.tribes.building.persistence.model.Building;
import rueppellii.backend2.tribes.building.service.BuildingService;
import rueppellii.backend2.tribes.building.utility.BuildingType;
import rueppellii.backend2.tribes.gameUtility.timeService.TimeService;
import rueppellii.backend2.tribes.kingdom.persistence.model.Kingdom;
import rueppellii.backend2.tribes.building.exception.BuildingNotFoundException;
import rueppellii.backend2.tribes.kingdom.service.KingdomService;
import rueppellii.backend2.tribes.progression.exception.InvalidProgressionRequestException;
import rueppellii.backend2.tribes.progression.persistence.ProgressionModel;
import rueppellii.backend2.tribes.progression.persistence.ProgressionModelRepository;
import rueppellii.backend2.tribes.progression.util.ProgressionDTO;
import rueppellii.backend2.tribes.troop.exception.TroopNotFoundException;
import rueppellii.backend2.tribes.troop.persistence.model.Troop;
import rueppellii.backend2.tribes.troop.service.TroopService;

import java.util.List;

@Service
public class ProgressionService {

    private ProgressionModelRepository progressionModelRepository;
    private BuildingService buildingService;
    private TroopService troopService;
    private TimeService timeService;
    private KingdomService kingdomService;

    @Autowired
    public ProgressionService(ProgressionModelRepository progressionModelRepository,
                              BuildingService buildingService,
                              TroopService troopService, TimeService timeService, KingdomService kingdomService) {
        this.progressionModelRepository = progressionModelRepository;
        this.buildingService = buildingService;
        this.troopService = troopService;
        this.timeService = timeService;
        this.kingdomService = kingdomService;
    }

    public void updateProgression(Kingdom kingdom) throws TroopNotFoundException, BuildingNotFoundException {
        List<ProgressionModel> progressions = kingdom.getKingdomsProgresses();
        for (ProgressionModel p : progressions) {
            System.out.println(p.getType());
            if (timeService.timeIsUp(p)) {
                progress(p, kingdom);
            }
        }
    }

    private void progress(ProgressionModel progressionModel, Kingdom kingdom) throws TroopNotFoundException,
            BuildingNotFoundException {
        if (progressionModel.getGameObjectId() == null) {
            if (progressionModel.getType().equals("TROOP")) {
                troopService.createTroop(kingdom);
                progressionModelRepository.deleteById(progressionModel.getId());
                return;
            }
            buildingService.createBuilding(progressionModel, kingdom);
            progressionModelRepository.delete(progressionModel);
            return;
        }
        if (progressionModel.getType().equals("TROOP")) {
            troopService.upgradeTroop(progressionModel);
            progressionModelRepository.delete(progressionModel);
            return;
        }
        buildingService.upgradeBuilding(progressionModel, kingdom);
        progressionModelRepository.deleteById(progressionModel.getId());
    }

    public void validateProgressionRequest(ProgressionDTO progressionDTO, Kingdom kingdom) throws InvalidProgressionRequestException {
        if (!EnumUtils.isValidEnum(BuildingType.class, progressionDTO.getType())) {
            throw new InvalidProgressionRequestException("Wrong type!");
        }
         if (progressionDTO.getType().toUpperCase().equals("TOWNHALL")) {
            throw new InvalidProgressionRequestException("Only one town hall to a kingdom");
        }
        checkIfBuildingIsAlreadyInProgress(kingdom);
    }

    public void checkIfBuildingIsAlreadyInProgress(Kingdom kingdom) throws InvalidProgressionRequestException {
        for (ProgressionModel p : kingdom.getKingdomsProgresses()) {
            if (EnumUtils.isValidEnum(BuildingType.class, p.getType())) {
                throw new InvalidProgressionRequestException("Building is already in progress");
            }
        }
    }

    public void generateBuildingCreationModel(Kingdom kingdom, ProgressionDTO progressionDTO) {
        Long timeOfBuildingCreation = timeService.calculateTimeOfBuildingCreation();
        ProgressionModel progressionModel = new ProgressionModel();
        progressionModel.setType(progressionDTO.getType());
        progressionModel.setTimeToProgress(timeOfBuildingCreation);
        saveProgressionIntoKingdom(progressionModel, kingdom);
    }

    public void generateBuildingUpgradeModel(Kingdom kingdom, Long buildingId) throws BuildingNotFoundException {
        ProgressionModel progressionModel = new ProgressionModel();
        Building building = buildingService.findBuildingInKingdom(kingdom, buildingId);
        Integer levelOfTownHall = buildingService.getLevelOfTownHall(kingdom.getKingdomsBuildings());
        Long timeOfBuildingUpgrade = timeService.calculateTimeOfBuildingUpgrade(building.getLevel(), levelOfTownHall);
        progressionModel.setGameObjectId(buildingId);
        progressionModel.setType(building.getType().getName());
        progressionModel.setTimeToProgress(timeOfBuildingUpgrade);
        saveProgressionIntoKingdom(progressionModel, kingdom);
    }

    public void generateTroopCreationModel(Kingdom kingdom) {
        ProgressionModel progressionModel = new ProgressionModel();
        progressionModel.setType("TROOP");
        Double troopCreationTimeMultiplier = buildingService.getTroopProgressionTimeMultiplier(kingdom);
        Long timeOfTroopCreationTime = timeService.calculateTimeOfTroopCreation(troopCreationTimeMultiplier);
        progressionModel.setTimeToProgress(timeOfTroopCreationTime);
        saveProgressionIntoKingdom(progressionModel, kingdom);
    }

    public void generateTroopUpgradeModel(Integer level, Kingdom kingdom) {
        List<Troop> troopsForUpgrade = troopService.getTroopsWithTheGivenLevel(level, kingdom);
        Double troopUpgradeTimeMultiplier = buildingService.getTroopProgressionTimeMultiplier(kingdom);
        Long timeOfTroopUpgrade = timeService.calculateTimeOfTroopUpgrade(troopUpgradeTimeMultiplier, level);
        ProgressionModel progressionModel = new ProgressionModel();
        for (Troop troop : troopsForUpgrade) {
            progressionModel.setGameObjectId(troop.getId());
            progressionModel.setType("TROOP"); //need a number of amount
            progressionModel.setTimeToProgress(timeOfTroopUpgrade);
            saveProgressionIntoKingdom(progressionModel, kingdom);
        }
    }

    private void saveProgressionIntoKingdom(ProgressionModel progressionModel, Kingdom kingdom){
        progressionModel.setProgressKingdom(kingdom);
        kingdom.getKingdomsProgresses().add(progressionModel);
        kingdomService.save(kingdom);
    }

}
