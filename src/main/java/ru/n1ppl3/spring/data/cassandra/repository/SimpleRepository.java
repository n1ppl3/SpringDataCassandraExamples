package ru.n1ppl3.spring.data.cassandra.repository;

import com.datastax.oss.driver.api.core.ConsistencyLevel;
import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.BoundStatement;
import com.datastax.oss.driver.api.core.cql.PreparedStatement;
import com.datastax.oss.driver.api.core.cql.ResultSet;
import com.datastax.oss.driver.api.core.cql.Row;
import com.datastax.oss.driver.api.core.cql.SimpleStatement;
import com.datastax.oss.driver.api.core.cql.Statement;
import com.datastax.oss.driver.api.core.metadata.Metadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;

import static ru.n1ppl3.spring.data.cassandra.utils.CassandraUtils.printResultSet;


public class SimpleRepository {
    private static final Logger logger = LoggerFactory.getLogger(SimpleRepository.class);


    private final CqlSession cqlSession;

    public SimpleRepository(@NonNull CqlSession cqlSession) {
        this.cqlSession = cqlSession;
    }


    /**
     *
     */
    public void printMetadata() {
        Metadata metadata = cqlSession.refreshSchema();
        System.err.println("ClusterName = " + metadata.getClusterName());
        System.err.println("Nodes: " + metadata.getNodes());
        System.err.println("KeySpaces: " + metadata.getKeyspaces().keySet());
    }

    /**
     *
     */
    public void printSystemLocalTable() {
        ResultSet rs = cqlSession.execute("select * from system.local");
        printResultSet(rs);
    }

    /**
     *
     */
    public void printServerTime() {
        logger.info("Server time is {}", getServerDateTime());
    }

    public Object getServerDateTime() {
        ResultSet rs = cqlSession.execute("SELECT toTimestamp(now()) FROM system.local");
        Row row = rs.one();
        return row != null ? row.getObject(0) : null;
    }

    /**
     *
     */
    public void simpleStatements() {
        Statement<?> statement1 = SimpleStatement.newInstance("select * from system.local where key = 'local'");
        ResultSet rs1 = cqlSession.execute(statement1);
        printResultSet(rs1);

        Statement<?> statement2 = SimpleStatement.builder("select * from system.local where key = ?")
            .addPositionalValue("local")
            .build();
        ResultSet rs2 = cqlSession.execute(statement2);
        printResultSet(rs2);

        Statement<?> statement3 = SimpleStatement.builder("select * from system.local where key = :myKey")
            .addNamedValue("myKey", "local")
            .build();
        ResultSet rs3 = cqlSession.execute(statement3);
        printResultSet(rs3);
    }

    /**
     * проверить ситуацию с кешированием preparedStatement в локальной переменной и "падением" одного из DC, что будет? TODO
     */
    public void preparedStatements() {
        PreparedStatement preparedStatement = cqlSession.prepare("select * from system.local where key = ?");
        BoundStatement boundStatement = preparedStatement.bind("local")
            .setConsistencyLevel(ConsistencyLevel.LOCAL_ONE);
        ResultSet rs = cqlSession.execute(boundStatement);
        logger.info("execInfo: {}", rs.getExecutionInfo());
        printResultSet(rs);
    }

}
