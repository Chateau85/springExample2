package org.zerock.persistence;

import static org.junit.Assert.fail;

import java.sql.Connection;
import java.sql.DriverManager;

import org.junit.Test;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JDBCTests {
	static {
		try {
			Class.forName("oracle.jdbc.OracleDriver");
		} catch (Exception e) {
			throw new ExceptionInInitializerError(e);
		}
	}

	@Test
	public void testConnection() {
		try (Connection conn = DriverManager.getConnection(requiredEnvironment("DB_URL"),
				requiredEnvironment("DB_USERNAME"), requiredEnvironment("DB_PASSWORD"))) {
			log.info("{}", conn);
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	private static String requiredEnvironment(String name) {
		String value = System.getenv(name);
		if (value == null || value.trim().isEmpty()) {
			throw new IllegalStateException(name + " environment variable is required");
		}
		return value;
	}
}
