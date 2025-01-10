package com.aiken.pos.admin.util;
/**
 * @author Nandana Basnayake
 * @version 1.1
 * @since 2021-11-05
 */
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.Model;

import com.aiken.pos.admin.constant.Endpoint;

public class LoginUserUtil {

	private static Logger logger = LoggerFactory.getLogger(LoginUserUtil.class);

	public static void loadLoginUser(Model model) {
		try {
			CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
					.getPrincipal();
			model.addAttribute("user", userDetails.getUsername());
			model.addAttribute("firstname", userDetails.getFirstName());
			model.addAttribute("lastname", userDetails.getLastName());
			model.addAttribute("userrole", userDetails.getUserRole());
			model.addAttribute("usergroup", userDetails.getUserGroup());
			
			
		} catch (Exception e) {
			logger.error("Load login user fail: " + e);
		}
	}
	public static boolean isNewUserLogin() {
		try {
			CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
					.getPrincipal();
			
			if (userDetails.isNewUser()){
				return true;
			}

		} catch (Exception e) {
			logger.error("Load login user fail: " + e);
		}
		return false;
	}

}
