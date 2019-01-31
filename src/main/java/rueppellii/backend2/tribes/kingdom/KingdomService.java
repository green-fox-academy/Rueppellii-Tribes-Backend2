package rueppellii.backend2.tribes.kingdom;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import rueppellii.backend2.tribes.exception.KingdomNotValidException;
import rueppellii.backend2.tribes.security.services.UserPrinciple;

@Service
public class KingdomService {

    private KingdomRepository kingdomRepository;

    @Autowired
    public KingdomService(KingdomRepository kingdomRepository) {
        this.kingdomRepository = kingdomRepository;
    }

    public KingdomDTO getKingdomByUsername() throws KingdomNotValidException {
        ModelMapper modelMapper = new ModelMapper();
        UserPrinciple loggedInUser = (UserPrinciple) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Kingdom userKingdom = kingdomRepository.findByApplicationUser_Username(loggedInUser.getUsername()).orElseThrow(() -> new KingdomNotValidException("You don't have a kingdom!"));
        return modelMapper.map(userKingdom, KingdomDTO.class);
    }
}
