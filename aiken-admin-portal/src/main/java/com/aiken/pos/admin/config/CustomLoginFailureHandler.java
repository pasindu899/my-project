package com.aiken.pos.admin.config;

import com.aiken.pos.admin.constant.UserConstants;
import com.aiken.pos.admin.exception.GenericException;
import com.aiken.pos.admin.model.SysUser;
import com.aiken.pos.admin.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;

/**
 * @author Nandana Basnayake
 * @version 1.0
 */

@Component
public class CustomLoginFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    private static Logger logger = LoggerFactory.getLogger(CustomLoginFailureHandler.class);
    @Autowired
    private UserService userService;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {
        String username = request.getParameter("username");
        SysUser user;

        try {
            user = userService.loadUserByUsername(username);
            if (user != null) {
                if (user.isActive() && user.isAccountNonLocked() && !(user.getUserRole().equalsIgnoreCase(UserConstants.ROLE_ADMIN) || user.getUserRole().equalsIgnoreCase(UserConstants.ROLE_POS_USER))) {
                    if (user.getFailedAttempt() < UserConstants.MAX_FAILED_ATTEMPTS - 1) {
                        try {
                            userService.increaseFailedAttempts(user);
                            logger.warn("Invalid login credencials: increase Failed Attempts");
                        } catch (GenericException e) {
                            // TODO Auto-generated catch block
                            logger.warn("increaseFailedAttempts Error");

                        }
                    } else {
                        try {
                            if (user.getUserRole().equalsIgnoreCase(UserConstants.ROLE_MANAGER)) {

                                exception = new LockedException("Your account has been locked due to 5 failed attempts."
                                        + " Please contact your system administrator.");
                                logger.info(UserConstants.MAX_FAILED_ATTEMPTS + " - failed attempts: Account Locked");
                            } else {
                                exception = new LockedException("Your account has been locked due to 5 failed attempts."
                                        + " Please wait one hour for auto unlock or contact your system administrator.");
                                logger.info(UserConstants.MAX_FAILED_ATTEMPTS + " - failed attempts: Account Locked");
                            }
                            userService.lock(user);

                        } catch (GenericException e) {
                            // TODO Auto-generated catch block

                            logger.warn("increaseFailedAttempts |User Lock Error: " + e.getMessage());
                        }

                    }
                } else if (!user.isAccountNonLocked()) {
                    try {
                        if (!user.getUserRole().equalsIgnoreCase(UserConstants.ROLE_MANAGER) || user.getUserRole().equalsIgnoreCase(UserConstants.ROLE_ADMIN)) {
                            if (userService.unlockWhenTimeExpired(user)) {
                                exception = new LockedException(
                                        "Your account has been unlocked. Please try to login again.");
                                logger.info("Account Unlocked");
                            }
                        }

                    } catch (GenericException | ParseException e) {
                        // TODO Auto-generated catch block
                        logger.warn("AccountNonLocked Error: " + e.getMessage());

                    }
                }

            } else {
                logger.warn("User Not Found");
                exception = new LockedException(
                        "Invalid Username or Password. Please try again.");
            }

        } catch (GenericException e1) {
            // TODO Auto-generated catch block
            logger.warn("User Load Error: " + e1.getMessage());
        }
        super.setDefaultFailureUrl("/login?error");
        super.onAuthenticationFailure(request, response, exception);
    }

}
