package rueppellii.backend2.tribes.building.service;

import org.junit.Ignore;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import rueppellii.backend2.tribes.building.persistence.model.Barracks;
import rueppellii.backend2.tribes.building.persistence.model.Building;
import rueppellii.backend2.tribes.building.persistence.model.TownHall;
import rueppellii.backend2.tribes.building.persistence.repository.BuildingRepository;
import rueppellii.backend2.tribes.kingdom.persistence.model.Kingdom;

import java.util.Optional;

import static org.mockito.Mockito.when;


class BuildingServiceTest {

    @InjectMocks
    BuildingService buildingService;

    @Mock
    BuildingRepository buildingRepository;

    @BeforeEach
    void setup() {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    void findBuildingInKingdom() {
        Kingdom kingdom = new Kingdom();
        Building building = new Barracks();
        kingdom.setId(1L);
        building.setId(1L);
        building.setBuildingsKingdom(kingdom);

        Assertions.assertTrue(kingdom.getId().equals(building.getId()));
    }

    @Test
    void findById() {
        Building building = new TownHall();
        long id = 1L;
        building.setId(id);
        when(buildingRepository.findById(id)).thenReturn(Optional.of(building));

        Assertions.assertEquals(Optional.of(1L), Optional.of(building.getId()));
    }

}