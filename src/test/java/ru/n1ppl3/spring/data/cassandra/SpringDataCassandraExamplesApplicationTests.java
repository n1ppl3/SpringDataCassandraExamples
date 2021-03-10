package ru.n1ppl3.spring.data.cassandra;

import org.cassandraunit.utils.EmbeddedCassandraServerHelper;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import ru.n1ppl3.spring.data.cassandra.utils.CassandraUtils;

import java.io.IOException;
import java.net.URI;

@SpringBootTest
class SpringDataCassandraExamplesApplicationTests {

	@BeforeAll
	static void beforeAll() throws IOException, InterruptedException {
		EmbeddedCassandraServerHelper.startEmbeddedCassandra();
		URI scriptsDir = new ClassPathResource("/scripts").getURI();
		CassandraUtils.executeScriptsInFolder(EmbeddedCassandraServerHelper.getSession(), scriptsDir);
	}

	@Test
	void contextLoads() {
	}

	@AfterAll
	static void afterAll() {
		//EmbeddedCassandraServerHelper.cleanEmbeddedCassandra();
	}

}
