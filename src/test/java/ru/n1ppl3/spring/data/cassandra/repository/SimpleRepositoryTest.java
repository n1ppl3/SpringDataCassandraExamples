package ru.n1ppl3.spring.data.cassandra.repository;

import org.junit.jupiter.api.Test;
import ru.n1ppl3.spring.data.cassandra.AbstractEmbeddedCassandraTestWithDefaultSession;

class SimpleRepositoryTest extends AbstractEmbeddedCassandraTestWithDefaultSession {

    private static final SimpleRepository simpleRepository = new SimpleRepository(cqlSession);

    @Test
    void test1_printMetadata() {
        simpleRepository.printMetadata();
    }

    @Test
    void test2_printSystemLocalTable() {
        simpleRepository.printSystemLocalTable();
    }

    @Test
    void test3_printServerTime() {
        simpleRepository.printServerTime();
    }

    @Test
    void test4_simpleStatements() {
        simpleRepository.simpleStatements();
    }

    @Test
    void test5_preparedStatements() {
        simpleRepository.preparedStatements();
    }

}
