package rueppellii.backend2.tribes.building.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import rueppellii.backend2.tribes.building.exception.UpgradeFailedException;
import rueppellii.backend2.tribes.building.persistence.model.Building;
import rueppellii.backend2.tribes.building.service.BuildingService;
import rueppellii.backend2.tribes.building.utility.ListKingdomsBuildingsDTO;
import rueppellii.backend2.tribes.gameUtility.purchaseService.PurchaseService;
import rueppellii.backend2.tribes.kingdom.exception.KingdomNotFoundException;
import rueppellii.backend2.tribes.kingdom.persistence.model.Kingdom;
import rueppellii.backend2.tribes.kingdom.service.KingdomService;
import rueppellii.backend2.tribes.building.exception.BuildingNotFoundException;
import rueppellii.backend2.tribes.progression.exception.InvalidProgressionRequestException;
import rueppellii.backend2.tribes.progression.service.ProgressionService;
import rueppellii.backend2.tribes.progression.util.ProgressionDTO;
import rueppellii.backend2.tribes.resource.exception.NoResourceException;
import rueppellii.backend2.tribes.resource.service.ResourceService;
import rueppellii.backend2.tribes.troop.exception.TroopNotFoundException;

import java.security.Principal;

@RestController
@RequestMapping("/api/kingdom/building")
public class BuildingController {

    private KingdomService kingdomService;
    private ProgressionService progressionService;
    private PurchaseService purchaseService;
    private ResourceService resourceService;
    private BuildingService buildingService;

    @Autowired
    public BuildingController(KingdomService kingdomService, ProgressionService progressionService, PurchaseService purchaseService, ResourceService resourceService, BuildingService buildingService) {
        this.kingdomService = kingdomService;
        this.progressionService = progressionService;
        this.purchaseService = purchaseService;
        this.resourceService = resourceService;
        this.buildingService = buildingService;
    }

    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    public ListKingdomsBuildingsDTO showBuildings(Principal principal) throws KingdomNotFoundException {
        ListKingdomsBuildingsDTO dto = new ListKingdomsBuildingsDTO();
        Kingdom kingdom = kingdomService.findByPrincipal(principal);
        dto.setBuildings(kingdom.getKingdomsBuildings());
        return dto;
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.OK)
    public void createBuilding(@RequestBody ProgressionDTO progressionDTO,
                               Principal principal, BindingResult bindingResult) throws KingdomNotFoundException,
            TroopNotFoundException, BuildingNotFoundException, NoResourceException, InvalidProgressionRequestException {
        Kingdom kingdom = kingdomService.findByPrincipal(principal);
        progressionService.updateProgression(kingdom);
        resourceService.updateResources(kingdom);

        progressionService.validateProgressionRequest(bindingResult, progressionDTO);
        progressionService.checkIfBuildingIsAlreadyInProgress(kingdom);

        purchaseService.buyBuilding(kingdom.getId());
        progressionService.generateBuildingCreationModel(kingdom, progressionDTO);
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public void upgradeBuilding(@PathVariable Long id, Principal principal) throws KingdomNotFoundException, TroopNotFoundException, BuildingNotFoundException, NoResourceException, UpgradeFailedException, InvalidProgressionRequestException {
        Kingdom kingdom = kingdomService.findByPrincipal(principal);
        progressionService.updateProgression(kingdom);
        resourceService.updateResources(kingdom);

        Building building = buildingService.validateBuildingUpgrade(kingdom, id);
        progressionService.checkIfBuildingIsAlreadyInProgress(kingdom);
        purchaseService.payForBuildingUpgrade(kingdom.getId(), building);

        progressionService.generateBuildingUpgradeModel(kingdom, id);
    }
}
