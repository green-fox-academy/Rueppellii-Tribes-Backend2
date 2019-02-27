package rueppellii.backend2.tribes.kingdom.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rueppellii.backend2.tribes.kingdom.exception.KingdomNotFoundException;
import rueppellii.backend2.tribes.kingdom.persistence.model.Kingdom;
import rueppellii.backend2.tribes.kingdom.persistence.repository.KingdomRepository;
import rueppellii.backend2.tribes.kingdom.utility.KingdomDTO;
import rueppellii.backend2.tribes.kingdom.utility.KingdomWithLocationDTO;
import rueppellii.backend2.tribes.kingdom.utility.KingdomListWithLocationDTO;
import rueppellii.backend2.tribes.location.exception.CountryCodeNotValidException;
import rueppellii.backend2.tribes.location.exception.LocationIsTakenException;
import rueppellii.backend2.tribes.location.persistence.model.Location;
import rueppellii.backend2.tribes.location.service.LocationService;
import rueppellii.backend2.tribes.security.auth.jwt.JwtAuthenticationToken;
import rueppellii.backend2.tribes.security.model.UserContext;
import rueppellii.backend2.tribes.user.util.ApplicationUserDTO;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Service
public class KingdomService {

    private KingdomRepository kingdomRepository;
    private LocationService locationService;

    @Autowired
    public KingdomService(KingdomRepository kingdomRepository, LocationService locationService) {
        this.kingdomRepository = kingdomRepository;
        this.locationService = locationService;
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

    public Kingdom createNewKingdomAndSetNameIfNotExists(ApplicationUserDTO applicationUserDTO) throws IOException, CountryCodeNotValidException, LocationIsTakenException {
        System.out.println(listCountryCodes());
        Kingdom kingdom = new Kingdom();
        Location location = new Location();
        List<Location> kingdomsLocations = new ArrayList<>();
        location.setCountryCode(applicationUserDTO.getLocation());
        kingdomsLocations.add(location);
        if (listCountryCodes().contains(location.getCountryCode()) && (locationService.findLocation(location.getCountryCode()) == null)) {
                kingdom.setKingdomsLocations(kingdomsLocations);
                location.setLocationsKingdom(kingdom);
            } else throw new CountryCodeNotValidException("Not a valid country code");
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

    public List<String> listCountryCodes() throws IOException {
        Path path = Paths.get("src/main/resources/CountryCodes.txt");
        try {
            List<String> lines = Files.readAllLines(path);
            List<String[]> splitLines = new ArrayList<>();
            List<String> countryCodes = new ArrayList<>();
            for (String line : lines) {
                splitLines.add(line.split("- "));
            }
            for (String[] splitLine : splitLines) {
                countryCodes.add(splitLine[1]);
            }
            return countryCodes;
        } catch (IOException e) {
            throw new IOException("Could not read file");
        }
    }

    public KingdomWithLocationDTO mapKingdomWithLocationDTO(Kingdom kingdom) {
        KingdomWithLocationDTO kingdomWithLocationDTO = new KingdomWithLocationDTO();
        List<String> locations = new ArrayList<>();
        for (Location location : kingdom.getKingdomsLocations()) {
            locations.add(location.getCountryCode());
        }
        kingdomWithLocationDTO.setId(kingdom.getId());
        kingdomWithLocationDTO.setName(kingdom.getName());
        kingdomWithLocationDTO.setPopulation(kingdom.getKingdomsTroops().size());
        kingdomWithLocationDTO.setLocation(locations);
        return kingdomWithLocationDTO;
    }

    public KingdomListWithLocationDTO listKingdomsWithLocation() {
        KingdomListWithLocationDTO dto = new KingdomListWithLocationDTO();
        List<KingdomWithLocationDTO> kingdomDTOList = new ArrayList<>();
        List<Kingdom> kingdomList = kingdomRepository.findAll();
        for (Kingdom kingdom : kingdomList) {
            kingdomDTOList.add(mapKingdomWithLocationDTO(kingdom));
        }
        dto.setKingdoms(kingdomDTOList);
        return dto;
    }
}
