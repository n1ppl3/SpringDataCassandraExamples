package ru.n1ppl3.spring.data.cassandra.repository;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.BoundStatement;
import com.datastax.oss.driver.api.core.cql.PreparedStatement;
import com.datastax.oss.driver.api.core.cql.ResultSet;
import com.datastax.oss.driver.api.core.cql.Row;
import org.springframework.lang.NonNull;
import ru.n1ppl3.spring.data.cassandra.entity.WeatherDataByDate;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

import static ru.n1ppl3.spring.data.cassandra.repository.SimpleRepository.buildDefaultCqlSession;


public class WeatherDataByDateRepository {


    private final CqlSession cqlSession;

    public WeatherDataByDateRepository() {
        this(buildDefaultCqlSession());
    }

    public WeatherDataByDateRepository(@NonNull CqlSession cqlSession) {
        this.cqlSession = cqlSession;
    }


    /**
     *
     */
    public void save(WeatherDataByDate entity) {
        PreparedStatement preparedStatement = cqlSession.prepare(
            "update my_keyspace.weather_data_by_date set " +
            "temperature = :temperature, wind_speed = :windSpeed " +
            "where date = :date and station_id = :stationId and time = :time"
        );
        BoundStatement boundStatement = preparedStatement.boundStatementBuilder()
            .setDouble("temperature", entity.getTemperature())
            .setDouble("windSpeed", entity.getWindSpeed())
            .setLocalDate("date", entity.getDate())
            .setUuid("stationId", entity.getStationId())
            .setLocalTime("time", entity.getTime())
            .build();
        cqlSession.execute(boundStatement);
    }

    /**
     *
     */
    public WeatherDataByDate getById(UUID id, LocalDate date, LocalTime time) {
        String query = "select * from my_keyspace.weather_data_by_date where date = :date and station_id = :stationId and time = :time";
        PreparedStatement preparedStatement = cqlSession.prepare(query);
        BoundStatement boundStatement = preparedStatement.boundStatementBuilder()
            .setLocalDate("date", date)
            .setUuid("stationId", id)
            .setLocalTime("time", time)
            .build();
        ResultSet rs = cqlSession.execute(boundStatement);
        Row row = rs.one();
        return row != null ? fromRow(row) : null;
    }

    private static WeatherDataByDate fromRow(Row row) {
        WeatherDataByDate entity = new WeatherDataByDate();
        entity.setDate(row.getLocalDate("date"));
        entity.setStationId(row.getUuid("station_id"));
        entity.setTime(row.getLocalTime("time"));
        entity.setTemperature(row.getDouble("temperature"));
        entity.setWindSpeed(row.getDouble("wind_speed"));
        return entity;
    }
}
