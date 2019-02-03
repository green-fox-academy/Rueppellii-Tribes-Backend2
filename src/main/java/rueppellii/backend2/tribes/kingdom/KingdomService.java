package rueppellii.backend2.tribes.kingdom;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import rueppellii.backend2.tribes.kingdom.exception.KingdomNotValidException;


@Service
public class KingdomService {

    private KingdomRepository kingdomRepository;

    @Autowired
    public KingdomService(KingdomRepository kingdomRepository) {
        this.kingdomRepository = kingdomRepository;
    }

    public KingdomDTO getKingdomByUsername() throws KingdomNotValidException {
        ModelMapper mapper = new ModelMapper();
        String loggedInUser = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Kingdom userKingdom = kingdomRepository.findByApplicationUser_Username(loggedInUser).orElseThrow(() -> new KingdomNotValidException("You don't have a kingdom!"));
        return mapper.map(userKingdom, KingdomDTO.class);
    }
}
