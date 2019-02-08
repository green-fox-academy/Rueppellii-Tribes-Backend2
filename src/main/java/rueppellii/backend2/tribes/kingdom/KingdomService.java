package rueppellii.backend2.tribes.kingdom;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rueppellii.backend2.tribes.kingdom.exception.KingdomNotValidException;
import rueppellii.backend2.tribes.security.auth.jwt.JwtAuthenticationToken;
import rueppellii.backend2.tribes.security.model.UserContext;

import java.security.Principal;


@Service
public class KingdomService {

    private KingdomRepository kingdomRepository;

    @Autowired
    public KingdomService(KingdomRepository kingdomRepository) {
        this.kingdomRepository = kingdomRepository;
    }

    public Kingdom getKingdomByUsername(String loggedInUser) throws KingdomNotValidException {
        return kingdomRepository.findByApplicationUser_Username(loggedInUser).orElseThrow(() -> new KingdomNotValidException("You don't have a kingdom!"));
    }

    public Kingdom getKingdomById(Long id) throws KingdomNotValidException {
        return kingdomRepository.findById(id).orElseThrow(() -> new KingdomNotValidException("You don't have a kingdom!"));
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
}