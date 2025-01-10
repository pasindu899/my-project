package com.aiken.pos.admin.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Validation Helper(Not using in v1)
 *
 * @author Asela Liyanage
 * @version 1.0
 * @since 2021-01-21
 */

@Configuration
public class WebConfiguration implements WebMvcConfigurer {

	@Bean
	public MessageSource messageSource() {
		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
		// Load file: validation.properties
		messageSource.setBasename("classpath:validation");
		messageSource.setDefaultEncoding("UTF-8");
		return messageSource;
	}

}