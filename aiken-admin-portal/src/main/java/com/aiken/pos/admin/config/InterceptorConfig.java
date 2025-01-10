/**
 * 
 */
package com.aiken.pos.admin.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @author Nandana Basnayake
 *
 * @created_On Dec 2, 2022
 */

@SuppressWarnings("deprecation")
@Configuration
public class InterceptorConfig extends WebMvcConfigurerAdapter{
	
	/*
	 * @Autowired LoggerInterceptor logInterceptor;
	 * 
	 * @Override public void addInterceptors(InterceptorRegistry registry) {
	 * registry.addInterceptor(logInterceptor); }
	 */
}
