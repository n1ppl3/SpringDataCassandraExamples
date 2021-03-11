package ru.n1ppl3.spring.data.cassandra.entity;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Data
public class WeatherDataByDate {

    private LocalDate date;
    private UUID stationId;
    private LocalTime time;
    private Double temperature;
    private Double windSpeed;
}
