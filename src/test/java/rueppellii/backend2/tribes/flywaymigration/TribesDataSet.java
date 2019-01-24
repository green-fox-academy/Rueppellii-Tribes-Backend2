package rueppellii.backend2.tribes.flywaymigration;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.autoconfigure.flyway.FlywayDataSource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

@RunWith(SpringRunner.class)
@ContextConfiguration
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class})
public class TribesDataSet {

    @Test
    @FlywayDataSource
    public void flywayTest() {

    }

}
