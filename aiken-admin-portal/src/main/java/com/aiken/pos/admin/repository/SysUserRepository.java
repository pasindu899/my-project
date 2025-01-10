package com.aiken.pos.admin.repository;

import com.aiken.pos.admin.exception.GenericException;
import com.aiken.pos.admin.model.SysUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * User Data Repository
 *
 * @author Asela Liyanage
 * @version 1.0
 * @modified Nandana Basnayake
 * @Date 2022-10-05
 * @since 2021-01-12
 */
@Repository
public class SysUserRepository {

    private Logger logger = LoggerFactory.getLogger(SysUserRepository.class);

    private final String FIND_ALL_USERS = "select * from public.sys_user Order BY user_role,username";
    private final String FIND_USER_BY_USERNAME = "select * from public.sys_user where username = :username";
    private final String FIND_USER_BY_USER_ID = "select * from public.sys_user where user_id = :user_id";
    private final String FIND_EXIST_USER_BY_USERNAME = "select exists (select from sys_user where upper(username) = upper(?))";
    private final String EXIST_USER_ACCOUNTS_FOR_USER_ROLE = "select exists (select * from sys_user where (select count(user_role) from sys_user where user_role= ?)< ?)";
    private final String INSERT_NEW_USER = "INSERT INTO public.sys_user"
            + " (username, password, user_role, active, first_name,last_name, added_date, added_by, last_update, updated_by, new_user,account_non_locked,user_group,user_email)"
            + " VALUES"
            + " (:username, :password, :user_role, :active, :first_name, :last_name, :added_date, :added_by, :last_update,:updated_by, :new_user,:account_non_locked,:user_group,:user_email)";

    private final String UPDATE_FAILED_ATTEMPT = "UPDATE  public.sys_user set failed_attempt=:failed_attempt where username=:username";

    private final String UPDATE_LOCK_USER = "UPDATE  public.sys_user set account_non_locked=:account_non_locked,"
            + " lock_time=:lock_time where username=:username";

    private final String UPDATE_FORGOT_PASWORD = "UPDATE  public.sys_user set req_reset=:req_reset,"
            + " req_reset_on=:req_reset_on,account_non_locked=:account_non_locked where username=:username";

    private final String UPDATE_UNLOCK_TIME_EXPIRED = "UPDATE  public.sys_user set account_non_locked=:account_non_locked,"
            + " lock_time=:lock_time, failed_attempt=:failed_attempt where username=:username";

    private final String UPDATE_RESET_PASSWORD = "UPDATE  public.sys_user set password=:password,last_update=:last_update,updated_by=:updated_by,"
            + " new_user=:new_user, req_reset =:req_reset, req_reset_on =:req_reset_on , user_email=:user_email where username=:username";

    private final String UPDATE_LOCK_STATUS = "UPDATE  public.sys_user set account_non_locked=:account_non_locked,"
            + " lock_time=:lock_time, failed_attempt=:failed_attempt,last_update=:last_update, updated_by = :updated_by where user_id=:user_id";

    private final String UPDATE_ENABLE_DISABLE_USER = "UPDATE  public.sys_user set active=:active,"
            + " last_update=:last_update, updated_by = :updated_by where user_id=:user_id";

    private final String UPDATE_USER_LOGI_LOGOUT = "UPDATE  public.sys_user set last_login=:last_login,"
            + " last_logout=:last_logout where user_id=:user_id";


    @Autowired
    private NamedParameterJdbcTemplate template;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public SysUser findByUsername(String username) {
        logger.info("################## START FIND USER BY USERNAME ##################");
        logger.info("UserName: " + username);
        try {
            return template.queryForObject(FIND_USER_BY_USERNAME,
                    new MapSqlParameterSource().addValue("username", username), (rs, rowNum) -> {
                        SysUser u = new SysUser();
                        u.setUserId(rs.getInt("user_id"));
                        u.setUsername(username);
                        u.setPassword(rs.getString("password"));
                        u.setUserRole(rs.getString("user_role"));
                        u.setActive(rs.getBoolean("active"));
                        u.setFirstName(rs.getString("first_name"));
                        u.setLastName(rs.getString("last_name"));
                        u.setLockTime(rs.getString("lock_time"));
                        u.setAccountNonLocked(rs.getBoolean("account_non_locked"));
                        u.setFailedAttempt(rs.getInt("failed_attempt"));
                        u.setNewUser(rs.getBoolean("new_user"));
                        u.setReqReset(rs.getBoolean("req_reset"));
                        u.setReqResetOn(rs.getString("req_reset_on"));
                        u.setUserGroup(rs.getString("user_group"));
                        return u;
                    });
        } catch (EmptyResultDataAccessException e2) {
            logger.error("Error Loading Sys User Login: " + e2);
        }
        return null;
    }

    public SysUser findByUserId(Integer userId) {
        logger.info("################## START FIND ALL USERS BY ID ##################");
        try {
            return template.queryForObject(FIND_USER_BY_USER_ID,
                    new MapSqlParameterSource().addValue("user_id", userId), (rs, rowNum) -> {
                        SysUser u = new SysUser();
                        u.setUserId(rs.getInt("user_id"));
                        u.setUsername(rs.getString("username"));
                        u.setPassword(rs.getString("password"));
                        u.setUserRole(rs.getString("user_role"));
                        u.setActive(rs.getBoolean("active"));
                        u.setFirstName(rs.getString("first_name"));
                        u.setLastName(rs.getString("last_name"));
                        u.setLockTime(rs.getString("lock_time"));
                        u.setAccountNonLocked(rs.getBoolean("account_non_locked"));
                        u.setFailedAttempt(rs.getInt("failed_attempt"));
                        u.setNewUser(rs.getBoolean("new_user"));
                        u.setReqReset(rs.getBoolean("req_reset"));
                        u.setReqResetOn(rs.getString("req_reset_on"));
                        u.setUserGroup(rs.getString("user_group"));
                        u.setLastLoginTime(rs.getString("last_login"));
                        u.setLastLogoutTime(rs.getString("last_logout"));
                        u.setEmail(rs.getString("user_email"));
                        return u;
                    });
        } catch (EmptyResultDataAccessException e2) {
            logger.error("Error Loading Sys User Login: " + e2);
            return null;
        }

    }

    public List<SysUser> findAllUsers() {
        logger.info("################## START FIND ALL USERS ##################");
        try {
            return template.query(FIND_ALL_USERS, (rs, rowNum) -> {
                SysUser u = new SysUser();
                u.setUserId(rs.getInt("user_id"));
                u.setUsername(rs.getString("username"));
                u.setPassword(rs.getString("password"));
                u.setUserRole(rs.getString("user_role"));
                u.setActive(rs.getBoolean("active"));
                u.setFirstName(rs.getString("first_name"));
                u.setLastName(rs.getString("last_name"));
                u.setLockTime(rs.getString("lock_time"));
                u.setAccountNonLocked(rs.getBoolean("account_non_locked"));
                u.setFailedAttempt(rs.getInt("failed_attempt"));
                u.setNewUser(rs.getBoolean("new_user"));
                u.setAddedDate(rs.getString("added_date"));
                u.setAddedBy(rs.getString("added_by"));
                u.setLastUpdate(rs.getString("last_update"));
                u.setUpdatedBy(rs.getString("updated_by"));
                u.setReqReset(rs.getBoolean("req_reset"));
                u.setReqResetOn(rs.getString("req_reset_on"));
                u.setUserGroup(rs.getString("user_group"));
                u.setEmail(rs.getString("user_email"));
                return u;
            });
        } catch (EmptyResultDataAccessException e2) {
            logger.error("Error Loading Sys User Login: " + e2);

        }
        logger.info("################## END FIND ALL USERS  ##################");
        return null;
    }

    @Transactional
    public Integer createUser(SysUser sysUser) throws GenericException {
        logger.info("################## START INSERT ##################");
        KeyHolder holder = new GeneratedKeyHolder();
        template.update(INSERT_NEW_USER,
                new MapSqlParameterSource()
                        .addValue("username", sysUser.getUsername())
                        .addValue("password", sysUser.getPassword())
                        .addValue("user_role", sysUser.getUserRole())
                        .addValue("active", sysUser.isActive())
                        .addValue("first_name", sysUser.getFirstName())
                        .addValue("last_name", sysUser.getLastName())
                        .addValue("added_date", sysUser.getAddedDate())
                        .addValue("added_by", sysUser.getAddedBy())
                        .addValue("last_update", sysUser.getLastUpdate())
                        .addValue("updated_by", sysUser.getUpdatedBy())
                        .addValue("new_user", sysUser.isNewUser())
                        .addValue("account_non_locked", sysUser.isAccountNonLocked())
                        .addValue("user_group", sysUser.getUserGroup())
                        .addValue("user_email", sysUser.getEmail()),
                holder, new String[]{"user_id"});
        logger.info("################## END INSERT ##################");
        return holder.getKey().intValue();
    }

    @Transactional
    public void updateFailedAttempts(int failAttempts, String username) throws GenericException {
        logger.info("################## START Update ##################");
        KeyHolder holder = new GeneratedKeyHolder();
        template.update(UPDATE_FAILED_ATTEMPT,
                new MapSqlParameterSource()
                        .addValue("username", username)
                        .addValue("failed_attempt", failAttempts),
                holder, new String[]{"user_id"});
        logger.info("################## END Update ##################");

    }

    @Transactional
    public void lockUser(SysUser user) throws GenericException {
        logger.info("################## START Update ##################");
        KeyHolder holder = new GeneratedKeyHolder();
        template.update(UPDATE_LOCK_USER,
                new MapSqlParameterSource()
                        .addValue("username", user.getUsername())
                        .addValue("account_non_locked", user.isAccountNonLocked())
                        .addValue("lock_time", user.getLockTime()),
                holder, new String[]{"user_id"});
        logger.info("################## END Update ##################");

    }

    @Transactional
    public void unlockWhenTimeExpired(SysUser user) throws GenericException {
        logger.info("################## START Update ##################");
        KeyHolder holder = new GeneratedKeyHolder();
        template.update(UPDATE_UNLOCK_TIME_EXPIRED,
                new MapSqlParameterSource()
                        .addValue("username", user.getUsername())
                        .addValue("account_non_locked", user.isAccountNonLocked())
                        .addValue("lock_time", user.getLockTime())
                        .addValue("failed_attempt", user.getFailedAttempt()),
                holder, new String[]{"user_id"});
        logger.info("################## END Update ##################");

    }

    @Transactional
    public void changePw(SysUser user) throws GenericException {
        logger.info("################## START Update -reset PW ##################");
        KeyHolder holder = new GeneratedKeyHolder();
        template.update(UPDATE_RESET_PASSWORD,
                new MapSqlParameterSource()
                        .addValue("username", user.getUsername())
                        .addValue("password", user.getPassword())
                        .addValue("last_update", user.getLastUpdate())
                        .addValue("updated_by", user.getUpdatedBy())
                        .addValue("new_user", user.isNewUser())
                        .addValue("req_reset", user.isReqReset())
                        .addValue("req_reset_on", user.getReqResetOn())
                        .addValue("user_email", user.getEmail()),
                holder, new String[]{"user_id"});
        logger.info("################## END Update ##################");

    }

    @Transactional
    public void lockUnlockUser(SysUser user) throws GenericException {
        logger.info("################## START Update -Lock/Unlock User ##################");
        KeyHolder holder = new GeneratedKeyHolder();
        template.update(UPDATE_LOCK_STATUS,
                new MapSqlParameterSource()
                        .addValue("user_id", user.getUserId())
                        .addValue("account_non_locked", user.isAccountNonLocked())
                        .addValue("last_update", user.getLastUpdate())
                        .addValue("updated_by", user.getUpdatedBy())
                        .addValue("lock_time", user.getLockTime())
                        .addValue("failed_attempt", user.getFailedAttempt()),
                holder, new String[]{"user_id"});
        logger.info("################## END Update ##################");
    }

    @Transactional
    public void enableDisableUser(SysUser user) throws GenericException {
        logger.info("################## START Update -Enable/Disable User ##################");
        KeyHolder holder = new GeneratedKeyHolder();
        template.update(UPDATE_ENABLE_DISABLE_USER,
                new MapSqlParameterSource()
                        .addValue("user_id", user.getUserId())
                        .addValue("active", user.isActive())
                        .addValue("last_update", user.getLastUpdate())
                        .addValue("updated_by", user.getUpdatedBy()),
                holder, new String[]{"user_id"});
        logger.info("################## END Update ##################");
    }

    @Transactional
    public void forgotPasswordResetRequest(SysUser user) throws GenericException {
        logger.info("################## START FORGOT PASWORD Update ##################");
        KeyHolder holder = new GeneratedKeyHolder();
        template.update(UPDATE_FORGOT_PASWORD,
                new MapSqlParameterSource()
                        .addValue("username", user.getUsername())
                        .addValue("req_reset", user.isReqReset())
                        .addValue("req_reset_on", user.getReqResetOn())
                        .addValue("account_non_locked", user.isAccountNonLocked()),
                holder, new String[]{"username"});
        logger.info("################## END Update ##################");

    }

    @Transactional
    public void updateUserLoginLogout(SysUser user) throws GenericException {
        logger.info("################## START Update -UserLogin ##################");
        KeyHolder holder = new GeneratedKeyHolder();
        template.update(UPDATE_USER_LOGI_LOGOUT,
                new MapSqlParameterSource()
                        .addValue("user_id", user.getUserId())
                        .addValue("last_login", user.getLastLoginTime())
                        .addValue("last_logout", user.getLastLogoutTime()),
                holder, new String[]{"user_id"});
        logger.info("################## END Update ##################");
    }

    public boolean existsByUsername(String userName) {
        return jdbcTemplate.queryForObject(FIND_EXIST_USER_BY_USERNAME, Boolean.class, userName);
    }

    public boolean isUserAccountsAvailable(String userRole, int userLimit) {
        return jdbcTemplate.queryForObject(EXIST_USER_ACCOUNTS_FOR_USER_ROLE, Boolean.class,
                new Object[]{
                        new String(userRole), userLimit,
                });
    }
}