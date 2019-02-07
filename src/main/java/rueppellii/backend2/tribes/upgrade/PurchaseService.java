package rueppellii.backend2.tribes.upgrade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rueppellii.backend2.tribes.kingdom.Kingdom;
import rueppellii.backend2.tribes.kingdom.KingdomRepository;
import rueppellii.backend2.tribes.kingdom.exception.KingdomNotValidException;
import rueppellii.backend2.tribes.resource.Resource;
import rueppellii.backend2.tribes.security.auth.jwt.JwtAuthenticationToken;
import rueppellii.backend2.tribes.security.model.UserContext;
import rueppellii.backend2.tribes.troop.models.Troop;
import rueppellii.backend2.tribes.troop.models.TroopTypes;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Service
public class PurchaseService {

    private KingdomRepository kingdomRepository;

    @Autowired
    public PurchaseService(KingdomRepository kingdomRepository) {
        this.kingdomRepository = kingdomRepository;
    }

//    public String findLoggedInUsername(Principal principal) {
//        JwtAuthenticationToken authenticationToken = (JwtAuthenticationToken) principal;
//        UserContext userContext = (UserContext) authenticationToken.getPrincipal();
//        return userContext.getUsername();
//    }

    public Kingdom findUsersKingdom(String userName) throws KingdomNotValidException {
        return kingdomRepository.findByApplicationUser_Username(userName).orElseThrow(() -> new KingdomNotValidException("You don't have a kingdom!"));
    }

    public Integer getKingdomsGoldAmount(String userName) throws KingdomNotValidException {
        Integer goldAmount = 0;
        Kingdom userKingdom = findUsersKingdom(userName);
        for (Resource gold : userKingdom.getKingdomsResources()) {
            if (gold.getResource_type().getTypeName().equals("RESOURCE_GOLD")) {
                goldAmount = gold.getAmount();
            }
        }
        return goldAmount;
    }

    public List<Resource> returnKingdomsResources(String userName) throws KingdomNotValidException {
        Kingdom kingdom = findUsersKingdom(userName);
        List<Resource> kingdomsResources = new ArrayList<>();
        for (Resource gold : kingdom.getKingdomsResources()) {
            if (gold.getResource_type().getTypeName().equals("RESOURCE_GOLD")) {
                kingdomsResources.add(gold);
            }
        }
        for (Resource food : kingdom.getKingdomsResources()) {
            if (food.getResource_type().getTypeName().equals("RESOURCE_FOOD")) {
                kingdomsResources.add(food);
            }
        }
        return kingdomsResources;
    }

    public void makeTroop(String userName, TroopTypes type) throws KingdomNotValidException {
        Kingdom kingdom = findUsersKingdom(userName);
        for (Troop troop : kingdom.getTroops()) {
            if (troop.getType().equals(type)) {
                troop.getType().createTroop();
            }
        }
    }

    public boolean hasEnoughGold(String userName, Integer amount) throws KingdomNotValidException {
                return getKingdomsGoldAmount(userName) > amount;
    }

    public void buyTroop(String userName, TroopTypes type) throws KingdomNotValidException {
        Kingdom kingdom = findUsersKingdom(userName);
        Integer troopPrice = 10;
        if (hasEnoughGold(userName, troopPrice)) {
            kingdom.setKingdomsResources(returnKingdomsResources(userName));
            makeTroop(userName, type);
            kingdomRepository.save(kingdom);
        }
    }
}
