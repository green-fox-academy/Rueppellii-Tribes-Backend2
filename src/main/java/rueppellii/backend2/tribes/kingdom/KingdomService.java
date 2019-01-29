package rueppellii.backend2.tribes.kingdom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import rueppellii.backend2.tribes.exception.KingdomNotValidException;
import rueppellii.backend2.tribes.security.services.UserPrinciple;

import javax.validation.Valid;

@Service
public class KingdomService {

    private KingdomRepository kingdomRepository;

    @Autowired
    public KingdomService(KingdomRepository kingdomRepository) {
        this.kingdomRepository = kingdomRepository;
    }

    public Kingdom getKingdomByUsername() throws KingdomNotValidException {
        UserPrinciple userPrinciple = (UserPrinciple) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (kingdomRepository.findByApplicationUser_Username(userPrinciple.getUsername()).isPresent()) {
            return kingdomRepository.findByApplicationUser_Username(userPrinciple.getUsername()).get();
        }
        throw new KingdomNotValidException("You don't have a kingdom!");
    }
}
