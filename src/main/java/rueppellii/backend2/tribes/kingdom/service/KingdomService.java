package rueppellii.backend2.tribes.kingdom.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rueppellii.backend2.tribes.building.utility.BuildingLeaderBoardDTO;
import rueppellii.backend2.tribes.kingdom.exception.KingdomNotFoundException;
import rueppellii.backend2.tribes.kingdom.persistence.model.Kingdom;
import rueppellii.backend2.tribes.kingdom.persistence.repository.KingdomRepository;
import rueppellii.backend2.tribes.kingdom.utility.KingdomDTO;
import rueppellii.backend2.tribes.security.auth.jwt.JwtAuthenticationToken;
import rueppellii.backend2.tribes.security.model.UserContext;
import rueppellii.backend2.tribes.user.util.ApplicationUserDTO;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Service
public class KingdomService {

    private KingdomRepository kingdomRepository;

    @Autowired
    public KingdomService(KingdomRepository kingdomRepository) {
        this.kingdomRepository = kingdomRepository;
    }

    public Kingdom findByUsername(String loggedInUser) throws KingdomNotFoundException {
        return kingdomRepository.findByApplicationUser_Username(loggedInUser).orElseThrow(() -> new KingdomNotFoundException("Kingdom not found by user: " + loggedInUser));
    }

    public Kingdom findById(Long id) throws KingdomNotFoundException {
        return kingdomRepository.findById(id).orElseThrow(() -> new KingdomNotFoundException("Kingdom not found by id: " + id));
    }

    public void save(Kingdom kingdom) {
        kingdomRepository.save(kingdom);
    }

    public KingdomDTO mapKingdomDTO(Kingdom kingdom) {
        ModelMapper mapper = new ModelMapper();
        return mapper.map(kingdom, KingdomDTO.class);
    }

    public Kingdom findByPrincipal(Principal principal) throws KingdomNotFoundException {
        JwtAuthenticationToken authenticationToken = (JwtAuthenticationToken) principal;
        UserContext userContext = (UserContext) authenticationToken.getPrincipal();
        String loggedInUser = userContext.getUsername();
        return findByUsername(loggedInUser);
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

    public KingdomDTO findOtherKingdom(Long id) throws KingdomNotFoundException {
        Kingdom kingdom = findById(id);
        return mapKingdomDTO(kingdom);
    }

    public List<Kingdom> findall(){
        return kingdomRepository.findAll();
    }

    public List<BuildingLeaderBoardDTO> findAllKingdomNames() throws KingdomNotFoundException {
        List<BuildingLeaderBoardDTO> kingdomsBuildings = new ArrayList<>();
        List<Kingdom> kingdomList = findall();

        for (int i = 0; i < kingdomList.size(); i++) {
            if (kingdomList.get(i).getName() != null) {
                kingdomsBuildings.add(getOneKingdom(kingdomList.get(i).getId()));
            }
        }
        return kingdomsBuildings;
    }

    public BuildingLeaderBoardDTO getOneKingdom(Long id) throws KingdomNotFoundException {
        BuildingLeaderBoardDTO leaderBoardDTO = createLeaderBoardDTO(findById(id));
        return leaderBoardDTO;
    }

    private BuildingLeaderBoardDTO createLeaderBoardDTO(Kingdom kingdom) {
        BuildingLeaderBoardDTO leaderBoardDTO = new BuildingLeaderBoardDTO();
        leaderBoardDTO.setKingdomName(kingdom.getName());
        leaderBoardDTO.setNumberOfBuildings(kingdom.getKingdomsBuildings().size());
        return leaderBoardDTO;
    }

}
