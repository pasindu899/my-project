package com.aiken.pos.admin.service;

import com.aiken.common.util.validation.StringUtil;
import com.aiken.pos.admin.api.dto.configdtos.ConfigurationSettingDto;
import com.aiken.pos.admin.api.dto.configdtos.SaveConfigurationDto;
import com.aiken.pos.admin.model.ConfigSettingData;
import com.aiken.pos.admin.model.Device;
import com.aiken.pos.admin.repository.ConfigSettingRepository;
import com.aiken.pos.admin.repository.DeviceRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author Sajith Alahakoon
 * @version aiken-admin-portal / 1.0
 * @since 2024-October-10
 */

@Service
public class ConfigurationService {
    private final Logger logger = LoggerFactory.getLogger(ConfigurationService.class);
    private final ConfigSettingRepository configSettingRepository;
    private final DeviceRepository deviceRepository;

    public ConfigurationService(ConfigSettingRepository configSettingRepository, DeviceRepository deviceRepository) {
        this.configSettingRepository = configSettingRepository;
        this.deviceRepository = deviceRepository;
    }

    public Integer saveConfigSetting(ConfigurationSettingDto dto) throws IllegalArgumentException {

        if (dto == null || dto.getSaveConfigurationDto() == null || StringUtil.isNullOrWhiteSpace(dto.getSerialNo())) {
            logger.info("configuration dto or serial number should not be null or empty");
            throw new BadCredentialsException(" configuration data or serial number should not be null ");
        }

        Device device = new Device();
        device.setSerialNo(dto.getSerialNo());
        if (!deviceRepository.existsByParams(device)) {
            logger.info("Invalid serial number or device not belong to this serial number");
            throw new BadCredentialsException("Invalid serial number or device not exists");
        }

        Optional<ConfigSettingData> settingDto = configSettingRepository.findByKey(dto.getSerialNo());

        ConfigSettingData configSettingData = new ConfigSettingData();
        //get user details
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Integer result;

        if (!settingDto.isPresent()) {
            configSettingData.setAddedBy(userDetails.getUsername());
            configSettingData.setUpdatedBy(userDetails.getUsername());
            configSettingData.setSerialNo(dto.getSerialNo());
            //convert json to string
            String settingData = new Gson().toJson(dto.getSaveConfigurationDto());
            configSettingData.setSettingData(settingData);

            //save configuration setting data
            result = configSettingRepository.insert(configSettingData);

        } else {
            configSettingData.setAddedBy(settingDto.get().getAddedBy());
            configSettingData.setAddedDate(settingDto.get().getAddedDate());
            configSettingData.setUpdatedBy(userDetails.getUsername());
            configSettingData.setSerialNo(settingDto.get().getSerialNo());
            //convert json to string
            String settingData = new Gson().toJson(dto.getSaveConfigurationDto());
            configSettingData.setSettingData(settingData);

            //update configuration setting data
            result = configSettingRepository.update(configSettingData);
        }
        return result;

    }

    public SaveConfigurationDto findConfigSetting(String serialNo) throws JsonProcessingException {

        if (StringUtil.isNullOrWhiteSpace(serialNo)) {
            logger.info("device serial number should not be null or empty - ConfigurationService ");
            throw new BadCredentialsException(" device serial number should not be null or empty");
        }

        Optional<ConfigSettingData> dto = configSettingRepository.findByKey(serialNo);

        if (!dto.isPresent()) {
            logger.info("No configuration setting data to view ");
            throw new BadCredentialsException(" No settings to view");
        }

        ObjectMapper mapper = new ObjectMapper();
        //return SaveConfigurationDto
        return mapper.readValue(dto.get().getSettingData(), new TypeReference<SaveConfigurationDto>() {
        });

    }

}
