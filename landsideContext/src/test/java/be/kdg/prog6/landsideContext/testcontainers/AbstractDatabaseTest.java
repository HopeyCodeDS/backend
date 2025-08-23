package be.kdg.prog6.landsideContext.testcontainers;

import be.kdg.prog6.landsideContext.LandsideContextApplication;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.TestPropertySourceUtils;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(
    classes = LandsideContextApplication.class,
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@ExtendWith(SpringExtension.class)
@Testcontainers
@ContextConfiguration(initializers = AbstractDatabaseTest.DataSourceInitializer.class)
@ActiveProfiles("test")
public abstract class AbstractDatabaseTest {
    
    private static final MySQLContainer<?> DATABASE;

    static {
        DATABASE = new MySQLContainer<>("mysql:9.0.1")
            .withDatabaseName("test_db")
            .withUsername("root")
            .withPassword("")
            .withInitScript("init-test-db.sql");
        DATABASE.start();
    }

    @Test
    void containerIsRunning() {
        assertTrue(DATABASE.isCreated());
        assertTrue(DATABASE.isRunning());
    }

    public static class DataSourceInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        @Override
        public void initialize(ConfigurableApplicationContext applicationContext) {
            TestPropertySourceUtils.addInlinedPropertiesToEnvironment(
                applicationContext,
                "spring.datasource.url=" + DATABASE.getJdbcUrl(),
                "spring.datasource.username=test_user",
                "spring.datasource.password=test_password",
                "spring.datasource.driver-class-name=" + DATABASE.getDriverClassName()
            );
        }
    }
}
