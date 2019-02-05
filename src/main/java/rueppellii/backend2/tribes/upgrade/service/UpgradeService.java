package rueppellii.backend2.tribes.upgrade.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rueppellii.backend2.tribes.upgrade.persistence.UpgradeModelRepository;

@Service
public class UpgradeService {

    private UpgradeModelRepository upgradeModelRepository;

    @Autowired
    public UpgradeService(UpgradeModelRepository upgradeModelRepository) {
        this.upgradeModelRepository = upgradeModelRepository;
    }

}
