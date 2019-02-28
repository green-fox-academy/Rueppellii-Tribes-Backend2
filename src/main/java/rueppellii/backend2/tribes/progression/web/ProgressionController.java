package rueppellii.backend2.tribes.progression.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import rueppellii.backend2.tribes.kingdom.exception.KingdomNotFoundException;
import rueppellii.backend2.tribes.kingdom.persistence.model.Kingdom;
import rueppellii.backend2.tribes.kingdom.service.KingdomService;
import rueppellii.backend2.tribes.progression.util.ProgressionListDTO;

import java.security.Principal;

@RestController
@RequestMapping("/api/kingdom/progression")
public class ProgressionController {

    private KingdomService kingdomService;

    @Autowired
    public ProgressionController(KingdomService kingdomService) {
        this.kingdomService = kingdomService;
    }

    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    public ProgressionListDTO showProgressions(Principal principal) throws KingdomNotFoundException {
        Kingdom kingdom = kingdomService.findByPrincipal(principal);
        ProgressionListDTO progressionListDTO = new ProgressionListDTO();
        progressionListDTO.setProgressionModels(kingdom.getKingdomsProgresses());
        return progressionListDTO;
    }
}
