package ru.n1ppl3.spring.data.cassandra;

import org.cassandraunit.utils.EmbeddedCassandraServerHelper;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest
class SpringDataCassandraExamplesApplicationTests {

	@BeforeAll
	static void beforeAll() throws IOException, InterruptedException {
		EmbeddedCassandraServerHelper.startEmbeddedCassandra();
	}

	@Test
	void contextLoads() {
	}

	@AfterAll
	static void afterAll() {
		EmbeddedCassandraServerHelper.cleanEmbeddedCassandra();
	}

}
