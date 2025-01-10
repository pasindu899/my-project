package com.aiken.pos.admin.config;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Initialize Hikari Database Connection Pool
 *
 * @author Asela Liyanage
 * @version 1.0
 * @since 2021-01-21
 */

@Configuration
public class DataSourceConfiguration {

	Logger logger = LoggerFactory.getLogger(DataSourceConfiguration.class);

	@Bean(name = "dataSource")
	@ConfigurationProperties("app.datasource")
	public DataSource dataSource() {
		logger.info("$$$$$$$$$ INITIALIZING HIKARAI DB POOL $$$$$$$$$");
		DataSource ds = null;
		try {
			ds = DataSourceBuilder
					.create()
					.build();
		} catch (Exception e) {
			logger.error("HIKARAI POOP INITIALIZE FAIL: " + e);
		}
		return ds;
	} 
}