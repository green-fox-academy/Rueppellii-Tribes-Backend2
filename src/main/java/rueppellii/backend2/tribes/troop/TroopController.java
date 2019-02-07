package rueppellii.backend2.tribes.troop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/kingdom")
public class TroopController {

    private TroopServiceImp troopServiceImp;

    @Autowired
    public TroopController(TroopServiceImp troopServiceImp) {
        this.troopServiceImp = troopServiceImp;
    }

    @PostMapping("/troop/create")
    @ResponseStatus(HttpStatus.OK)
    public void createNewTroop(@RequestBody Troop troop){
        troopServiceImp.saveTroop(troop);
    }

}
