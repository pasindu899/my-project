/**
 * 
 */
package com.aiken.pos.admin.constant;

/**
 * @author Nandana Basnayake
 *
 * @created at Nov 6, 2021
 */
public interface UserConstants {
	

    String ROLE_ADMIN = "ROLE_ADMIN";
    String ROLE_MANAGER = "ROLE_MANAGER";
    String ROLE_POS_USER = "ROLE_POS_USER";
    String ROLE_USER = "ROLE_USER";
    String ROLE_BANK_USER = "ROLE_BANK_USER";
    
	Integer ADMIN_MAX_USER_LIMIT = 1;
	Integer MANAGER_MAX_USER_LIMIT = 3;
	Integer POS_USER_MAX_USER_LIMIT = 1;
	Integer USER_MAX_USER_LIMIT = 25;
	Integer USER_MAX_BANK_USER_LIMIT = 25;

	int MAX_FAILED_ATTEMPTS = 5;
	long LOCK_TIME_DURATION = 60 * 60 * 1000; // One hour
	
	/* ####### Password Validation pattern.##############################################
	 * (?=.*[0-9]) represents a digit must occur at least once. 
	 * (?=.*[a-z]) represents a lower case alphabet must occur at least once.
	 * (?=.*[A-Z]) represents an upper case alphabet that must occur at least once.
	 * (?=.*[@#$%^&-+=()] represents a special character that must occur at least once.
	 * (?=\\S+$) white spaces don’t allowed in the entire string. 
	 * .{8, 20} represents at least 8 characters and at most 20 characters.
	 */
	String PASSWORD_PATTERN = "^(?=.*[0-9])" 
            + "(?=.*[a-z])(?=.*[A-Z])" 		
            + "(?=.*[@#$%^&+=])"			
            + "(?=\\S+$).{8,20}$";
	
	String PASSWORD_CONSTRAINTS = "Your password should contains;\n"
			+ "* At least 8 characters and at most 20 characters.\n "
			+ "* At least one digit.\n "
			+ "* At least one upper case alphabet.\n "
			+ "* At least one lower case alphabet.\n "
			+ "* At least one special character (!@#$%&+=^)\n"
			+ "* Doesn’t contain any white space." ;
	
	String NOT_ENOUGH_LENGTH_FOR_TEMP_PASSWORD = "Temporary password should have at least four characters";
	String USERNAME_NOT_AVAILABLE = "Given Username is not available. Please try again with another Username";
	String USERNAME_IS_REQUIRED = "Username is required";
	String PASSWORD_NOT_MATCH = "Passwords are not matching. Please try again.";
	String ONLINE_USER = "ONLINE_USER_REQUEST";
	String MAX_USER_ACCOUNT_LIMIT_REACHED = "Maximum user accounts limit has reached. You can not create new accounts under this User-Role";
	

	
	

	
}
