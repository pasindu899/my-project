package com.aiken.pos.admin.config;

/**
 * Map Application User Roles
 *
 * @author Asela Liyanage
 * @version 1.0
 * @since 2021-01-21
 */
public interface UserRoleMapper {

    String ROLE_ADMIN = "ADMIN";
    String ROLE_MANAGER = "MANAGER";
    String ROLE_POS_USER = "POS_USER";
    String ROLE_USER = "USER";
    String ROLE_BANK_USER = "BANK_USER";

    String PRE_AUTH_ADMIN = "hasRole('ADMIN')";
    String PRE_AUTH_MANAGER = "hasRole('MANAGER')";
    String PRE_AUTH_POS_USER = "hasRole('POS_USER')";
    String PRE_AUTH_USER = "hasRole('USER')";
    String PRE_AUTH_BANK_USER = "hasRole('BANK_USER')";

    String PRE_AUTH_ADMIN_OR_MANAGER = "hasRole('ADMIN') or hasRole('MANAGER')";
    String PRE_AUTH_ADMIN_OR_USER = "hasRole('ADMIN') or hasRole('USER')";
    String PRE_AUTH_MANAGER_OR_USER = "hasRole('MANAGER') or hasRole('USER')";

    String PRE_AUTH_ADMIN_OR_POS_USER = "hasRole('ADMIN') or hasRole('POS_USER')";

    String PRE_AUTH_ADMIN_OR_MANAGER_OR_POS_USER = "hasRole('ADMIN') or hasRole('MANAGER') or hasRole('POS_USER')";

    String PRE_AUTH_ADMIN_OR_MANAGER_OR_POS_USER_OR_USER = "hasRole('ADMIN') or hasRole('MANAGER') or hasRole('POS_USER') or hasRole('USER')";
    
    String PRE_AUTH_ADMIN_OR_MANAGER_OR_POS_USER_OR_USER_OR_BANK_USER = "hasRole('ADMIN') or hasRole('MANAGER') or hasRole('POS_USER') or hasRole('USER') or hasRole('BANK_USER')";

}
