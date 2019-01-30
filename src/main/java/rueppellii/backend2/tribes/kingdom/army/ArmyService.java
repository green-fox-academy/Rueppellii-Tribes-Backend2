package rueppellii.backend2.tribes.kingdom.army;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ArmyService {

    private ArmyRepository armyRepository;

    @Autowired
    public ArmyService(ArmyRepository armyRepository) {
        this.armyRepository = armyRepository;
    }
}
