package ru.n1ppl3.spring.data.cassandra;

import lombok.extern.slf4j.Slf4j;
import org.cassandraunit.utils.EmbeddedCassandraServerHelper;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.core.io.ClassPathResource;
import ru.n1ppl3.spring.data.cassandra.utils.CassandraUtils;

import java.io.IOException;
import java.net.URI;

@Slf4j
public abstract class AbstractEmbeddedCassandraTest {

    @BeforeAll
    static void beforeAll0() throws IOException, InterruptedException {
        log.info("Gonna start EmbeddedCassandra!");
        EmbeddedCassandraServerHelper.startEmbeddedCassandra();
        URI scriptsDir = new ClassPathResource("/scripts").getURI();
        CassandraUtils.executeScriptsInFolder(EmbeddedCassandraServerHelper.getSession(), scriptsDir);
    }

    @AfterAll
    static void afterAll0() {
        //EmbeddedCassandraServerHelper.cleanEmbeddedCassandra();
    }
}
