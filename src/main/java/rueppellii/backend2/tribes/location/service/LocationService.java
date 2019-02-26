package rueppellii.backend2.tribes.location.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rueppellii.backend2.tribes.location.exception.LocationIsTakenException;
import rueppellii.backend2.tribes.location.persistence.model.Location;
import rueppellii.backend2.tribes.location.persistence.repository.LocationRepository;

@Service
public class LocationService {

    private LocationRepository locationRepository;

    @Autowired
    public LocationService(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    public Location findLocation(String countryCode) throws LocationIsTakenException {
        if (locationRepository.findById(countryCode).isPresent()) {
            throw new LocationIsTakenException("Country is already taken");
        }
        return null;
    }

    public void saveLocation(Location location) {
        locationRepository.save(location);
    }
}
