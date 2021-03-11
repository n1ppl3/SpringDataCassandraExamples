package ru.n1ppl3.spring.data.cassandra.repository;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import ru.n1ppl3.spring.data.cassandra.AbstractEmbeddedCassandraTest;

class SimpleRepositoryTest extends AbstractEmbeddedCassandraTest {

    private static final SimpleRepository simpleRepository = new SimpleRepository();

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

    @AfterAll
    static void afterAll() {
        simpleRepository.close();
    }
}
