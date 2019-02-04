package rueppellii.backend2.tribes.kingdom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
<<<<<<< HEAD
import rueppellii.backend2.tribes.exception.KingdomNotValidException;
=======
import rueppellii.backend2.tribes.kingdom.exception.KingdomNotValidException;

>>>>>>> 4fba8a431eccdaf170bf5c224994b9ad42154acf

@Service
public class KingdomService {

    private KingdomRepository kingdomRepository;

    @Autowired
    public KingdomService(KingdomRepository kingdomRepository) {
        this.kingdomRepository = kingdomRepository;
    }

<<<<<<< HEAD
//    public KingdomDTO getKingdomByUsername() throws KingdomNotValidException {
//        ModelMapper modelMapper = new ModelMapper();
//        UserPrinciple loggedInUser = (UserPrinciple) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        Kingdom userKingdom = kingdomRepository.findByApplicationUser_Username(loggedInUser.getUsername()).orElseThrow(() -> new KingdomNotValidException("You don't have a kingdom!"));
//        return modelMapper.map(userKingdom, KingdomDTO.class);
//    }
=======
    public KingdomDTO getKingdomByUsername() throws KingdomNotValidException {
        ModelMapper mapper = new ModelMapper();
        String loggedInUser = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Kingdom userKingdom = kingdomRepository.findByApplicationUser_Username(loggedInUser).orElseThrow(() -> new KingdomNotValidException("You don't have a kingdom!"));
        return mapper.map(userKingdom, KingdomDTO.class);
    }
>>>>>>> 4fba8a431eccdaf170bf5c224994b9ad42154acf
}
