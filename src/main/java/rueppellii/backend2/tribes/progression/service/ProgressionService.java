package rueppellii.backend2.tribes.progression.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rueppellii.backend2.tribes.building.service.BuildingService;
import rueppellii.backend2.tribes.gameUtility.timeService.TimeServiceImpl;
import rueppellii.backend2.tribes.kingdom.persistence.model.Kingdom;
import rueppellii.backend2.tribes.progression.exception.BuildingNotFoundException;
import rueppellii.backend2.tribes.progression.persistence.ProgressionModel;
import rueppellii.backend2.tribes.progression.persistence.ProgressionModelRepository;
import rueppellii.backend2.tribes.troop.exception.TroopNotFoundException;
import rueppellii.backend2.tribes.troop.service.TroopService;

import java.util.List;

@Service
public class ProgressionService {

    private ProgressionModelRepository progressionModelRepository;
    private BuildingService buildingService;
    private TroopService troopService;
    private TimeServiceImpl timeService;

    @Autowired
    public ProgressionService(ProgressionModelRepository progressionModelRepository, BuildingService buildingService, TroopService troopService, TimeServiceImpl timeService) {
        this.progressionModelRepository = progressionModelRepository;
        this.buildingService = buildingService;
        this.troopService = troopService;
        this.timeService = timeService;
    }

    public List<ProgressionModel> findAllByKingdom(Kingdom kingdom) {
        return progressionModelRepository.findAllByProgressKingdom(kingdom);
    }

    public void refreshProgression(Kingdom kingdom) throws TroopNotFoundException, BuildingNotFoundException {
        List<ProgressionModel> progressions = findAllByKingdom(kingdom);
        for (ProgressionModel p : progressions) {
            if(timeService.timeIsUp(p)){
                progress(p, kingdom);
            }
        }
    }

    public void progress(ProgressionModel progressionModel, Kingdom kingdom) throws TroopNotFoundException, BuildingNotFoundException {
        if (progressionModel.getGameObjectId() == null) {
            if (progressionModel.getType().equals("TROOP")) {
                troopService.createTroop(kingdom);
                progressionModelRepository.deleteById(progressionModel.getId());
            }
            buildingService.createBuilding(progressionModel, kingdom);
            progressionModelRepository.deleteById(progressionModel.getId());
        }
        if (progressionModel.getType().equals("TROOP")) {
            troopService.upgradeTroop(progressionModel, kingdom);
            progressionModelRepository.deleteById(progressionModel.getId());
        }
        buildingService.upgradeBuilding(progressionModel, kingdom);
        progressionModelRepository.deleteById(progressionModel.getId());
    }
}
