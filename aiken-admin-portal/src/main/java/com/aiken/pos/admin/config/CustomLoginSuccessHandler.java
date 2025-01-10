package com.aiken.pos.admin.config;

import com.aiken.pos.admin.exception.GenericException;
import com.aiken.pos.admin.service.UserService;
import com.aiken.pos.admin.util.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Nandana Basnayake
 * @version 1.0
 */
@Component
public class CustomLoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    @Autowired
    private UserService userService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        //AuthenticationSuccessHandler target = new SavedRequestAwareAuthenticationSuccessHandler();

        if (userDetails.getFailedAttempt() > 0) {
            try {
                userService.resetFailedAttempts(userDetails.getUsername());
                userService.updateUserLoginLogout(userDetails.getUserId(), true);

            } catch (GenericException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } else if (userDetails.isNewUser()) {
            response.sendRedirect("new-login");

        } else {
            try {
                userService.updateUserLoginLogout(userDetails.getUserId(), true);
            } catch (GenericException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        }
        super.onAuthenticationSuccess(request, response, authentication);
    }
}
