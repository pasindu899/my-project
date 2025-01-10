package com.aiken.pos.admin.config;

import com.aiken.pos.admin.constant.Endpoint;
import com.aiken.pos.admin.exception.BlockedUserException;
import com.aiken.pos.admin.model.SysUser;
import com.aiken.pos.admin.service.UserService;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Sajith Alahakoon
 * @version aiken-admin-portal / 1.0
 * @since 2024-May-29
 */

public class CustomDaoAuthenticationProvider extends DaoAuthenticationProvider {
    private final UserService userService;

    public CustomDaoAuthenticationProvider(UserService userService) {
        this.userService = userService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        //get current request
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        //get username
        String username = authentication.getName();
        StringBuffer requestURL = request.getRequestURL();
        logger.info("requestURL: " +requestURL);

        //check whether the request is rest or web
        if (request.getRequestURL().toString().toUpperCase().contains(Endpoint.URL_API_V1.toUpperCase())) {
            return super.authenticate(authentication);
        } else {
            //web requests
            if (isPosUser(username)) {

                logger.info("You are not an authorized user to login");

                throw new BlockedUserException("You are not an authorized user to log in");
            } else {
                //default flow
                return super.authenticate(authentication);
            }
        }
    }

    private boolean isPosUser(String username) throws AuthenticationException {

        try {
            //find user role by username
            SysUser sysUser = userService.loadUserByUsername(username);
            String userRole = sysUser.getUserRole();
            logger.info("user role:" + userRole);

            return sysUser.getUserRole().equalsIgnoreCase("ROLE_POS_USER");
        } catch (Exception e) {
            throw new BlockedUserException("You are not an authorized user to log in");
        }
    }

}

