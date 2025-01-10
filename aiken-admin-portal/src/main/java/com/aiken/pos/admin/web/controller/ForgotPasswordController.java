/**
 * 
 */
package com.aiken.pos.admin.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.aiken.pos.admin.constant.Endpoint;
import com.aiken.pos.admin.constant.ErrorCode;
import com.aiken.pos.admin.constant.EventMessage;
import com.aiken.pos.admin.exception.GenericException;
import com.aiken.pos.admin.helper.SessionHelper;
import com.aiken.pos.admin.model.CommonData;
import com.aiken.pos.admin.model.Device;
import com.aiken.pos.admin.service.UserService;
import com.aiken.pos.admin.util.LoginUserUtil;
import com.aiken.pos.admin.web.form.CommonDataForm;
import com.aiken.pos.admin.web.form.ForgotPasswordForm;


/**
 * @author Nandana Basnayake
 *
 * @created_at Nov 09, 2021
 */
@Controller
@RequestMapping(Endpoint.URL_FORGOT_PASSWORD)
public class ForgotPasswordController {
	
	@Autowired
	private UserService userService;
	
	private Logger logger = LoggerFactory.getLogger(ForgotPasswordController.class);
	
	 	@GetMapping
	    public String viewForgotPasswordPage(HttpServletRequest request, Model model) {
		 ForgotPasswordForm forgotPasswordForm = new ForgotPasswordForm();
		 
		 	model.addAttribute("forgotPasswordForm",forgotPasswordForm );

	        return Endpoint.PAGE_FORGOT_PASSWORD;
	    }
	 

		@PostMapping
		public String requestPasswordReset(HttpServletRequest request, @ModelAttribute @Valid ForgotPasswordForm forgotPasswordForm,
				BindingResult result, Model model) throws GenericException {
			logger.info("Forgot Passowrd. Request Reset");
			SessionHelper.removeFormInSession(request);
			if ((forgotPasswordForm == null) || (forgotPasswordForm.getUsername().isEmpty())) {
				return Endpoint.REDIRECT_NAME + Endpoint.URL_FORGOT_PASSWORD;
			}
			logger.info("Forgot Passowrd [UserName] :" + forgotPasswordForm.getUsername());
			if(userService.forgotPassword(forgotPasswordForm.getUsername())) {
				return "redirect:/forgot-password?success";
			}else {
				return "redirect:/forgot-password?error";
			}
			

		}
	
}
