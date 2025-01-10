/**
 * 
 */
package com.aiken.pos.admin.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;
import org.springframework.stereotype.Component;
import com.aiken.pos.admin.exception.GenericException;
import com.aiken.pos.admin.service.UserService;
import com.aiken.pos.admin.util.CustomUserDetails;


/**
 * @author Nandana Basnayake
 *
 * @created_On Oct 7, 2022
 */
@Component
public class CustomLogoutSuccessHandler extends SimpleUrlLogoutSuccessHandler  {
	
	@Autowired
    private UserService userService;
	private Logger logger = LoggerFactory.getLogger(CustomLogoutSuccessHandler.class);

    @Override
    public void onLogoutSuccess(HttpServletRequest request,
                HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {
         
    	CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        Integer userId = userDetails.getUserId();
        String Username = userDetails.getUsername();
         
        logger.info("The user " + Username + " has logged out.");
        try {
			userService.updateUserLoginLogout(userId,false);
		} catch (GenericException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        super.onLogoutSuccess(request, response, authentication);
    }  
}
