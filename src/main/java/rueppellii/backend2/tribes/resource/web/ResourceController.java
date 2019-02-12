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
import rueppellii.backend2.tribes.resource.presistence.model.Resource;
import rueppellii.backend2.tribes.resource.service.ResourceService;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/kingdom/resources")
public class ResourceController {

    private ResourceService resourceService;
    private KingdomService kingdomService;

    @Autowired
    public ResourceController(ResourceService resourceService, KingdomService kingdomService) {
        this.resourceService = resourceService;
        this.kingdomService = kingdomService;
    }

    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    public List<Resource> listKingdomsResources(Principal principal) throws KingdomNotFoundException {
        Kingdom kingdom = kingdomService.findByPrincipal(principal);
        return kingdom.getKingdomsResources();
    }
}
