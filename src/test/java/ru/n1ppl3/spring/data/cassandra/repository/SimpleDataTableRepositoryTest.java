package ru.n1ppl3.spring.data.cassandra.repository;

import org.junit.jupiter.api.Test;
import ru.n1ppl3.spring.data.cassandra.AbstractEmbeddedCassandraTestWithDefaultSession;

import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;

import static java.time.ZoneOffset.UTC;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static ru.n1ppl3.spring.data.cassandra.utils.CassandraUtils.toEpochMicroseconds;

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

    @Test
    void test_write_time() {
        Long key = 2L;
        String value = "Two";
        Instant before = ZonedDateTime.of(2021, 9, 9, 12, 13, 14, 0, UTC).toInstant();
        Instant after = ZonedDateTime.of(2021, 9, 9, 12, 13, 15, 0, UTC).toInstant();
        simpleDataTableRepository.saveValueToKeyWithWriteTime(key, value, after);
        simpleDataTableRepository.saveValueToKeyWithWriteTime(key, "Three", before);
        // более старое значение не перетрёт более свежее
        assertEquals(toEpochMicroseconds(after), simpleDataTableRepository.findValueWriteTime(key));
        assertEquals(value, simpleDataTableRepository.findValueByKey(key));
    }
}
