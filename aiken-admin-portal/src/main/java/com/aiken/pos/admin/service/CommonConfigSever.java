package com.aiken.pos.admin.service;

import com.aiken.pos.admin.exception.GenericException;
import com.aiken.pos.admin.exception.ResourceNotFoundException;
import com.aiken.pos.admin.model.CommonConfig;
import com.aiken.pos.admin.repository.ConfigurationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

/**
 * @author Sajith Alahakoon
 * @version 1.0
 * @since 2024-12-03
 */
@Service
public class CommonConfigSever {

    @Autowired
    private ConfigurationRepository configurationRepository;

    private Logger logger = LoggerFactory.getLogger(DashboardService.class);

    public CommonConfig findConfig() throws GenericException {

        try {
            CommonConfig commonConfig = configurationRepository.findCommonConfig();
            return commonConfig;
        } catch (Exception e) {
            logger.error("Error: Data not found" + e);
            throw new RuntimeException(e);
        }

    }


    public void updateCommonConfiguragion(CommonConfig commonConfig) throws GenericException {

        try {
            configurationRepository.updateCommonConfig(commonConfig);

        } catch (EmptyResultDataAccessException e) {
            logger.error("Error:Update Not Found" + e.getMessage());
            throw new ResourceNotFoundException("Update Not Found");
        } catch (Exception e) {
            logger.error("Error: " + e);
            throw new GenericException("Error Finding Update ");
        }


    }
}
