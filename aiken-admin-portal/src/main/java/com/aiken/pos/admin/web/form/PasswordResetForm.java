/**
 *
 */
package com.aiken.pos.admin.web.form;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author Nandana Basnayake
 *
 * @created at Nov 6 2021
 */
public class PasswordResetForm {

    private String newPassword;
    private String reNewPassword;
    private String currentPassword;
    @JsonIgnore
    private Integer userId;
    private String email;

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getReNewPassword() {
        return reNewPassword;
    }

    public void setReNewPassword(String reNewPassword) {
        this.reNewPassword = reNewPassword;
    }

    public String getCurrentPassword() {
        return currentPassword;
    }

    public void setCurrentPassword(String currentPassword) {
        this.currentPassword = currentPassword;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
