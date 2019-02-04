package rueppellii.backend2.tribes.troop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/kingdom")
public class TroopController {

    private TroopService troopService;

    @Autowired
    public TroopController(TroopService troopService) {
        this.troopService = troopService;
    }

    @PostMapping("/troop/create")
    @ResponseStatus(HttpStatus.OK)
    public void createNewTroop(@RequestBody TroopDTO troopDTO){
        troopService.saveTroop(troopDTO);
    }
}
