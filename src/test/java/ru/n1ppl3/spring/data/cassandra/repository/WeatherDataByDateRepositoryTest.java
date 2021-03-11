package ru.n1ppl3.spring.data.cassandra.repository;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import ru.n1ppl3.spring.data.cassandra.AbstractEmbeddedCassandraTest;
import ru.n1ppl3.spring.data.cassandra.entity.WeatherDataByDate;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
class WeatherDataByDateRepositoryTest extends AbstractEmbeddedCassandraTest {

    private final WeatherDataByDateRepository repository = new WeatherDataByDateRepository();

    @Test
    void test() {
        WeatherDataByDate entity = new WeatherDataByDate();
        entity.setDate(LocalDate.now());
        entity.setStationId(UUID.randomUUID());
        entity.setTime(LocalTime.now());
        entity.setTemperature(18.4);
        entity.setWindSpeed(9.8);

        repository.save(entity);

        WeatherDataByDate found = repository.getById(entity.getStationId(), entity.getDate(), entity.getTime());
        log.info("found {}", found);
        assertEquals(entity, found);
    }
}
