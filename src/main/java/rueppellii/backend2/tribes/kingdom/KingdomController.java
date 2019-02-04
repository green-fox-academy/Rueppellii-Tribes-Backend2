package rueppellii.backend2.tribes.kingdom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import rueppellii.backend2.tribes.kingdom.exception.KingdomNotValidException;


@RestController
public class KingdomController {

    private KingdomService kingdomService;

    @Autowired
    public KingdomController(KingdomService kingdomService) {
        this.kingdomService = kingdomService;
    }

//    @GetMapping("/kingdom")
//    public Object showKingdom() throws KingdomNotValidException {
//        return kingdomService.getKingdomByUsername();
//    }
}
