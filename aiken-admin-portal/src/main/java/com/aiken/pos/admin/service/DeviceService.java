package com.aiken.pos.admin.service;

import com.aiken.common.util.validation.DateUtil;
import com.aiken.pos.admin.constant.ActionType;
import com.aiken.pos.admin.constant.AppStatus;
import com.aiken.pos.admin.exception.GenericException;
import com.aiken.pos.admin.exception.ResourceNotFoundException;
import com.aiken.pos.admin.model.*;
import com.aiken.pos.admin.repository.ConfigSettingRepository;
import com.aiken.pos.admin.repository.DeviceRepository;
import com.aiken.pos.admin.web.form.OfflineUserForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Device Service
 *
 * @author Asela Liyanage
 * @version 1.0
 * @since 2021-01-29
 */

@Service
public class DeviceService {

    private Logger logger = LoggerFactory.getLogger(DeviceService.class);

    @Autowired
    private DeviceRepository deviceRepository;
    @Autowired
    private ConfigSettingRepository configSettingRepository;

    // save device
    @Transactional
    public Integer saveDevice(Device device) throws GenericException {
        Integer deviceId = null;

        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        device.setAction(ActionType.INSERT);
        device.setAddedDate(DateUtil.getCurrentTime());
        device.setAddedBy(userDetails.getUsername());
        device.setLastUpdate(DateUtil.getCurrentTime());
        device.setUpdatedBy(userDetails.getUsername());
        device.setStatus(AppStatus.DEVICE_STATUS_NEW_DEVICE);
        if (device.getProfiles() != null && !device.getProfiles().isEmpty()) {
            device.setProfile(true);
        }
        deviceId = deviceRepository.insert(device);

        return deviceId;
    }

    // edit device
    @Transactional
    public Integer editDevice(Device device) throws GenericException {

        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        device.setAction(ActionType.UPDATE);
        device.setLastUpdate(DateUtil.getCurrentTime());
        device.setUpdatedBy(userDetails.getUsername());
        device.setStatus(AppStatus.DEVICE_STATUS_DEVICE_UPDATED);
        Integer deviceId = deviceRepository.update(device);

        return deviceId;
    }

    // token update
    @Transactional
    public Integer modifyToken(Token token) {

        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Device device = new Device();
        device.setAction(ActionType.TOKEN_UPDATE);
        device.setLastUpdate(DateUtil.getCurrentTime());
        device.setUpdatedBy(userDetails.getUsername());
        device.setStatus(AppStatus.DEVICE_STATUS_TOKEN_UPDATE);

        device.setSerialNo(token.getSerialNo());
        device.setToken(token.getToken());

        Integer deviceId = deviceRepository.update(device);

        return deviceId;
    }

    // states change
    @Transactional
    public Integer modifyDeviceStatus(String serialNo) {

        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Device device = new Device();
        device.setSerialNo(serialNo);
        device.setAction(ActionType.STATE_UPDATE);
        device.setLastUpdate(DateUtil.getCurrentTime());
        device.setUpdatedBy(userDetails.getUsername());
        device.setStatus(AppStatus.DEVICE_STATUS_SYNC_WITH_SERVER);

        Integer deviceId = deviceRepository.update(device);

        return deviceId;
    }

    // states change
    @Transactional
    public Integer modifyDeviceStatus(String serialNo, String status) {

        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Device device = new Device();
        device.setSerialNo(serialNo);
        device.setAction(ActionType.STATE_UPDATE);
        device.setLastUpdate(DateUtil.getCurrentTime());
        device.setUpdatedBy(userDetails.getUsername());
        device.setStatus(status);

        Integer deviceId = deviceRepository.update(device);

        return deviceId;
    }

    // get device by serial no
    @Transactional(readOnly = true)
    public Device findDeviceBySerial(String serialNo) throws Exception {

        try {
            Optional<Device> device = deviceRepository.findByKey(serialNo);
            if (device.isPresent()) {
                return device.get();
            } else {
                logger.warn("Device Not Found");
                throw new ResourceNotFoundException("Devie Not Found with given Serial No: " + serialNo);
            }
        } catch (EmptyResultDataAccessException e) {
            logger.error("Error: Device Not Found with given Serial No : {}", e.getMessage());
            throw new ResourceNotFoundException("Device Not Found with given Serial No: " + serialNo);
        } catch (Exception e) {
            logger.error("Error: {}", e.getMessage());
            throw new GenericException("Error Finding Device by Serial No");
        }
    }

    // get device by id
    @Transactional(readOnly = true)
    public Device findDeviceById(Integer deviceId) throws Exception {

        try {
            Optional<Device> device = deviceRepository.findById(deviceId);
            if (device.isPresent()) {
                return device.get();
            } else {
                logger.warn("Device Not Found");
                throw new ResourceNotFoundException("Device Not Found with given Device Id: " + deviceId);
            }
        } catch (EmptyResultDataAccessException e) {
            logger.error("Error: Device Not Found with given Serial No : {}", e.getMessage());
            throw new ResourceNotFoundException("Device Not Found with given Device Id: " + deviceId);
        } catch (Exception e) {
            logger.error("Error: {}", e.getMessage());
            throw new GenericException("Error Finding Device by Device Id");
        }
    }

    // find all devices
    @Transactional(readOnly = true)
    public List<Device> findAllDevices() {

        return deviceRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Device> findAllDevicesForTidOrMid(String mid, String tid) {
        return deviceRepository.findAllForGivenTidOrMid(mid, tid);
    }

    @Transactional(readOnly = true)
    public List<Device> findAllDevicesByDates(String fromDate, String toDate) {

        logger.info("findAllDevicesByDate: fromDate: {} | toDate: {}", fromDate, toDate);

        List<String> dates = new ArrayList<String>();
        dates.add(fromDate);
        dates.add(toDate);

        return deviceRepository.findAllByDates(dates);
    }

    public List<Device> findAllDevicesAddedOnToday(String fromDate, String toDate) {

        logger.info("findAllDevicesByDate: fromDate: {} | toDate: {}", fromDate, toDate);

        List<String> dates = new ArrayList<String>();
        dates.add(fromDate);
        dates.add(toDate);

        return deviceRepository.findAllAddedByTodays(dates);
    }

    @Transactional(readOnly = true)
    public List<Device> findAllByParam(String param) {

        logger.info("findAllByParam: Key: {}", param);


        return deviceRepository.findAllByParam(param);
    }

    // delete device
    @Transactional
    public void deleteDevice(Integer deviceId) {

        Optional<Device> device = deviceRepository.findById(deviceId);
        if (device.isPresent()) {
            Optional<ConfigSettingData> configSettingData = configSettingRepository.findByKey(device.get().getSerialNo());
            if (configSettingData.isPresent()) {
                //delete configuration settings
                String result = configSettingRepository.deleteByKey(device.get().getSerialNo());
                if (Integer.parseInt(result) <= 0) {
                    logger.info("Fail to delete the setting configuration data - deviceService ");
                    throw new BadCredentialsException("Fail to delete the setting configuration data");
                }
            }
            // No response required
            deviceRepository.deleteById(deviceId);
        } else {
            logger.info("No device in given device id - deviceService");
            throw new BadCredentialsException("No device in given device id");
        }

    }

    public List<Profile> findProfileByDeviceId(int deviceId) {
        return deviceRepository.findProfileById(deviceId);
    }

    // delete device-profile
    @Transactional
    public void deleteDeviceProfile(Integer profileId) throws GenericException {
        // No response required
        deviceRepository.deleteProfileById(profileId);
    }

    @Transactional
    public Integer getMaxSequence() {
        return deviceRepository.getMaxSeqValue();
    }

    public Integer syncProfile(Device device) throws GenericException {
        return deviceRepository.syncProfiles(device);
    }

    public boolean syncDeviceComData(DeviceComStatus deviceComStatus) throws GenericException {

        return deviceRepository.updateComStatus(deviceComStatus);
    }

    public List<OfflineUserForm> findAllOfflineUser() {
        return deviceRepository.findAllOfflineUser();
    }
}
