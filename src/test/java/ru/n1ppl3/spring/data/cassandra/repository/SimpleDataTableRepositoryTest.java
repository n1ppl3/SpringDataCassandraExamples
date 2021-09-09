package ru.n1ppl3.spring.data.cassandra.repository;

import org.junit.jupiter.api.Test;
import ru.n1ppl3.spring.data.cassandra.AbstractEmbeddedCassandraTestWithDefaultSession;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class SimpleDataTableRepositoryTest extends AbstractEmbeddedCassandraTestWithDefaultSession {

    private final SimpleDataTableRepository simpleDataTableRepository = new SimpleDataTableRepository(cqlSession);

    @Test
    void test_found() {
        Long key = 1L;
        String value = "One";
        simpleDataTableRepository.saveValueToKey(key, value);
        assertEquals(value, simpleDataTableRepository.findValueByKey(key));
    }

    @Test
    void test_not_found() {
        assertNull(simpleDataTableRepository.findValueByKey(-1L));
    }
}
