package com.aiken.pos.admin.model;

/**
 * @author Sajith Alahakoon
 * @version 1.0
 * @since 2024-12-03
 */
public class CommonConfig {

    private boolean profileValidation;

    public CommonConfig() {

    }

    public boolean isProfileValidation() {
        return profileValidation;
    }

    public void setProfileValidation(boolean profileValidation) {
        this.profileValidation = profileValidation;
    }
}
