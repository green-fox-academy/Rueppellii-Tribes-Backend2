package rueppellii.backend2.tribes.gameUtility.purchaseService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rueppellii.backend2.tribes.building.persistence.model.Building;
import rueppellii.backend2.tribes.building.service.BuildingService;
import rueppellii.backend2.tribes.building.utility.BuildingDTO;
import rueppellii.backend2.tribes.kingdom.persistence.model.Kingdom;
import rueppellii.backend2.tribes.kingdom.service.KingdomService;
import rueppellii.backend2.tribes.resource.service.ResourceServiceImp;
import rueppellii.backend2.tribes.resource.utility.ResourceType;
import rueppellii.backend2.tribes.resource.exception.NoResourceException;
import rueppellii.backend2.tribes.troop.persistence.model.Troop;
import rueppellii.backend2.tribes.troop.service.TroopServiceImp;

@Service
public class PurchaseService {

    private KingdomService kingdomService;
    private ResourceServiceImp resourceServiceImp;
    private TroopServiceImp troopServiceImp;
    private BuildingService buildingService;

    @Autowired
    public PurchaseService(KingdomService kingdomService, ResourceServiceImp resourceServiceImp, TroopServiceImp troopServiceImp, BuildingService buildingService) {
        this.kingdomService = kingdomService;
        this.resourceServiceImp = resourceServiceImp;
        this.troopServiceImp = troopServiceImp;
        this.buildingService = buildingService;
    }

    public Long getKingdomsGoldAmount(Long kingdomId) throws NoResourceException {
        return resourceServiceImp.returnResource(ResourceType.GOLD, kingdomId).getAmount();
    }

    public boolean hasEnoughGold(Long kingdomId, Integer amount) throws Exception {
        return getKingdomsGoldAmount(kingdomId) > amount;
    }

    public void buyTroop(Long kingdomId) throws Exception {
        Kingdom kingdom = kingdomService.getKingdomById(kingdomId);
        Integer troopPrice = 10;
        if (hasEnoughGold(kingdomId, troopPrice)) {
            resourceServiceImp.minusGoldAmount(troopPrice, kingdomId);
            Troop troop = new Troop();
            troopServiceImp.saveTroop(troop);
            kingdom.getKingdomsTroops().add(troop);
            kingdomService.saveKingdom(kingdom);
            return;
        }
        throw new NoResourceException("You don't have enough gold!");
    }

    public void upgradeTroop(Long kingdomId, Long troopId) throws Exception {
        Troop troop = troopServiceImp.findById(troopId);
        Integer desiredLevel = troop.getLevel() + 1;
        Integer upgradePrice = 10 * desiredLevel;
        if (hasEnoughGold(kingdomId, upgradePrice)) {
            troop.setLevel(desiredLevel);
            troopServiceImp.saveTroop(troop);
            resourceServiceImp.minusGoldAmount(upgradePrice, kingdomId);
            return;
        }
        throw new NoResourceException("You don't have enough gold!");
    }

    public void buyBuilding(Long kingdomId, BuildingDTO buildingDTO) throws Exception {
        Kingdom kingdom = kingdomService.getKingdomById(kingdomId);
        Integer buildingPrice = 100;

        if (hasEnoughGold(kingdomId, buildingPrice)) {
            resourceServiceImp.minusGoldAmount(buildingPrice, kingdomId);
            buildingService.createBuilding(buildingDTO, kingdom.getApplicationUser().getUsername());
            return;
        }
        throw new NoResourceException("You don't have enough gold!");
    }

    public void upgradeBuilding(Long kingdomId, Long buildingId) throws Exception {
        Building building = buildingService.findByIds(buildingId, kingdomId);
        Integer desiredLevel = building.getLevel() + 1;
        Integer upgradePrice = 100 * desiredLevel;
        if (hasEnoughGold(kingdomId, upgradePrice)) {
            building.setLevel(desiredLevel);
            buildingService.saveBuilding(building);
            resourceServiceImp.minusGoldAmount(upgradePrice, kingdomId);
            return;
        }
        throw new NoResourceException("You don't have enough gold!");
    }
}
