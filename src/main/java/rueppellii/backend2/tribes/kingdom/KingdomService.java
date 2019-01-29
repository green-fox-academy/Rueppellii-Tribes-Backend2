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
        UserPrinciple userPrinciple = (UserPrinciple) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (kingdomRepository.findByApplicationUser_Username(userPrinciple.getUsername()).isPresent()) {
            ModelMapper modelMapper = new ModelMapper();
            return modelMapper.map(kingdomRepository.findByApplicationUser_Username(userPrinciple.getUsername()).get(), KingdomDTO.class);
        }
        throw new KingdomNotValidException("You don't have a kingdom!");
    }
}
