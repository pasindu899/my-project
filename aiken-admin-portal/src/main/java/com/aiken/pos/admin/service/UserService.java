package com.aiken.pos.admin.service;

import com.aiken.common.util.validation.DateUtil;
import com.aiken.common.util.validation.StringUtil;
import com.aiken.pos.admin.constant.UserConstants;
import com.aiken.pos.admin.exception.GenericException;
import com.aiken.pos.admin.model.CommonUserData;
import com.aiken.pos.admin.model.SysUser;
import com.aiken.pos.admin.repository.SysUserRepository;
import com.aiken.pos.admin.util.CustomUserDetails;
import com.aiken.pos.admin.util.email.SendEmailAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * User Service
 *
 * @author Nandana Basnayake
 * @version 1.0
 * @since 2021-11-04
 */

@Service
public class UserService {
    private static Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private SysUserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private SendEmailAdapter sendEmailAdapter;

    public SysUser loadUserByUsername(String username) throws GenericException {

        SysUser sysUser = userRepository.findByUsername(username);
        if (sysUser == null) {
            logger.warn("Invalid User : {}", username);
        }
        return sysUser;
    }

    public SysUser loadUserByUserId(int userid) {

        SysUser sysUser = userRepository.findByUserId(userid);
        if (sysUser == null) {
            logger.warn("Invalid User  : {}", userid);
        }
        return sysUser;
    }

    public void increaseFailedAttempts(SysUser user) throws GenericException {
        int newFailAttempts = user.getFailedAttempt() + 1;
        logger.warn("[ {} ]  #Login Fail Attempts: {}", user.getUsername(), newFailAttempts);
        userRepository.updateFailedAttempts(newFailAttempts, user.getUsername());
    }

    public void resetFailedAttempts(String username) throws GenericException {
        logger.info("[ {} ]  Reset Failed Attempts", username);
        userRepository.updateFailedAttempts(0, username);
    }

    public void lock(SysUser user) throws GenericException {
        user.setAccountNonLocked(false);
        user.setLockTime(DateUtil.getCurrentTime());
        if (!user.getUserRole().equals(UserConstants.ROLE_ADMIN)) {
            userRepository.lockUser(user);
        }
    }

    public void resetPW(CommonUserData customUser) throws GenericException {
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        SysUser user = new SysUser();
        user.setUsername(userDetails.getUsername());
        user.setPassword(passwordEncoder.encode(customUser.getNewPassword()));
        user.setUpdatedBy(userDetails.getUsername());
        user.setLastUpdate(DateUtil.getCurrentTime());
        user.setNewUser(false);
        userRepository.changePw(user);
    }

    public void changePW(SysUser user) throws GenericException {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setNewUser(false);
        userRepository.changePw(user);
    }

    public void createUser(SysUser user) throws GenericException {
        if (!user.getUserRole().equals(UserConstants.ROLE_BANK_USER)) {
            user.setUserGroup("GENERAL");
        }
        String password = user.getPassword();
        user.setPassword(passwordEncoder.encode(password));
        Integer userCount = userRepository.createUser(user);
        if (userCount > 0) {
            logger.info("### Mail sending ###");
            sendEmailAdapter.sendEmail(user.getEmail(), user.getUsername(), password);

        } else {
            logger.info("user creation failed");
            throw new BadCredentialsException("user creation failed");
        }
//        try {
//            logger.info("Create User: {}", user.getClass().toString());
//
//            //String o = AuditObjectMapper.mapObject("INSERT", user, null, null);
//
//
//            //logger.info("Create User: " + o);
//        } catch (Exception e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//            logger.info("Create User: {}", e.getMessage());
//        }
    }

    public void resetUserPW(SysUser user) throws GenericException {
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        String password = user.getPassword();
        user.setPassword(passwordEncoder.encode(password));
        user.setNewUser(true);
        user.setReqReset(false);
        user.setReqResetOn(null);
        user.setUpdatedBy(userDetails.getUsername());
        user.setLastUpdate(DateUtil.getCurrentTime());
        userRepository.changePw(user);
        if (!StringUtil.isNullOrWhiteSpace(user.getEmail())) {
            logger.info("### Mail sending ###");
            sendEmailAdapter.sendEmail(user.getEmail(), user.getUsername(), password);
        } else {
            logger.info("Can not send a new password because no mail is added.");
        }

    }

    public boolean enableDisableUsers(int userId, String actionType) throws GenericException {
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        SysUser user = userRepository.findByUserId(userId);
        if (user == null)
            return false;

        if (actionType.equalsIgnoreCase("enable")) {
            user.setActive(true);
        } else
            user.setActive(false);
        user.setUpdatedBy(userDetails.getUsername());
        user.setLastUpdate(DateUtil.getCurrentTime());
        userRepository.enableDisableUser(user);
        return true;
    }

    public boolean updateUserLoginLogout(int userId, Boolean isLogin) throws GenericException {
        //CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        SysUser user = userRepository.findByUserId(userId);
        if (user == null)
            return false;

        if (isLogin) {
            user.setLoginTime(DateUtil.getCurrentTime());
            user.setLastLogoutTime(null);
        } else {
            user.setLastLogoutTime(DateUtil.getCurrentTime());
        }
        userRepository.updateUserLoginLogout(user);
        return true;
    }

    public boolean lockUnlockUsers(int userId, String actionType) throws GenericException {
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        SysUser user = userRepository.findByUserId(userId);
        if (user == null)
            return false;

        if (actionType.equalsIgnoreCase("lock")) {
            user.setAccountNonLocked(false);
            user.setLockTime(DateUtil.getCurrentTime());
        } else {
            user.setAccountNonLocked(true);
            user.setLockTime(null);
            user.setFailedAttempt(0);
        }
        user.setUpdatedBy(userDetails.getUsername());
        user.setLastUpdate(DateUtil.getCurrentTime());
        userRepository.lockUnlockUser(user);
        return true;
    }

    public boolean isUserExist(String username) {
        return userRepository.existsByUsername(username);
    }

    public List<SysUser> findAllUsers() {
        return userRepository.findAllUsers();
    }

    public boolean isUserAccountsAvailable(String userRole) {
        Integer userLimit = 0;
        if (userRole.equalsIgnoreCase(UserConstants.ROLE_ADMIN)) {
            userLimit = UserConstants.ADMIN_MAX_USER_LIMIT;
        } else if (userRole.equalsIgnoreCase(UserConstants.ROLE_MANAGER)) {
            userLimit = UserConstants.MANAGER_MAX_USER_LIMIT;
        } else if (userRole.equalsIgnoreCase(UserConstants.ROLE_POS_USER)) {
            userLimit = UserConstants.POS_USER_MAX_USER_LIMIT;
        } else if (userRole.equalsIgnoreCase(UserConstants.ROLE_USER)) {
            userLimit = UserConstants.USER_MAX_USER_LIMIT;
        } else if (userRole.equalsIgnoreCase(UserConstants.ROLE_BANK_USER)) {
            userLimit = UserConstants.USER_MAX_BANK_USER_LIMIT;
        }
        return userRepository.isUserAccountsAvailable(userRole, userLimit);
    }

    public boolean forgotPassword(String username) throws GenericException {

        SysUser user = userRepository.findByUsername(username);
        if (user == null)
            return false;

        user.setReqReset(true);
        user.setReqResetOn(DateUtil.getCurrentTime());
        user.setAccountNonLocked(false);
        user.setUpdatedBy(UserConstants.ONLINE_USER);
        user.setLastUpdate(DateUtil.getCurrentTime());
        userRepository.forgotPasswordResetRequest(user);
        return true;
    }

    public boolean unlockWhenTimeExpired(SysUser user) throws GenericException, ParseException {
        logger.info("Account has Locked @ : {}", user.getLockTime());
        if (user.getLockTime() == null) {
            return false;
        }
        long lockTimeInMillis = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(user.getLockTime()).getTime();
        long currentTimeInMillis = System.currentTimeMillis();

        if (lockTimeInMillis + UserConstants.LOCK_TIME_DURATION < currentTimeInMillis) {
            user.setAccountNonLocked(true);
            user.setLockTime(null);
            user.setFailedAttempt(0);

            userRepository.unlockWhenTimeExpired(user); // Auto unlock

            return true;
        } else {
            logger.info("Account Locked @ : {} . Wait 1hr to unclock account", user.getLockTime());
            return false;
        }

    }

}