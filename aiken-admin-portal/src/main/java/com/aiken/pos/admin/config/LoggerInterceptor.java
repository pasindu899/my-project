/**
 * 
 */
package com.aiken.pos.admin.config;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.net.URI;
import java.net.URL;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;

/**
 * @author Nandana Basnayake
 *
 * @created_On Dec 2, 2022
 */
@Component
public class LoggerInterceptor implements HandlerInterceptor{
	Logger log = org.slf4j.LoggerFactory.getLogger(this.getClass());
	
	/*
	 * @Override public void afterCompletion(HttpServletRequest request,
	 * HttpServletResponse response, Object object, Exception arg3) throws Exception
	 * { log.info("Request is complete"); }
	 * 
	 * @Override public void postHandle(HttpServletRequest request,
	 * HttpServletResponse response, Object object, ModelAndView model) throws
	 * Exception { log.info("Handler execution is complete");
	 * log.info("RequestURI: " + request.getRequestURI()); URL url = new
	 * URL(request.getRequestURL().toString()); String host = url.getHost(); String
	 * userInfo = url.getUserInfo(); String scheme = url.getProtocol();
	 * 
	 * int port = url.getPort(); String path = (String)
	 * request.getAttribute("javax.servlet.forward.request_uri"); String query =
	 * (String) request.getAttribute("javax.servlet.forward.query_string");
	 * 
	 * URI uri = new URI(scheme,userInfo,host,port,path,query,null);
	 * log.info("Request-URI: " + uri);
	 * 
	 * }
	 * 
	 * @Override public boolean preHandle(HttpServletRequest request,
	 * HttpServletResponse response, Object object) throws Exception {
	 * log.info("Before Handler execution"); return true; }
	 */

}
