package rueppellii.backend2.tribes.progression.service;

import org.apache.commons.lang3.EnumUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
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

    public List<ProgressionModel> findAllByKingdom(Kingdom kingdom) {
        return progressionModelRepository.findAllByProgressKingdom(kingdom);
    }

    public void refreshProgression(Kingdom kingdom)
            throws TroopNotFoundException, BuildingNotFoundException,
            InvalidProgressionRequestException {
        List<ProgressionModel> progressions = findAllByKingdom(kingdom);
        for (ProgressionModel p : progressions) {
            if (timeService.timeIsUp(p)) {
                progress(p, kingdom);
            }
        }
    }

    public void progress(ProgressionModel progressionModel, Kingdom kingdom)
            throws TroopNotFoundException, BuildingNotFoundException {
        if (progressionModel.getGameObjectId() == null) {
            if (progressionModel.getType().equals("TROOP")) {
                troopService.createTroop(kingdom);
                progressionModelRepository.deleteById(progressionModel.getId());
                return;
            }
            buildingService.createBuilding(progressionModel, kingdom);
            progressionModelRepository.deleteById(progressionModel.getId());
            return;
        }

        if (progressionModel.getType().equals("TROOP")) {
            troopService.upgradeTroop(progressionModel);
            progressionModelRepository.deleteById(progressionModel.getId());
            return;
        }
        buildingService.upgradeBuilding(progressionModel.getGameObjectId());
        progressionModelRepository.deleteById(progressionModel.getId());
    }

    public void validateProgressionRequest(BindingResult bindingResult,
                                           ProgressionDTO progressionDTO) throws InvalidProgressionRequestException {
        if (bindingResult.hasErrors() || progressionDTO.getType() == null) {
            throw new InvalidProgressionRequestException("Missing parameter: type");
        } else if (!EnumUtils.isValidEnum(BuildingType.class, progressionDTO.getType())) {
            throw new InvalidProgressionRequestException("Wrong type!");
        } else if (progressionDTO.getType().toUpperCase().equals("TOWNHALL")) {
            throw new InvalidProgressionRequestException("Only one town hall to a kingdom");
        }
    }

    public void checkIfBuildingIsAlreadyInProgress(Kingdom kingdom) throws InvalidProgressionRequestException {
        for (ProgressionModel type : progressionModelRepository.findAllByProgressKingdom(kingdom)) {
            if (type.getType().toUpperCase().equals("TOWNHALL") ||
                    type.getType().toUpperCase().equals("FARM") ||
                    type.getType().toUpperCase().equals("MINE") ||
                    type.getType().toUpperCase().equals("BARRACKS")) {
                throw new InvalidProgressionRequestException("Building is already in progress");
            }
        }
    }

    public void generateBuildingCreationModel(Kingdom kingdom, ProgressionDTO progressionDTO) {
        ProgressionModel progressionModel = new ProgressionModel();
        progressionModel.setType(progressionDTO.getType());
        progressionModel.setTimeToProgress(timeService.calculateTimeOfBuildingCreation(kingdom));
        progressionModel.setProgressKingdom(kingdom);
        kingdom.getKingdomsProgresses().add(progressionModel);
        kingdomService.save(kingdom);
    }

    public void generateBuildingUpgradeModel(Kingdom kingdom, Long buildingId) throws BuildingNotFoundException {
        ProgressionModel progressionModel = new ProgressionModel();
        Building building = buildingService.findBuildingInKingdom(kingdom, buildingId);
        progressionModel.setGameObjectId(buildingId);
        progressionModel.setType(building.getType().getName());
        progressionModel.setTimeToProgress(timeService.calculateTimeOfBuildingUpgrade(kingdom));
        progressionModel.setProgressKingdom(kingdom);
        kingdom.getKingdomsProgresses().add(progressionModel);
        kingdomService.save(kingdom);
    }

    public void generateTroopCreationModel(Kingdom kingdom) {
        ProgressionModel progressionModel = new ProgressionModel();
        progressionModel.setType("TROOP");
        progressionModel.setTimeToProgress(timeService.calculateTimeOfTroopCreation(kingdom));
        progressionModel.setProgressKingdom(kingdom);
        kingdom.getKingdomsProgresses().add(progressionModel);
        kingdomService.save(kingdom);
    }

    public void generateTroopUpgradeModel(Kingdom kingdom, Long id) {
        ProgressionModel progressionModel = new ProgressionModel();
        progressionModel.setGameObjectId(id);
        progressionModel.setType("TROOP");
        progressionModel.setTimeToProgress(timeService.calculateTimeOfTroopUpgrade(kingdom));
        progressionModel.setProgressKingdom(kingdom);
        kingdom.getKingdomsProgresses().add(progressionModel);
        kingdomService.save(kingdom);
    }
}
