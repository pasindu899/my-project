/**
 *
 */
package com.aiken.pos.admin.web.controller;

import com.aiken.common.util.validation.DateUtil;
import com.aiken.pos.admin.config.UserRoleMapper;
import com.aiken.pos.admin.constant.Endpoint;
import com.aiken.pos.admin.constant.ErrorCode;
import com.aiken.pos.admin.constant.EventMessage;
import com.aiken.pos.admin.constant.UserConstants;
import com.aiken.pos.admin.exception.GenericException;
import com.aiken.pos.admin.helper.SessionHelper;
import com.aiken.pos.admin.model.CommonData;
import com.aiken.pos.admin.model.CommonUserData;
import com.aiken.pos.admin.model.SysUser;
import com.aiken.pos.admin.service.BankTypeService;
import com.aiken.pos.admin.service.RoleTypeService;
import com.aiken.pos.admin.service.UserService;
import com.aiken.pos.admin.util.CustomUserDetails;
import com.aiken.pos.admin.util.LoginUserUtil;
import com.aiken.pos.admin.web.form.PasswordResetForm;
import com.aiken.pos.admin.web.form.UserForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Nandana Basnayake
 *
 * @created at Nov 6, 2021 
 */
@Controller
public class UserWebController {
    private Logger logger = LoggerFactory.getLogger(UserWebController.class);

    @Autowired
    private UserService usersService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleTypeService roleTypeService;

    @Autowired
    private BankTypeService bankTypeService;


    @PostMapping(value = Endpoint.URL_RESET_PASSWORD)
    public String resetPassword(HttpServletRequest request, @ModelAttribute @Valid PasswordResetForm resetForm,
                                BindingResult result, Model model) throws GenericException {
        logger.info("First time login - Reset Password >>>>>>>>>>>>>>>>>>");
        // load login user
        LoginUserUtil.loadLoginUser(model);
        SessionHelper.removeFormInSession(request);
        if (resetForm == null) {

            return Endpoint.REDIRECT_TO_RESET_PASSWORD;
        }
        CommonUserData commonData = convertPasswordResetFormToCommonUserData(resetForm);

        if (!commonData.getNewPassword().equals(commonData.getRenewPassword())) {
            model.addAttribute("message", "Passwords are not matched. Please try again.");
            return Endpoint.PAGE_NEW_LOGIN;
        } else if (!isValidPassword(commonData.getNewPassword())) {
            model.addAttribute("message", UserConstants.PASSWORD_CONSTRAINTS);
            return Endpoint.PAGE_NEW_LOGIN;
        } else {
            usersService.resetPW(commonData);
            model.addAttribute("message", EventMessage.PASSWORD_CHANGED_PLEASE_LOGIN);
            model.addAttribute("back_link", Endpoint.LOGIN);
            return Endpoint.PAGE_OPERATION_SUCCESS;
        }

    }

    @PostMapping(value = Endpoint.URL_RESET_USER_PASSWORD)
    @PreAuthorize(UserRoleMapper.PRE_AUTH_ADMIN_OR_MANAGER)
    public String resetUserPassword(HttpServletRequest request, @ModelAttribute @Valid PasswordResetForm resetForm,
                                    BindingResult result, Model model) throws GenericException {
        logger.info("Reset User Password >>>>>>>>>>>>>>>>>>");
        // load login user
        LoginUserUtil.loadLoginUser(model);
        SessionHelper.removeFormInSession(request);
        if (resetForm == null) {
            return Endpoint.REDIRECT_NAME + Endpoint.URL_VIEW_ALL_USERS;
        }
        CommonUserData commonData = convertPasswordResetFormToCommonUserData(resetForm);

        if (!commonData.getNewPassword().equals(commonData.getRenewPassword())) {
            return Endpoint.REDIRECT_NAME + Endpoint.URL_VIEW_ALL_USERS;
        }
        SysUser user = usersService.loadUserByUserId(commonData.getUserId());
        if (user == null) {
            return Endpoint.REDIRECT_NAME + Endpoint.URL_VIEW_ALL_USERS;

        } else {
            user.setPassword(commonData.getNewPassword());
            user.setEmail(commonData.getEmail());
            usersService.resetUserPW(user);
            model.addAttribute("message", EventMessage.PASSWORD_CHANGED_SUCCESSFULLY);
            model.addAttribute("back_link", Endpoint.URL_VIEW_ALL_USERS);
            return Endpoint.PAGE_OPERATION_SUCCESS;
        }

    }

    @GetMapping(value = Endpoint.URL_MANAGE_ACCOUNT)
    public String loadManageAccountPage(HttpServletRequest request, Model model) {

        logger.info("load Manage Account page >>>>>>>>>>>>>>>>>>");
        // load login user
        LoginUserUtil.loadLoginUser(model);
        SessionHelper.removeFormInSession(request);
        PasswordResetForm changePwForm = new PasswordResetForm();
        model.addAttribute("changePwForm", changePwForm);
        return Endpoint.PAGE_MANAGE_ACCOUNT;
    }

    @PostMapping(value = Endpoint.URL_CHANGE_PASSWORD)
    public String setNewPassword(HttpServletRequest request, @ModelAttribute @Valid PasswordResetForm changePwForm,
                                 BindingResult result, Model model) throws GenericException {
        logger.info("Change Password >>>>>>>>>>>>>>>>>>");
        // load login user
        LoginUserUtil.loadLoginUser(model);

        SessionHelper.removeFormInSession(request);
        if (changePwForm == null) {

            return Endpoint.REDIRECT_TO_MANAGE_ACCOUNT;
        }
        CommonUserData commonData = convertPasswordResetFormToCommonUserData(changePwForm);
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        if (!passwordEncoder.matches(commonData.getCurrentPassword(), userDetails.getPassword())) {
            logger.info("Current PW not Matched");
            model.addAttribute("message", "Current Password is invalid. Please try again.");
            PasswordResetForm changePwForm1 = new PasswordResetForm();
            model.addAttribute("changePwForm", changePwForm1);
            model.addAttribute("enableDiv", "password");
            return Endpoint.PAGE_MANAGE_ACCOUNT;
        }
        if (!commonData.getNewPassword().equals(commonData.getRenewPassword())) {
            logger.info("New PWs are not Matched");
            model.addAttribute("message", "Passwords are not matched. Please try again.");
            PasswordResetForm changePwForm1 = new PasswordResetForm();
            model.addAttribute("changePwForm", changePwForm1);
            model.addAttribute("enableDiv", "password");
            return Endpoint.PAGE_MANAGE_ACCOUNT;
        }
        if (!isValidPassword(commonData.getNewPassword())) {
            logger.info("New PW not satisfied the required format");
            model.addAttribute("message", UserConstants.PASSWORD_CONSTRAINTS);
            PasswordResetForm changePwForm1 = new PasswordResetForm();
            model.addAttribute("changePwForm", changePwForm1);
            model.addAttribute("enableDiv", "password");
            return Endpoint.PAGE_MANAGE_ACCOUNT;
        }
        SysUser sysUser = new SysUser();
        sysUser.setUsername(userDetails.getUsername());
        sysUser.setPassword(commonData.getNewPassword());
        sysUser.setUpdatedBy(userDetails.getUsername());
        sysUser.setLastUpdate(DateUtil.getCurrentTime());
        usersService.changePW(sysUser);
        model.addAttribute("message", EventMessage.PASSWORD_CHANGED_SUCCESSFULLY);
        model.addAttribute("back_link", Endpoint.URL_MANAGE_ACCOUNT);
        return Endpoint.PAGE_OPERATION_SUCCESS;
    }

    @GetMapping(value = Endpoint.URL_REGISTER_USER)
    @PreAuthorize(UserRoleMapper.PRE_AUTH_ADMIN_OR_MANAGER)
    public String loadRegisterUserPage(HttpServletRequest request, Model model) {

        logger.info("load Manage Account page >>>>>>>>>>>>>>>>>>");
        // load login user
        LoginUserUtil.loadLoginUser(model);
        SessionHelper.removeFormInSession(request);
        UserForm userForm = new UserForm();
        model.addAttribute("userForm", userForm);
        model.addAttribute("roles", roleTypeService.getRoleTypes());
        model.addAttribute("groups", bankTypeService.getBankTypes());
        return Endpoint.PAGE_REGISTER_USER;
    }

    //Create new user
    @PostMapping(value = Endpoint.URL_SAVE_USER)
    @PreAuthorize(UserRoleMapper.PRE_AUTH_ADMIN_OR_MANAGER)
    public String registerNewUser(HttpServletRequest request, @ModelAttribute @Valid UserForm userForm,
                                  BindingResult result, Model model) throws GenericException {
        logger.info("Register New User >>>>>>>>>>>>>>>>>>");
        // load login user
        LoginUserUtil.loadLoginUser(model);

        SessionHelper.removeFormInSession(request);
        if (userForm == null) {

            return Endpoint.REDIRECT_NAME + Endpoint.URL_REGISTER_USER;
        }

        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        if (!userForm.getUserRole().isEmpty()) {
            if (!usersService.isUserAccountsAvailable(userForm.getUserRole())) {
                logger.info("User Account Max limit reached [" + userForm.getUserRole() + "]");
                model.addAttribute("message", UserConstants.MAX_USER_ACCOUNT_LIMIT_REACHED);
                model.addAttribute("back_link", Endpoint.URL_REGISTER_USER);
                return Endpoint.PAGE_OPERATION_FAIL;
            }
        }
        if ((userForm.getUsername().trim().isEmpty())) {
            model.addAttribute("message", UserConstants.USERNAME_IS_REQUIRED);
            model.addAttribute("userForm", userForm);
            model.addAttribute("roles", roleTypeService.getRoleTypes());
            model.addAttribute("groups", bankTypeService.getBankTypes());
            return Endpoint.PAGE_REGISTER_USER;
        }
        if (usersService.isUserExist(userForm.getUsername())) {
            model.addAttribute("message", UserConstants.USERNAME_NOT_AVAILABLE);
            model.addAttribute("userForm", userForm);
            model.addAttribute("roles", roleTypeService.getRoleTypes());
            model.addAttribute("groups", bankTypeService.getBankTypes());
            return Endpoint.PAGE_REGISTER_USER;
        }

        if (!userForm.getPassword().equals(userForm.getReEnterPassword())) {
            logger.info("New PWs are not Matched");
            model.addAttribute("message", UserConstants.PASSWORD_NOT_MATCH);
            model.addAttribute("userForm", userForm);
            model.addAttribute("roles", roleTypeService.getRoleTypes());
            model.addAttribute("groups", bankTypeService.getBankTypes());
            return Endpoint.PAGE_REGISTER_USER;
        }
        if ((userForm.getPassword().length() <= 3)) {
            logger.info("Password-length not enough [>3]");
            model.addAttribute("message", UserConstants.NOT_ENOUGH_LENGTH_FOR_TEMP_PASSWORD);
            model.addAttribute("userForm", userForm);
            model.addAttribute("roles", roleTypeService.getRoleTypes());
            model.addAttribute("groups", bankTypeService.getBankTypes());
            return Endpoint.PAGE_REGISTER_USER;
        }
        SysUser sysUser = convertUserFormtoSysUser(userForm);
        sysUser.setAddedBy(userDetails.getUsername());
        sysUser.setAddedDate(DateUtil.getCurrentTime());
        sysUser.setLastUpdate(DateUtil.getCurrentTime());
        sysUser.setNewUser(true);
        usersService.createUser(sysUser);
        model.addAttribute("message", EventMessage.USER_CREATED_SUCCESSFULLY);
        model.addAttribute("back_link", Endpoint.URL_REGISTER_USER);
        return Endpoint.PAGE_OPERATION_SUCCESS;
    }

    @GetMapping(value = Endpoint.URL_VIEW_ALL_USERS)
    public String loadViewUsersPage(HttpServletRequest request, Model model) {

        logger.info("load view all users page>>>>>>>>>>>>>");
        // load login user
        LoginUserUtil.loadLoginUser(model);

        SessionHelper.removeFormInSession(request);
        List<SysUser> users = usersService.findAllUsers();


        CommonData commonData = new CommonData();
        PasswordResetForm resetForm = new PasswordResetForm();
        model.addAttribute("commonData", commonData);
        model.addAttribute("users", users);
        model.addAttribute("resetForm", resetForm);
        model.addAttribute("tableStatus", ErrorCode.TABLE_DATA_NOT_FOUND);
        model.addAttribute("pageStatus", EventMessage.ALL_USERS);

        return Endpoint.PAGE_VIEW_USERS;
    }

    // Enable / disable user
    @GetMapping(value = Endpoint.URL_ENABLE_USER + "/{userId}/{actionType}")
    public String setUserEnable(HttpServletRequest request, @PathVariable("userId") Integer userId,
                                @PathVariable("actionType") String actionType, Model model) throws GenericException {
        if (userId == null || actionType == null) {
            return Endpoint.URL_VIEW_ALL_USERS;
        }

        logger.info("Enable User Status: [ " + userId + " | " + actionType + " ]");

        if (usersService.enableDisableUsers(userId, actionType)) {
            model.addAttribute("message", EventMessage.SUCCESSFULLY_UPDATED);
            model.addAttribute("back_link", Endpoint.URL_VIEW_ALL_USERS);
            return Endpoint.PAGE_OPERATION_SUCCESS;
        } else {
            logger.info("Error Occured");
            model.addAttribute("message", ErrorCode.SYSTEM_ERROR);
            model.addAttribute("back_link", Endpoint.URL_VIEW_ALL_USERS);
            return Endpoint.PAGE_OPERATION_FAIL;
        }
    }

    // Enable / disable user
    @GetMapping(value = Endpoint.URL_LOCK_USER + "/{userId}/{actionType}")
    public String setUserLock(HttpServletRequest request, @PathVariable("userId") Integer userId,
                              @PathVariable("actionType") String actionType, Model model) throws GenericException {
        if (userId == null || actionType == null) {
            return Endpoint.URL_VIEW_ALL_USERS;
        }

        logger.info("Lock User Status: [ " + userId + " | " + actionType + " ]");

        if (usersService.lockUnlockUsers(userId, actionType)) {
            model.addAttribute("message", EventMessage.SUCCESSFULLY_UPDATED);
            model.addAttribute("back_link", Endpoint.URL_VIEW_ALL_USERS);
            return Endpoint.PAGE_OPERATION_SUCCESS;
        } else {
            logger.info("Error Occured");
            model.addAttribute("message", ErrorCode.SYSTEM_ERROR);
            model.addAttribute("back_link", Endpoint.URL_VIEW_ALL_USERS);
            return Endpoint.PAGE_OPERATION_FAIL;
        }
    }

    private CommonUserData convertPasswordResetFormToCommonUserData(PasswordResetForm passwordResetForm) {
        CommonUserData cdata = new CommonUserData();
        cdata.setNewPassword(passwordResetForm.getNewPassword());
        cdata.setRenewPassword(passwordResetForm.getReNewPassword());
        cdata.setCurrentPassword(passwordResetForm.getCurrentPassword());
        cdata.setEmail(passwordResetForm.getEmail());
        if (!(passwordResetForm.getUserId() == null)) {
            cdata.setUserId(passwordResetForm.getUserId());
        }
        return cdata;
    }

    private SysUser convertUserFormtoSysUser(UserForm userForm) {
        SysUser sysUser = new SysUser();

        sysUser.setAccountNonLocked((userForm.isAccountNonLocked() == true ? false : true));
        sysUser.setActive(userForm.isActive());
        sysUser.setAddedBy(userForm.getAddedBy());
        sysUser.setAddedDate(userForm.getAddedDate());
        sysUser.setFailedAttempt(userForm.getFailedAttempt());
        sysUser.setFirstName(userForm.getFirstName());
        sysUser.setLastName(userForm.getLastName());
        sysUser.setLastUpdate(userForm.getLastUpdate());
        sysUser.setLockTime(userForm.getLockTime());
        sysUser.setNewUser(userForm.isNewUser());
        sysUser.setPassword(userForm.getPassword());
        sysUser.setUpdatedBy(userForm.getUpdatedBy());
        sysUser.setUserId(userForm.getUserId());
        sysUser.setUsername(userForm.getUsername());
        sysUser.setUserRole(userForm.getUserRole());
        sysUser.setUserGroup(userForm.getUserGroup());
        sysUser.setEmail(userForm.getEmail().trim());

        return sysUser;
    }

    // Function to validate the password.
    public boolean isValidPassword(String password) {
        // Compile the ReGex
        Pattern p = Pattern.compile(UserConstants.PASSWORD_PATTERN);

        if (password == null) {
            return false;
        }
        Matcher m = p.matcher(password);
        return m.matches();
    }
}
