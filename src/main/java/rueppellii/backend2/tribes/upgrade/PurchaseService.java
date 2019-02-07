package rueppellii.backend2.tribes.upgrade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rueppellii.backend2.tribes.kingdom.Kingdom;
import rueppellii.backend2.tribes.kingdom.KingdomService;
import rueppellii.backend2.tribes.kingdom.exception.KingdomNotValidException;
import rueppellii.backend2.tribes.resource.Resource;
import rueppellii.backend2.tribes.resource.ResourceService;
import rueppellii.backend2.tribes.resource.ResourceType;
import rueppellii.backend2.tribes.troop.models.Troop;
import rueppellii.backend2.tribes.troop.models.TroopTypes;

import java.util.ArrayList;
import java.util.List;

@Service
public class PurchaseService {

    private KingdomService kingdomService;
    private ResourceService resourceService;

    @Autowired
    public PurchaseService(KingdomService kingdomService, ResourceService resourceService) {
        this.kingdomService = kingdomService;
        this.resourceService = resourceService;
    }

    public Integer getGold(Long kingdomId) throws Exception {
        return resourceService.returnResource(ResourceType.RESOURCE_GOLD, kingdomId).getAmount();
    }
//
//    public List<Resource> returnKingdomsResources(String userName) throws KingdomNotValidException {
//        Kingdom kingdom = findUsersKingdom(userName);
//        List<Resource> kingdomsResources = new ArrayList<>();
//        for (Resource gold : kingdom.getKingdomsResources()) {
//            if (gold.getType().getTypeName().equals("RESOURCE_GOLD")) {
//                kingdomsResources.add(gold);
//            }
//        }
//        for (Resource food : kingdom.getKingdomsResources()) {
//            if (food.getType().getTypeName().equals("RESOURCE_FOOD")) {
//                kingdomsResources.add(food);
//            }
//        }
//        return kingdomsResources;
//    }


    public boolean hasEnoughGold(Long kingdomId, Integer amount) throws Exception {
        return getGold(kingdomId) > amount;
    }
//
//    public void buyTroop(String userName, TroopTypes type) throws KingdomNotValidException {
//        Kingdom kingdom = findUsersKingdom(userName);
//        Integer troopPrice = 10;
//        if (hasEnoughGold(userName, troopPrice)) {
//            kingdom.setKingdomsResources(returnKingdomsResources(userName));
//            makeTroop(userName, type);
//            kingdomRepository.save(kingdom);
//        }
//    }
}
