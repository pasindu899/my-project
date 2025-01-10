package com.aiken.pos.admin.service;

import com.aiken.pos.admin.api.dto.ActivityHistoryDto;
import com.aiken.pos.admin.api.dto.ActivityHistoryHeader;
import com.aiken.pos.admin.exception.GenericException;
import com.aiken.pos.admin.exception.ResourceNotFoundException;
import com.aiken.pos.admin.model.ActivityHistory;
import com.aiken.pos.admin.repository.ActivityHistoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * Activity History
 *
 * @author Chathuranga Dissanayake
 * @version 1.0
 * @since 2021-09-23
 */

@Service
public class ActivityHistoryService {

    private Logger logger = LoggerFactory.getLogger(ActivityHistoryService.class);

    @Autowired
    private ActivityHistoryRepository activityHistoryRepository;

    // save activity history
    @Transactional
    public void saveActivityHistory(ActivityHistoryHeader data) throws GenericException {
        logger.info("Save: " + data.getData().get(0).getSerialNo() );
        data.getData().forEach(dto -> {
			ActivityHistory activityHistory = convertDtoToModel(dto);
            logger.info("Save activityHistory Data: " + activityHistory.getSerialNo()  );
            List<String> params=new ArrayList<String>();
            params.add(activityHistory.getSerialNo());
            params.add(activityHistory.getType());
            params.add(activityHistory.getDate());
            params.add(activityHistory.getTime());
            activityHistoryRepository.deleteByParams(params);
            activityHistoryRepository.insert(activityHistory);
        });
    }

    private ActivityHistory convertDtoToModel(ActivityHistoryDto dto) {
        //date format
        SimpleDateFormat currentFormat = new SimpleDateFormat("yyyyMMdd");
        SimpleDateFormat requiredFormat = new SimpleDateFormat("yyyy-MM-dd");
        //time format
        SimpleDateFormat currentTimeFormat = new SimpleDateFormat("hhmmss");
        SimpleDateFormat requiredTimeFormat = new SimpleDateFormat("hh:mm:ss");

        ActivityHistory activityHistory = new ActivityHistory();
        activityHistory.setSerialNo(dto.getSerialNo());
        activityHistory.setType(dto.getTyp());
        activityHistory.setStep(dto.getStep());
        activityHistory.setDesc(dto.getDesc());
        activityHistory.setAction(dto.getActn());
        activityHistory.setStatus(dto.getStatus());
        try {
            activityHistory.setDate(requiredFormat.format(currentFormat.parse(dto.getDate())));
            activityHistory.setTime(requiredTimeFormat.format(currentTimeFormat.parse(dto.getTime())));
        } catch (Exception e) {
            e.printStackTrace();
        }
        activityHistory.setIsData("N");
        return activityHistory;
    }

    // get activity history by id
    @Transactional(readOnly = true)
    public ActivityHistory findActivityHistoryById(Long activityHistoryId) throws Exception {

        Optional<ActivityHistory> activityHistory = activityHistoryRepository.findById(activityHistoryId);
        if (activityHistory.isPresent()) {
            return activityHistory.get();
        } else {
            logger.warn("Activity Not Found");
            throw new ResourceNotFoundException("Activity Not Found with given Id: " + activityHistoryId);
        }
    }

    // get activity history by serial no
    @Transactional(readOnly = true)
    public List<ActivityHistory> findAllBySerialNo(String serialNo) throws Exception {
        return activityHistoryRepository.findAllById(Arrays.asList(serialNo));
    }

    @Transactional
    public void deleteAll() {

    	activityHistoryRepository.deleteAll();
    }

    // find all activities history
    @Transactional(readOnly = true)
    public List<ActivityHistory> findAllActivities() {

    	return activityHistoryRepository.findAll();
    }

}
