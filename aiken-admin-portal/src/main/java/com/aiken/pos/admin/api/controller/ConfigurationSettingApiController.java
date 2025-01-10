package com.aiken.pos.admin.api.controller;

import com.aiken.pos.admin.api.dto.configdtos.ConfigurationSettingDto;
import com.aiken.pos.admin.api.dto.configdtos.SaveConfigurationDto;
import com.aiken.pos.admin.config.UserRoleMapper;
import com.aiken.pos.admin.constant.Endpoint;
import com.aiken.pos.admin.service.ConfigurationService;
import com.aiken.pos.admin.util.response.GenericResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.http.HttpStatus.OK;

/**
 * @author Sajith Alahakoon
 * @version aiken-admin-portal / 1.0
 * @since 2024-October-10
 */

@RestController
@RequestMapping(value = Endpoint.URL_CONFIG_SETTING)
public class ConfigurationSettingApiController {
    private final ConfigurationService configurationService;

    public ConfigurationSettingApiController(ConfigurationService configurationService) {
        this.configurationService = configurationService;
    }

    @PostMapping(value = "/{serialNo}")
    @PreAuthorize(UserRoleMapper.PRE_AUTH_ADMIN_OR_POS_USER)
    public ResponseEntity<GenericResponse> saveConfigurationData(@Valid @RequestBody SaveConfigurationDto saveConfigurationDto,
                                                                 @PathVariable("serialNo") String serialNumber) {

        ConfigurationSettingDto dto = new ConfigurationSettingDto();
        dto.setSaveConfigurationDto(saveConfigurationDto);
        dto.setSerialNo(serialNumber);

        //save configuration setting
        Integer result = configurationService.saveConfigSetting(dto);
        if (result > 0) {
            return new ResponseEntity<>(new GenericResponse.Builder()
                    .withCode(OK.value()).withResponse(OK.getReasonPhrase())
                    .withDescription("configuration setting save successfully")
                    .withType("default").build(), OK);

        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }


    }
}
