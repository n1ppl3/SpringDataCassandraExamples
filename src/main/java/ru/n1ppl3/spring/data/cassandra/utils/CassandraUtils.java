package ru.n1ppl3.spring.data.cassandra.utils;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.ResultSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class CassandraUtils {
    private static final Logger logger = LoggerFactory.getLogger(CassandraUtils.class);

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

    public static Set<Path> listFilesInDir(URI dir) throws IOException {
        try (Stream<Path> filesStream = Files.list(Paths.get(dir))) {
             return filesStream
                 .filter(file -> !Files.isDirectory(file))
                 .collect(Collectors.toSet());
        }
    }

}
