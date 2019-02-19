package rueppellii.backend2.tribes.common;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UpgradeableRespository extends JpaRepository<Upgradeable, Long> {
}
