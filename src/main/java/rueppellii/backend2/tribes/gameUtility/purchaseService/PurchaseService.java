package rueppellii.backend2.tribes.gameUtility.purchaseService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rueppellii.backend2.tribes.building.persistence.model.Building;
import rueppellii.backend2.tribes.building.service.BuildingService;
import rueppellii.backend2.tribes.kingdom.exception.KingdomNotFoundException;
import rueppellii.backend2.tribes.kingdom.persistence.model.Kingdom;
import rueppellii.backend2.tribes.kingdom.service.KingdomService;
import rueppellii.backend2.tribes.progression.exception.BuildingNotFoundException;
import rueppellii.backend2.tribes.resource.service.ResourceService;
import rueppellii.backend2.tribes.resource.utility.ResourceType;
import rueppellii.backend2.tribes.resource.exception.NoResourceException;
import rueppellii.backend2.tribes.troop.exception.TroopNotFoundException;
import rueppellii.backend2.tribes.troop.persistence.model.Troop;
import rueppellii.backend2.tribes.troop.service.TroopService;

import static rueppellii.backend2.tribes.troop.utility.TroopFactory.makeTroop;

@Service
public class PurchaseService {

    private KingdomService kingdomService;
    private ResourceService resourceService;
    private TroopService troopService;
    private BuildingService buildingService;

    @Autowired
    public PurchaseService(KingdomService kingdomService, ResourceService resourceService, TroopService troopService, BuildingService buildingService) {
        this.kingdomService = kingdomService;
        this.resourceService = resourceService;
        this.troopService = troopService;
        this.buildingService = buildingService;
    }

    public Long getKingdomsGoldAmount(Long kingdomId) throws NoResourceException {
        return resourceService.returnResource(ResourceType.GOLD, kingdomId).getAmount();
    }

    public Boolean hasEnoughGold(Long kingdomId, Integer amount) throws NoResourceException {
        return getKingdomsGoldAmount(kingdomId) >= amount;
    }

    public void buyTroop(Long kingdomId) throws NoResourceException, KingdomNotFoundException {
        Integer troopPrice = 10;
        if (hasEnoughGold(kingdomId, troopPrice)) {
            resourceService.minusGoldAmount(troopPrice, kingdomId);
            return;
        }
        throw new NoResourceException("You don't have enough gold!");
    }

    public void upgradeTroop(Long kingdomId, Long troopId) throws NoResourceException, TroopNotFoundException {
        Troop troop = troopService.findById(troopId);
        Integer desiredLevel = troop.getLevel() + 1;
        Integer upgradePrice = 10 * desiredLevel;
        if (hasEnoughGold(kingdomId, upgradePrice)) {
            resourceService.minusGoldAmount(upgradePrice, kingdomId);
            return;
        }
        throw new NoResourceException("You don't have enough gold!");
    }

    public void buyBuilding(Long kingdomId) throws NoResourceException {
        Integer buildingPrice = 50;
         if (hasEnoughGold(kingdomId, buildingPrice)) {
            resourceService.minusGoldAmount(buildingPrice, kingdomId);
            return;
        }
        throw new NoResourceException("You don't have enough gold!");
    }

    public void upgradeBuilding(Long kingdomId, Long buildingId) throws NoResourceException, BuildingNotFoundException {
        Building building = buildingService.findById(buildingId);
        Integer desiredLevel = building.getLevel() + 1;
        Integer upgradePrice = 10 * desiredLevel;
        if (hasEnoughGold(kingdomId, upgradePrice)) {
            resourceService.minusGoldAmount(upgradePrice, kingdomId);
            return;
        }
        throw new NoResourceException("You don't have enough gold!");
    }
}
