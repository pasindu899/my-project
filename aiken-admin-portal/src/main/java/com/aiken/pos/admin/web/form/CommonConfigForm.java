package com.aiken.pos.admin.web.form;

/**
 * @author Sajith Alahakoon
 * @version 1.0
 * @since 2024-12-03
 */
public class CommonConfigForm {

    private boolean profileValidation;

    public CommonConfigForm() {
    }

    public CommonConfigForm(boolean profileValidation) {
        this.profileValidation = profileValidation;
    }

    public boolean isProfileValidation() {
        return profileValidation;
    }

    public void setProfileValidation(boolean profileValidation) {
        this.profileValidation = profileValidation;
    }
}
