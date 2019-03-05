package rueppellii.backend2.tribes.gameUtility.gameChainService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rueppellii.backend2.tribes.building.exception.BuildingNotFoundException;
import rueppellii.backend2.tribes.building.exception.UpgradeFailedException;
import rueppellii.backend2.tribes.building.persistence.model.Building;
import rueppellii.backend2.tribes.building.service.BuildingService;
import rueppellii.backend2.tribes.gameUtility.purchaseService.PurchaseService;
import rueppellii.backend2.tribes.kingdom.exception.KingdomNotFoundException;
import rueppellii.backend2.tribes.kingdom.persistence.model.Kingdom;
import rueppellii.backend2.tribes.kingdom.service.KingdomService;
import rueppellii.backend2.tribes.progression.exception.InvalidProgressionRequestException;
import rueppellii.backend2.tribes.progression.service.ProgressionService;
import rueppellii.backend2.tribes.progression.util.ProgressionDTO;
import rueppellii.backend2.tribes.resource.exception.NoResourceException;
import rueppellii.backend2.tribes.resource.service.ResourceService;
import rueppellii.backend2.tribes.integrationTests.troop.exception.TroopNotFoundException;
import rueppellii.backend2.tribes.integrationTests.troop.service.TroopService;

import java.security.Principal;

@Service
public class GameChainService {

    private KingdomService kingdomService;
    private ResourceService resourceService;
    private ProgressionService progressionService;
    private PurchaseService purchaseService;
    private BuildingService buildingService;
    private TroopService troopService;

    @Autowired
    public GameChainService(KingdomService kingdomService, ResourceService resourceService,
                            ProgressionService progressionService, PurchaseService purchaseService,
                            BuildingService buildingService, TroopService troopService) {
        this.kingdomService = kingdomService;
        this.resourceService = resourceService;
        this.progressionService = progressionService;
        this.purchaseService = purchaseService;
        this.buildingService = buildingService;
        this.troopService = troopService;
    }

    public void createBuildingChain(ProgressionDTO progressionDTO, Principal principal)
            throws KingdomNotFoundException, TroopNotFoundException,
            BuildingNotFoundException, InvalidProgressionRequestException, NoResourceException {
        Kingdom kingdom = kingdomService.findByPrincipal(principal);
        updateGameAssets(kingdom);
        progressionService.validateProgressionRequest(progressionDTO, kingdom);
        purchaseService.buyBuilding(kingdom.getId());
        progressionService.generateBuildingCreationModel(kingdom, progressionDTO);
    }

    public void upgradeBuildingChain(Long id, Principal principal)
            throws KingdomNotFoundException, TroopNotFoundException, BuildingNotFoundException,
            UpgradeFailedException, InvalidProgressionRequestException, NoResourceException {
        Kingdom kingdom = kingdomService.findByPrincipal(principal);
        updateGameAssets(kingdom);
        Building building = buildingService.validateBuildingUpgrade(kingdom, id);
        progressionService.checkIfBuildingIsAlreadyInProgress(kingdom);
        purchaseService.payForBuildingUpgrade(kingdom.getId(), building);
        progressionService.generateBuildingUpgradeModel(kingdom, id);
    }

    public void createTroopGameChain(Principal principal)
            throws KingdomNotFoundException, TroopNotFoundException, BuildingNotFoundException,
            NoResourceException {
        Kingdom kingdom = kingdomService.findByPrincipal(principal);
        updateGameAssets(kingdom);
        purchaseService.buyTroop(kingdom.getId());
        progressionService.generateTroopCreationModel(kingdom);
    }

    public void upgradeTroopGameChain(Integer level, Principal principal)
            throws KingdomNotFoundException, TroopNotFoundException, BuildingNotFoundException,
            NoResourceException, InvalidProgressionRequestException {
        Kingdom kingdom = kingdomService.findByPrincipal(principal);
        updateGameAssets(kingdom);
        troopService.validateUpgradeTroopRequest(level, kingdom);
        purchaseService.upgradeTroops(level, kingdom);
        progressionService.generateTroopUpgradeModel(level, kingdom);
    }

    public void updateGameAssets(Kingdom kingdom)
            throws TroopNotFoundException, BuildingNotFoundException {
        progressionService.updateProgression(kingdom);
        resourceService.updateResources(kingdom.getKingdomsResources());
    }
}
