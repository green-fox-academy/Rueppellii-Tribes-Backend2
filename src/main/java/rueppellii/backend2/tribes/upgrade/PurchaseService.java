package rueppellii.backend2.tribes.upgrade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rueppellii.backend2.tribes.kingdom.KingdomService;
import rueppellii.backend2.tribes.resource.ResourceService;
import rueppellii.backend2.tribes.resource.ResourceType;

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
        return resourceService.returnResource(ResourceType.GOLD, kingdomId).getAmount();
    }
//
//    public List<Resource> returnKingdomsResources(String userName) throws KingdomNotValidException {
//        Kingdom kingdom = findUsersKingdom(userName);
//        List<Resource> kingdomsResources = new ArrayList<>();
//        for (Resource gold : kingdom.getKingdomsResources()) {
//            if (gold.getType().getTypeName().equals("GOLD")) {
//                kingdomsResources.add(gold);
//            }
//        }
//        for (Resource food : kingdom.getKingdomsResources()) {
//            if (food.getType().getTypeName().equals("FOOD")) {
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
