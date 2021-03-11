
-- date & station_id is partition key, time - clustering key
CREATE TABLE IF NOT EXISTS my_keyspace.weather_data_by_date (
    date date,
    station_id UUID,
    time time,
    temperature double,
    wind_speed double,
    PRIMARY KEY ((date, station_id), time)
)
WITH CLUSTERING ORDER BY (time DESC)
AND default_time_to_live = 2592000 -- 30 days
AND comment = 'weather station data'
;
