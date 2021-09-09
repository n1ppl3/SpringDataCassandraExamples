package ru.n1ppl3.spring.data.cassandra.repository;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.ResultSet;
import com.datastax.oss.driver.api.core.cql.Row;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.n1ppl3.spring.data.cassandra.utils.CassandraUtils;

import java.time.Instant;
import java.util.Collections;
import java.util.Map;

import static ru.n1ppl3.spring.data.cassandra.utils.CassandraUtils.toEpochMicroseconds;

@Slf4j
@AllArgsConstructor
public class SimpleDataTableRepository {

    private final CqlSession cqlSession;

    /**
     *
     */
    public String findValueByKey(Long key) {
        ResultSet rs = cqlSession.execute("select * from my_keyspace.simple_data_table where key = :key", Collections.singletonMap("key", key));
        Row row = rs.one();
        return (row != null) ? row.getString("value") : null;
    }

    /**
     *
     */
    public Long findValueWriteTime(Long key) {
        ResultSet rs = cqlSession.execute("select writetime(value) as write_time from my_keyspace.simple_data_table where key = :key",
            Collections.singletonMap("key", key));
        Row row = rs.one();
        return (row != null) ? row.getLong("write_time") : null;
    }

    /**
     *
     */
    public void saveValueToKey(Long key, String value) {
        ResultSet rs = cqlSession.execute("update my_keyspace.simple_data_table set value = :value where key = :key", value, key);
        CassandraUtils.printResultSet(rs);
        System.out.println("Updated simple_data_table for $key");
    }

    /**
     *
     */
    public void saveValueToKeyWithWriteTime(Long key, String value, Instant writeTime) {
        String cql = "update my_keyspace.simple_data_table using timestamp :writeTime set value = :value where key = :key";
        ResultSet rs = cqlSession.execute(cql, toEpochMicroseconds(writeTime), value, key);
        CassandraUtils.printResultSet(rs);
        System.out.println("Updated simple_data_table for $key with writeTime");
    }

}
