package org.zerock.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
@ComponentScan(basePackages = { "org.zerock.sample" })
@MapperScan(basePackages = { "org.zerock.mapper" })
public class RootConfig {
	@Bean
	public DataSource dataSource() {
		String jdbcUrl = System.getenv("DB_URL");
		String username = System.getenv("DB_USERNAME");
		String password = System.getenv("DB_PASSWORD");
		requireValue("DB_URL", jdbcUrl);
		requireValue("DB_USERNAME", username);
		requireValue("DB_PASSWORD", password);

		Properties properties = new Properties();
		properties.setProperty("driverClassName", "oracle.jdbc.OracleDriver");
		properties.setProperty("jdbcUrl", jdbcUrl);
		properties.setProperty("username", username);
		properties.setProperty("password", password);
		properties.setProperty("initializationFailTimeout", "-1");
		properties.setProperty("minimumIdle", "0");
		HikariConfig hikariConfig = new HikariConfig(properties);
		return new HikariDataSource(hikariConfig);
	}

	@Bean
	public SqlSessionFactory sqlSessionFactory() throws Exception {
		SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
		sqlSessionFactoryBean.setDataSource(dataSource());
		return sqlSessionFactoryBean.getObject();
	}

	private static void requireValue(String name, String value) {
		if (value == null || value.trim().isEmpty()) {
			throw new IllegalStateException(name + " environment variable is required");
		}
	}
}
