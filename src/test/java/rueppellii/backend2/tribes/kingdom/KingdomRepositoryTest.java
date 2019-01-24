package rueppellii.backend2.tribes.kingdom;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("Test")
@DataJpaTest
class KingdomRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private KingdomRepository kingdomRepository;

    @Test
    void saveKingdomWorks() {
        Kingdom kingdom = new Kingdom();
        kingdom.setName("Starbucks");
        entityManager.persist(kingdom);
        entityManager.flush();
        Kingdom found = kingdomRepository.findByName(kingdom.getName());
        assertThat(found.getName())
                .isEqualTo(kingdom.getName());
    }
}