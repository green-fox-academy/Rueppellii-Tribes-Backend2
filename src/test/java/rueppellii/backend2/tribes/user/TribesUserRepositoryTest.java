package rueppellii.backend2.tribes.user;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
class TribesUserRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private TribesUserRepository tribesUserRepository;

    @Test
    public void whenFindByName_thenReturnUser() {
        //given
        TribesUser testUser = new TribesUser();
        testUser.setUsername("testUser");
        testUser.setPassword("password");
        entityManager.persist(testUser);
        entityManager.flush();

        //when
        TribesUser found = tribesUserRepository.findByUsername(testUser.getUsername());

        //then
        assertThat(found.getUsername())
                .isEqualTo(testUser.getUsername());
    }
}