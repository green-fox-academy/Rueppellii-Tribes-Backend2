package rueppellii.backend2.tribes.building.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import rueppellii.backend2.tribes.building.exception.UpgradeFailedException;
import rueppellii.backend2.tribes.building.utility.ListKingdomsBuildingsDTO;
import rueppellii.backend2.tribes.gameUtility.gameChain.GameChainService;
import rueppellii.backend2.tribes.kingdom.exception.KingdomNotFoundException;
import rueppellii.backend2.tribes.building.exception.BuildingNotFoundException;
import rueppellii.backend2.tribes.kingdom.persistence.model.Kingdom;
import rueppellii.backend2.tribes.kingdom.service.KingdomService;
import rueppellii.backend2.tribes.progression.exception.InvalidProgressionRequestException;
import rueppellii.backend2.tribes.progression.util.ProgressionDTO;
import rueppellii.backend2.tribes.resource.exception.NoResourceException;
import rueppellii.backend2.tribes.troop.exception.TroopNotFoundException;
import rueppellii.backend2.tribes.user.utility.ErrorResponse;

import javax.validation.Valid;
import java.security.Principal;

@RestController
@RequestMapping("/api/kingdom/building")
public class BuildingController {

    private GameChainService gameChainService;
    private KingdomService kingdomService;

    @Autowired
    public BuildingController(GameChainService gameChainService, KingdomService kingdomService) {
        this.gameChainService = gameChainService;
        this.kingdomService = kingdomService;
    }

    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    public ListKingdomsBuildingsDTO showBuildings(Principal principal) throws KingdomNotFoundException, TroopNotFoundException, BuildingNotFoundException {
        Kingdom kingdom = kingdomService.findByPrincipal(principal);
        gameChainService.updateGameAssets(kingdom);
        ListKingdomsBuildingsDTO dto = new ListKingdomsBuildingsDTO();
        dto.setBuildings(kingdomService.findByPrincipal(principal).getKingdomsBuildings());
        return dto;
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.OK)
    public ConfirmDTO createBuilding(@RequestBody @Valid ProgressionDTO progressionDTO, Principal principal)
            throws KingdomNotFoundException, TroopNotFoundException, BuildingNotFoundException,
            NoResourceException, InvalidProgressionRequestException {
        gameChainService.createBuildingChain(progressionDTO, principal);
        ConfirmDTO confirmDTO = new ConfirmDTO();
        confirmDTO.setConfirm("ok");
        return confirmDTO;
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public void upgradeBuilding(@PathVariable Long id, Principal principal)
            throws KingdomNotFoundException, TroopNotFoundException, BuildingNotFoundException,
            NoResourceException, UpgradeFailedException, InvalidProgressionRequestException {
        gameChainService.upgradeBuildingChain(id, principal);
    }
}
