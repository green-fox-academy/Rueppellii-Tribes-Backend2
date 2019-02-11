package rueppellii.backend2.tribes.kingdom.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rueppellii.backend2.tribes.kingdom.exception.KingdomNotValidException;
import rueppellii.backend2.tribes.kingdom.persistence.model.Kingdom;
import rueppellii.backend2.tribes.kingdom.persistence.repository.KingdomRepository;
import rueppellii.backend2.tribes.kingdom.utility.KingdomDTO;
import rueppellii.backend2.tribes.security.auth.jwt.JwtAuthenticationToken;
import rueppellii.backend2.tribes.security.model.UserContext;
import rueppellii.backend2.tribes.user.persistence.model.ApplicationUserDTO;

import java.security.Principal;


@Service
public class KingdomService {

    private KingdomRepository kingdomRepository;

    @Autowired
    public KingdomService(KingdomRepository kingdomRepository) {
        this.kingdomRepository = kingdomRepository;
    }

    public Kingdom getKingdomByUsername(String loggedInUser) throws KingdomNotValidException {
        return kingdomRepository.findByApplicationUser_Username(loggedInUser).orElseThrow(() -> new KingdomNotValidException("You don't have a troopsKingdom!"));
    }

    public Kingdom getKingdomById(Long id) throws KingdomNotValidException {
        return kingdomRepository.findById(id).orElseThrow(() -> new KingdomNotValidException("You don't have a troopsKingdom!"));
    }

    public void saveKingdom(Kingdom kingdom) {
        kingdomRepository.save(kingdom);
    }

    public KingdomDTO mapKingdomDTO(Kingdom kingdom) {
        ModelMapper mapper = new ModelMapper();
        return mapper.map(kingdom, KingdomDTO.class);
    }

    public Kingdom findKingdomByPrincipal(Principal principal) throws KingdomNotValidException {
        JwtAuthenticationToken authenticationToken = (JwtAuthenticationToken) principal;
        UserContext userContext = (UserContext) authenticationToken.getPrincipal();
        String loggedInUser = userContext.getUsername();
        return getKingdomByUsername(loggedInUser);
    }

    public Kingdom createNewKingdomAndSetNameIfNotExists(ApplicationUserDTO applicationUserDTO) {
        Kingdom kingdom = new Kingdom();
        if (applicationUserDTO.getKingdomName().isEmpty()) {
            kingdom.setName(applicationUserDTO.getUsername() + "'s Kingdom");
        } else {
            kingdom.setName(applicationUserDTO.getKingdomName());
        }
        return kingdom;
    }
}
