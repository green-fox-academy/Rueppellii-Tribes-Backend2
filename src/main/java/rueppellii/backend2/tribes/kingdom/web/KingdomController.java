package rueppellii.backend2.tribes.kingdom.web;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import rueppellii.backend2.tribes.kingdom.persistence.model.Kingdom;
import rueppellii.backend2.tribes.kingdom.utility.KingdomDTO;
import rueppellii.backend2.tribes.kingdom.service.KingdomService;
import rueppellii.backend2.tribes.kingdom.exception.KingdomNotFoundException;
import rueppellii.backend2.tribes.building.exception.BuildingNotFoundException;
import rueppellii.backend2.tribes.kingdom.utility.KingdomNameDTO;
import rueppellii.backend2.tribes.progression.service.ProgressionService;
import rueppellii.backend2.tribes.resource.service.ResourceService;
import rueppellii.backend2.tribes.troop.exception.TroopNotFoundException;

import java.security.Principal;

@RestController
@RequestMapping("/api/kingdom")
public class KingdomController {

    private KingdomService kingdomService;
    private ProgressionService progressionService;
    private ResourceService resourceService;
//    private LocationService locationService;


    @Autowired
    public KingdomController(KingdomService kingdomService, ProgressionService progressionService, ResourceService resourceService) {
        this.kingdomService = kingdomService;
        this.progressionService = progressionService;
        this.resourceService = resourceService;
//        this.locationService = locationService;
    }

    @GetMapping("")
    public KingdomDTO showKingdom(Principal principal) throws KingdomNotFoundException, TroopNotFoundException, BuildingNotFoundException {
        Kingdom kingdom = kingdomService.findByPrincipal(principal);
        progressionService.updateProgression(kingdom);
        resourceService.updateResources(kingdom.getKingdomsResources());
        return kingdomService.mapKingdomDTO(kingdom);
    }

    @PutMapping("")
    @ResponseStatus(HttpStatus.OK)
    public void resetKingdomsName(@RequestBody KingdomNameDTO kingdomNameDTO, Principal principal) throws KingdomNotFoundException {
        Kingdom kingdom = kingdomService.findByPrincipal(principal);
        kingdom.setName(kingdomNameDTO.getName());
        kingdomService.save(kingdom);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public KingdomDTO showOtherKingdom(@PathVariable Long id) throws KingdomNotFoundException {
        return kingdomService.findOtherKingdom(id);
    }

//    @PostMapping(path = "/map", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
//    @ResponseStatus(HttpStatus.OK)
//    public Kingdom addLocationToKingdom(@RequestBody Location location, Principal principal) throws KingdomNotFoundException, TroopNotFoundException, BuildingNotFoundException, InvalidProgressionRequestException {
//        Kingdom kingdom = kingdomService.findByPrincipal(principal);
//        locationService.save(location);
//        return kingdom;
//
//    }

}
