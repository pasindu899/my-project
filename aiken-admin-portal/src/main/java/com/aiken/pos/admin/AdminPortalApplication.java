package com.aiken.pos.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Admin Portal Application Runner
 * 
 * @author Asela Liyanage
 * @version 1.0
 * @since 2021-01-30
 */
@SpringBootApplication
@EnableScheduling
public class AdminPortalApplication {

	public static void main(String[] args) {

		//SpringApplication.run(AdminPortalApplication.class, args);
		SpringApplicationBuilder builder = new SpringApplicationBuilder(AdminPortalApplication.class);

		builder.headless(false);

		ConfigurableApplicationContext context = builder.run(args);
	}

}
