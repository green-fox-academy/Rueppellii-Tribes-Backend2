package rueppellii.backend2.tribes.progression.service;

import org.apache.commons.lang3.EnumUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rueppellii.backend2.tribes.building.exception.UpgradeFailedException;
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
import rueppellii.backend2.tribes.resource.service.ResourceService;
import rueppellii.backend2.tribes.troop.exception.TroopNotFoundException;
import rueppellii.backend2.tribes.troop.persistence.model.Troop;
import rueppellii.backend2.tribes.troop.service.TroopService;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static rueppellii.backend2.tribes.gameUtility.timeService.TimeConstants.TROOP_PROGRESSION_TIME;

@Service
public class ProgressionService {

    private ProgressionModelRepository progressionModelRepository;
    private BuildingService buildingService;
    private TroopService troopService;
    private TimeService timeService;
    private KingdomService kingdomService;
    private ResourceService resourceService;

    @Autowired
    public ProgressionService(ProgressionModelRepository progressionModelRepository,
                              BuildingService buildingService, TroopService troopService,
                              TimeService timeService, KingdomService kingdomService,
                              ResourceService resourceService) {
        this.progressionModelRepository = progressionModelRepository;
        this.buildingService = buildingService;
        this.troopService = troopService;
        this.timeService = timeService;
        this.kingdomService = kingdomService;
        this.resourceService = resourceService;
    }

    public void updateProgression(Kingdom kingdom)
            throws TroopNotFoundException, BuildingNotFoundException {
        List<ProgressionModel> progressions = progressionModelRepository.findAllByProgressKingdom(kingdom);
        for (ProgressionModel p : progressions) {
            System.out.println(p.getType());
            if (timeService.timeIsUp(p)) {
                progress(p, kingdom);
            }
        }
    }

    private void progress(ProgressionModel progressionModel, Kingdom kingdom)
            throws TroopNotFoundException, BuildingNotFoundException {
        if (progressionModel.getGameObjectId() == null) {
            if (progressionModel.getType().equals("TROOP")) {
                troopService.createTroop(kingdom);
                resourceService.feedTheTroop(kingdom);
                progressionModelRepository.deleteById(progressionModel.getId());
                return;
            }
            buildingService.createBuilding(progressionModel, kingdom);
            resourceService.setResourcePerMinute(progressionModel.getType(), kingdom.getKingdomsResources());
            progressionModelRepository.deleteById(progressionModel.getId());
            return;
        }
        if (progressionModel.getType().equals("TROOP")) {
            troopService.upgradeTroop(progressionModel);
            progressionModelRepository.deleteById(progressionModel.getId());
            return;
        }
        buildingService.upgradeBuilding(progressionModel, kingdom);
        resourceService.setResourcePerMinute(progressionModel.getType(), kingdom.getKingdomsResources());
        progressionModelRepository.deleteById(progressionModel.getId());
    }

    public void validateProgressionRequest(ProgressionDTO progressionDTO, Kingdom kingdom)
            throws InvalidProgressionRequestException {
        if (!EnumUtils.isValidEnum(BuildingType.class, progressionDTO.getType())) {
            throw new InvalidProgressionRequestException("Wrong type!");
        }
        if (progressionDTO.getType().toUpperCase().equals("TOWNHALL")) {
            throw new InvalidProgressionRequestException("Only one town hall to a kingdom");
        }
        checkIfBuildingIsAlreadyInProgress(kingdom);
    }

    public void checkIfBuildingIsAlreadyInProgress(Kingdom kingdom)
            throws InvalidProgressionRequestException {
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

    public void generateBuildingUpgradeModel(Kingdom kingdom, Long buildingId)
            throws BuildingNotFoundException {
        ProgressionModel progressionModel = new ProgressionModel();
        Building building = buildingService.findBuildingInKingdom(kingdom, buildingId);
        Integer levelOfTownHall = buildingService.getLevelOfTownHall(kingdom.getKingdomsBuildings());
        Long timeOfBuildingUpgrade = timeService.calculateTimeOfBuildingUpgrade(building.getLevel(), levelOfTownHall);
        progressionModel.setGameObjectId(buildingId);
        progressionModel.setType(building.getType().getName());
        progressionModel.setTimeToProgress(timeOfBuildingUpgrade);
        saveProgressionIntoKingdom(progressionModel, kingdom);
        resourceService.setResourcePerMinute(progressionModel.getType(), kingdom.getKingdomsResources());
    }

    public void generateTroopCreationModel(Kingdom kingdom) {
        ProgressionModel progressionModel = new ProgressionModel();
        progressionModel.setType("TROOP");
        Double troopCreationTimeMultiplier = buildingService.getTroopProgressionTimeMultiplier(kingdom);
        Long timeOfTroopCreation = timeService.calculateTimeOfTroopCreation(troopCreationTimeMultiplier);
        progressionModel.setTimeToProgress(System.currentTimeMillis() + timeOfTroopCreation);
        if (findTroopProgressionWithLongestTime(kingdom).isPresent()) {
            progressionModel.setTimeToProgress(findTroopProgressionWithLongestTime(kingdom).get().getTimeToProgress() + timeOfTroopCreation);
        }
        saveProgressionIntoKingdom(progressionModel, kingdom);
    }

    public Optional<ProgressionModel> findTroopProgressionWithLongestTime(Kingdom kingdom) {
        return kingdom.getKingdomsProgresses().stream()
                .filter(troop -> troop.getType().equals("TROOP"))
                .max(Comparator.comparing(ProgressionModel::getTimeToProgress));
    }

    public void generateTroopUpgradeModel(Integer level, Kingdom kingdom) throws UpgradeFailedException {
        List<Troop> troopsForUpgrade = troopService.getTroopsWithTheGivenLevel(level, kingdom);
        troopUpgradeValidator(kingdom, getTroopsForUpgradeIds(troopsForUpgrade));
        Double troopUpgradeTimeMultiplier = buildingService.getTroopProgressionTimeMultiplier(kingdom);
        Long timeOfTroopUpgrade = timeService.calculateTimeOfTroopUpgrade(troopUpgradeTimeMultiplier, level);
        ProgressionModel progressionModel = new ProgressionModel();
        for (int i = 0; i < troopsForUpgrade.size(); i++) {
            Troop troop = troopsForUpgrade.get(i);
            progressionModel.setGameObjectId(troop.getId());
            progressionModel.setType("TROOP");
            progressionModel.setTimeToProgress(System.currentTimeMillis() + (timeOfTroopUpgrade * (i + 1)));
            saveProgressionIntoKingdom(progressionModel, kingdom);
        }
    }

    public List<Long> getTroopsForUpgradeIds(List<Troop> troopsForUpgrade) {
        return troopsForUpgrade.stream().map(Troop::getId).collect(Collectors.toList());
    }

    public void troopUpgradeValidator(Kingdom kingdom, List<Long> troopsForUpgradeIds) throws UpgradeFailedException {
        for(ProgressionModel troopProgression : kingdom.getKingdomsProgresses()) {
            if (troopProgression.getType().equals("TROOP")
                    && troopProgression.getGameObjectId() != null
                    && troopsForUpgradeIds.contains(troopProgression.getGameObjectId())) {
                throw new UpgradeFailedException("Troops are already training");
            }
        }
    }

    private void saveProgressionIntoKingdom(ProgressionModel progressionModel, Kingdom kingdom){
        progressionModel.setProgressKingdom(kingdom);
        kingdom.getKingdomsProgresses().add(progressionModel);
        kingdomService.save(kingdom);
    }
}
