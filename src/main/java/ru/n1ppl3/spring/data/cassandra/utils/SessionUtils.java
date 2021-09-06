package ru.n1ppl3.spring.data.cassandra.utils;

import com.datastax.oss.driver.api.core.CqlSession;

public abstract class SessionUtils {

    public static CqlSession buildDefaultCqlSession() {
        // uses defaults from application.conf file
        return CqlSession.builder()
            .withApplicationName("simple-repository")
            .build();
    }
}
