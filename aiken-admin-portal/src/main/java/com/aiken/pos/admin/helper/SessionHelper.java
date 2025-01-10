package com.aiken.pos.admin.helper;

import javax.servlet.http.HttpServletRequest;

import com.aiken.pos.admin.constant.CommonConstant;
import com.aiken.pos.admin.web.form.FormInfo;

/**
 * Shopping Cart Helper
 *
 * @author Asela Liyanage
 * @version 1.0
 * @since 2021-01-29
 */
public class SessionHelper {

	// device stored in Session.
	public static FormInfo getFormInSession(HttpServletRequest request) {

		FormInfo formInfo = (FormInfo) request.getSession().getAttribute(CommonConstant.SESSION_VAR_NAME);
		
		if (formInfo == null) {
			formInfo = new FormInfo();

			request.getSession().setAttribute(CommonConstant.SESSION_VAR_NAME, formInfo);
		}

		return formInfo;
	}

	public static void removeFormInSession(HttpServletRequest request) {
		request.getSession().removeAttribute(CommonConstant.SESSION_VAR_NAME);
	}

	public static void storeLastFormInSession(HttpServletRequest request, FormInfo formInfo) {
		request.getSession().setAttribute(CommonConstant.SESSION_VAR_NAME, formInfo);
	}

	public static FormInfo getLastFormInSession(HttpServletRequest request) {
		return (FormInfo) request.getSession().getAttribute(CommonConstant.SESSION_VAR_NAME);
	}

	public static FormInfo getFormBinInSession(HttpServletRequest request) {

		FormInfo formInfo = (FormInfo) request.getSession().getAttribute(CommonConstant.SESSION_DEVICE_BIN_FORM);

		if (formInfo == null) {
			formInfo = new FormInfo();

			request.getSession().setAttribute(CommonConstant.SESSION_DEVICE_BIN_FORM, formInfo);
		}

		return formInfo;
	}

	public static void removeFormBinInSession(HttpServletRequest request) {
		request.getSession().removeAttribute(CommonConstant.SESSION_DEVICE_BIN_FORM);
	}

	public static void storeLastFormBinInSession(HttpServletRequest request, FormInfo formInfo) {
		request.getSession().setAttribute(CommonConstant.SESSION_DEVICE_BIN_FORM, formInfo);
	}

	public static FormInfo getLastFormBinInSession(HttpServletRequest request) {
		return (FormInfo) request.getSession().getAttribute(CommonConstant.SESSION_DEVICE_BIN_FORM);
	}

}