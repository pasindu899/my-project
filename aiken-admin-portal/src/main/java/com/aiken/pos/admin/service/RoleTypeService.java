package com.aiken.pos.admin.service;

import java.util.Arrays;
import java.util.List;
import org.springframework.stereotype.Service;
import com.aiken.pos.admin.config.UserRoleMapper;
import com.aiken.pos.admin.constant.UserConstants;


/**
 *  * 
 * @author Nandana Basnayake
 * 
 * @since 2021-11-08
 */

@Service
public class RoleTypeService {


	public List<String> getRoleTypes() {

		return Arrays.asList(
				UserConstants.ROLE_BANK_USER,
				UserConstants.ROLE_POS_USER,
				UserConstants.ROLE_USER,
				UserConstants.ROLE_MANAGER,
				UserConstants.ROLE_ADMIN
				);
	}
}
