package rueppellii.backend2.tribes.gameUtility.purchaseService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rueppellii.backend2.tribes.building.persistence.model.Building;
import rueppellii.backend2.tribes.kingdom.persistence.model.Kingdom;
import rueppellii.backend2.tribes.resource.service.ResourceService;
import rueppellii.backend2.tribes.resource.exception.NoResourceException;
import rueppellii.backend2.tribes.troop.persistence.model.Troop;
import rueppellii.backend2.tribes.troop.service.TroopService;

import java.util.List;

import static rueppellii.backend2.tribes.gameUtility.purchaseService.UpgradeConstants.BUILDING_PRICE;
import static rueppellii.backend2.tribes.gameUtility.purchaseService.UpgradeConstants.TROOP_PRICE;

@Service
public class PurchaseService {

    private ResourceService resourceService;
    private TroopService troopService;

    @Autowired
    public PurchaseService(ResourceService resourceService, TroopService troopService) {
        this.resourceService = resourceService;
        this.troopService = troopService;
    }

    public void buyTroop(Long kingdomId) throws NoResourceException {
        if (resourceService.hasEnoughGold(kingdomId, TROOP_PRICE)) {
            resourceService.minusGoldAmount(TROOP_PRICE, kingdomId);
            return;
        }
        throw new NoResourceException("Not enough gold!");
    }

    public void upgradeTroops(Integer level, Kingdom kingdom) throws NoResourceException {
        List<Troop> troopsForUpgrade = troopService.getTroopsWithTheGivenLevel(level, kingdom);
        int amountOfTroopToUpgrade = troopsForUpgrade.size();
        Integer upgradePrice = TROOP_PRICE * (level + 1) * amountOfTroopToUpgrade;
        if (resourceService.hasEnoughGold(kingdom.getId(), upgradePrice)) {
            resourceService.minusGoldAmount(upgradePrice, kingdom.getId());
            return;
        }
        throw new NoResourceException("Not enough gold!");
    }

    public void buyBuilding(Long kingdomId) throws NoResourceException {
         if (resourceService.hasEnoughGold(kingdomId, BUILDING_PRICE)) {
            resourceService.minusGoldAmount(BUILDING_PRICE, kingdomId);
            return;
        }
        throw new NoResourceException("Not enough gold!");
    }

    public void payForBuildingUpgrade(Long kingdomId, Building building) throws NoResourceException {
        if (resourceService.hasEnoughGold(kingdomId, BUILDING_PRICE)) {
            Integer upgradePrice = BUILDING_PRICE * (building.getLevel() + 1);
            resourceService.minusGoldAmount(upgradePrice, kingdomId);
            return;
        }
        throw new NoResourceException("Not enough gold!");
    }
}
