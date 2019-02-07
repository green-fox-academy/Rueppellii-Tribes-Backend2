package rueppellii.backend2.tribes.upgrade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rueppellii.backend2.tribes.kingdom.Kingdom;
import rueppellii.backend2.tribes.kingdom.KingdomService;
import rueppellii.backend2.tribes.resource.ResourceService;
import rueppellii.backend2.tribes.resource.ResourceType;
import rueppellii.backend2.tribes.troop.Troop;
import rueppellii.backend2.tribes.troop.TroopServiceImp;

@Service
public class PurchaseService {

    private KingdomService kingdomService;
    private ResourceService resourceService;
    private TroopServiceImp troopServiceImp;

    @Autowired
    public PurchaseService(KingdomService kingdomService, ResourceService resourceService, TroopServiceImp troopServiceImp) {
        this.kingdomService = kingdomService;
        this.resourceService = resourceService;
        this.troopServiceImp = troopServiceImp;
    }

    public Integer getKingdomsGoldAmount(Long kingdomId) throws Exception {
        return resourceService.returnResource(ResourceType.RESOURCE_GOLD, kingdomId).getAmount();
    }

    public boolean hasEnoughGold(Long kingdomId, Integer amount) throws Exception {
        return getKingdomsGoldAmount(kingdomId) > amount;
    }

    public void buyTroop(Long kingdomId) throws Exception {
        Kingdom kingdom = kingdomService.getKingdomById(kingdomId);
        Integer troopPrice = 10;
        if (hasEnoughGold(kingdomId, troopPrice)) {
            resourceService.minusGoldAmount(troopPrice, kingdomId);
            Troop troop = new Troop();
            troopServiceImp.saveTroop(troop);
            kingdom.getKingdomsTroops().add(troop);
            kingdomService.saveKingdom(kingdom);
        }
    }

    public void upgradeTroop(Long kingdomId, Long troopId) throws Exception {
        Troop troop = troopServiceImp.findByIds(troopId, kingdomId);
        Integer desiredLevel = troop.getLevel() + 1;
        Integer upgradePrice = 10 * desiredLevel;
        if (hasEnoughGold(kingdomId, upgradePrice)) {
            troop.setLevel(desiredLevel);
            troopServiceImp.saveTroop(troop);
            resourceService.minusGoldAmount(upgradePrice, kingdomId);
        }
    }
}
