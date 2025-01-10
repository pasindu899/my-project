package com.aiken.pos.admin.web.form;

import com.aiken.pos.admin.model.ProfileMerchant;

import java.util.ArrayList;
import java.util.List;

/**
 * Form Info POJO
 *
 * @author Asela Liyanage
 * @version 1.0
 * @since 2021-01-27
 */
public class FormInfo {

    private int rowNo = 0;
    private List<MerchantForm> merchants = new ArrayList<>(0);
    private DeviceForm deviceForm = new DeviceForm();
    private EventForm eventForm = new EventForm();
    private List<ProfileForm> profiles = new ArrayList<>(0);
    private List<ProfileMerchant> profileMerchants = new ArrayList<>(0);
    private UserForm userForm = new UserForm();
    private ProfileForm profileForm = new ProfileForm();
    private List<BinConfigForm> merchantBin = new ArrayList<>(0);
    private CommonDataForm commonDataForm = new CommonDataForm();
    private Integer selectedMerchantID;
    private CommonConfigForm commonConfigForm = new CommonConfigForm();
    private List<OfflineUserForm> offlineUser = new ArrayList<>(0);

    private boolean diffSale;

    public ProfileForm getProfileForm() {
        return profileForm;
    }

    public void setProfileForm(ProfileForm profileForm) {
        this.profileForm = profileForm;
    }

    public List<MerchantForm> getMerchants() {
        return merchants;
    }

    public int getRowNo() {
        return rowNo;
    }

    public void setRowNo(int rowNo) {
        this.rowNo = rowNo;
    }

    public DeviceForm getDeviceForm() {
        return deviceForm;
    }

    public void setDeviceForm(DeviceForm deviceForm) {
        this.deviceForm = deviceForm;
    }

    public void setMerchants(List<MerchantForm> merchants) {
        this.merchants = merchants;
    }

    public EventForm getEventForm() {
        return eventForm;
    }

    public void setEventForm(EventForm eventForm) {
        this.eventForm = eventForm;
    }

    public List<ProfileForm> getProfiles() {
        return profiles;
    }

    public void setProfiles(List<ProfileForm> profiles) {
        this.profiles = profiles;
    }

    public List<ProfileMerchant> getProfileMerchants() {
        return profileMerchants;
    }

    public void setProfileMerchants(List<ProfileMerchant> profileMerchants) {
        this.profileMerchants = profileMerchants;
    }

    public UserForm getUserForm() {
        return userForm;
    }

    public void setUserForm(UserForm userForm) {
        this.userForm = userForm;
    }

    public List<BinConfigForm> getMerchantBin() {
        return merchantBin;
    }

    public void setMerchantBin(List<BinConfigForm> merchantBin) {
        this.merchantBin = merchantBin;
    }

    public CommonDataForm getCommonDataForm() {
        return commonDataForm;
    }

    public void setCommonDataForm(CommonDataForm commonDataForm) {
        this.commonDataForm = commonDataForm;
    }

    public Integer getSelectedMerchantID() {
        return selectedMerchantID;
    }

    public void setSelectedMerchantID(Integer selectedMerchantID) {
        this.selectedMerchantID = selectedMerchantID;
    }

    public boolean isDiffSale() {
        return diffSale;
    }

    public void setDiffSale(boolean diffSale) {
        this.diffSale = diffSale;
    }

    public CommonConfigForm getCommonConfigForm() {
        return commonConfigForm;
    }

    public void setCommonConfigForm(CommonConfigForm commonConfigForm) {
        this.commonConfigForm = commonConfigForm;
    }

    public List<OfflineUserForm> getOfflineUser() {
        return offlineUser;
    }

    public void setOfflineUser(List<OfflineUserForm> offlineUser) {
        this.offlineUser = offlineUser;
    }
}