package ru.n1ppl3.spring.data.cassandra.utils;

import com.datastax.oss.driver.api.core.CqlIdentifier;
import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.ColumnDefinition;
import com.datastax.oss.driver.api.core.cql.ColumnDefinitions;
import com.datastax.oss.driver.api.core.cql.ResultSet;
import com.datastax.oss.driver.api.core.cql.Row;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class CassandraUtils {
    private static final Logger logger = LoggerFactory.getLogger(CassandraUtils.class);

    /**
     *
     */
    public static void executeScriptsInFolder(CqlSession cqlSession, URI pathToFolder) throws IOException {
        Set<Path> filesInDir = listFilesInDir(pathToFolder);
        for (Path file : filesInDir) {
            logger.info("Gonna read {}", file);
            String content = new String(Files.readAllBytes(file));
            logger.info("Gonna execute:\n{}", content);
            ResultSet rs = cqlSession.execute(content);
            logger.info("\n{} executed {}", file, rs.getExecutionInfo());
        }
    }

    /**
     *
     */
    public static Set<Path> listFilesInDir(URI dir) throws IOException {
        try (Stream<Path> filesStream = Files.list(Paths.get(dir))) {
             return filesStream
                 .filter(file -> !Files.isDirectory(file))
                 .collect(Collectors.toSet());
        }
    }

    /**
     *
     */
    public static void printResultSet(ResultSet rs) {
        List<CqlIdentifier> columnNames = toColumnNames(rs.getColumnDefinitions());

        columnNames.forEach(columnIdentifier -> System.err.print("" + columnIdentifier + "\t|"));
        System.err.println();

        for (Row row : rs) {
            for (CqlIdentifier columnName : columnNames) {
                System.err.print("" + row.getObject(columnName) + "\t|");
            }
        }
        System.err.println();
    }

    /**
     *
     */
    public static List<CqlIdentifier> toColumnNames(ColumnDefinitions columnDefinitions) {
        List<CqlIdentifier> result = new ArrayList<>(columnDefinitions.size());
        for (ColumnDefinition columnDefinition : columnDefinitions) {
            result.add(columnDefinition.getName());
        }
        return result;
    }

}
