package rueppellii.backend2.tribes.resource.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import rueppellii.backend2.tribes.kingdom.exception.KingdomNotFoundException;
import rueppellii.backend2.tribes.kingdom.persistence.model.Kingdom;
import rueppellii.backend2.tribes.kingdom.service.KingdomService;
import rueppellii.backend2.tribes.resource.exception.NoResourceException;
import rueppellii.backend2.tribes.resource.presistence.model.Resource;
import rueppellii.backend2.tribes.resource.service.ResourceService;
import rueppellii.backend2.tribes.resource.utility.ResourceDTO;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/kingdom/resources")
public class ResourceController {

    private KingdomService kingdomService;

    @Autowired
    public ResourceController(KingdomService kingdomService) {
        this.kingdomService = kingdomService;
    }

    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    public ResourceDTO listKingdomsResources(Principal principal) throws KingdomNotFoundException, NoResourceException {
        Kingdom kingdom = kingdomService.findByPrincipal(principal);
        ResourceDTO resourceDTO = new ResourceDTO();
        resourceDTO.setResources(kingdom.getKingdomsResources());
        return resourceDTO;
    }
}
