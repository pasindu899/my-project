package com.aiken.pos.admin.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Global Error
 *
 * @author Asela Liyanage
 * @version 1.0
 * @since 2021-08-24
 */

@Component
public class LoggingAccessDeniedHandler implements AccessDeniedHandler {

	private static Logger log = LoggerFactory.getLogger(LoggingAccessDeniedHandler.class);

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException ex)
			throws IOException, ServletException {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		if (auth != null) {
			log.info(auth.getName() + " was trying to access protected resource: " + request.getRequestURI());
			response.sendError(HttpServletResponse.SC_FORBIDDEN, "Access Denied: You do not have permission to access this resource");
		}

		//response.sendRedirect(request.getContextPath() + "/403");

	}
}
