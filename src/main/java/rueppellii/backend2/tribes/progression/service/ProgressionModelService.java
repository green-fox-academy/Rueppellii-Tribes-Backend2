package rueppellii.backend2.tribes.progression.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rueppellii.backend2.tribes.progression.persistence.ProgressionModelRepository;

@Service
public class ProgressionModelService {

    private ProgressionModelRepository upgradeProgressionModelRepository;

    @Autowired
    public ProgressionModelService(ProgressionModelRepository upgradeProgressionModelRepository) {
        this.upgradeProgressionModelRepository = upgradeProgressionModelRepository;
    }

    public Boolean actionHandler(Integer actionCode) {
        if(actionCode > 0) {
            return false;
        }
        return true;
    }
}
