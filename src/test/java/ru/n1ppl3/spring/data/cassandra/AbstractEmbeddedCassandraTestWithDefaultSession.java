package ru.n1ppl3.spring.data.cassandra;

import com.datastax.oss.driver.api.core.CqlSession;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import ru.n1ppl3.spring.data.cassandra.utils.SessionUtils;

@Slf4j
public abstract class AbstractEmbeddedCassandraTestWithDefaultSession extends AbstractEmbeddedCassandraTest {

    protected static CqlSession cqlSession = null;

    @BeforeAll
    static void beforeAll() {
        log.info("Gonna build default CqlSession using settings from application.conf");
        cqlSession = SessionUtils.buildDefaultCqlSession();
    }

    @AfterAll
    static void afterAll() {
        log.info("Gonna close CqlSession");
        if (cqlSession != null) {
            cqlSession.close();
        }
    }
}
