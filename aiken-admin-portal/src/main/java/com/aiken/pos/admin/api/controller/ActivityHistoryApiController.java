package com.aiken.pos.admin.api.controller;

import com.aiken.common.util.validation.StringUtil;
import com.aiken.pos.admin.api.dto.ActivityHistoryHeader;
import com.aiken.pos.admin.config.UserRoleMapper;
import com.aiken.pos.admin.constant.Endpoint;
import com.aiken.pos.admin.model.ActivityHistory;
import com.aiken.pos.admin.service.ActivityHistoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Activity History Controller
 *
 * @author Chathuranga Dissanayake
 * @version 1.0
 * @since 2021-09-23
 */

@RestController
@RequestMapping(value = Endpoint.URL_ACTIVITY)
public class ActivityHistoryApiController {

	private Logger logger = LoggerFactory.getLogger(ActivityHistoryService.class);

	@Autowired
	private ActivityHistoryService activityHistoryService;

	@PostMapping
	@PreAuthorize(UserRoleMapper.PRE_AUTH_ADMIN_OR_POS_USER)
	public ResponseEntity<String> saveActivityHistory(@Valid @RequestBody ActivityHistoryHeader data) throws Exception {

		// not found
		if (data == null || data.getData() == null || data.getData().isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		activityHistoryService.saveActivityHistory(data);

		return new ResponseEntity<String>("SUCCESS", HttpStatus.OK);
	}

	@GetMapping("/{serialNo}")
	@PreAuthorize(UserRoleMapper.PRE_AUTH_ADMIN_OR_USER)
	public ResponseEntity<List<ActivityHistory>> getAllBySerialNo(@PathVariable("serialNo") String serialNo)
			throws Exception {

		if (StringUtil.isNullOrWhiteSpace(serialNo)) {
			// invalid params
			return new ResponseEntity<List<ActivityHistory>>(HttpStatus.BAD_REQUEST);
		} else {
			List<ActivityHistory> data = activityHistoryService.findAllBySerialNo(serialNo);
			return new ResponseEntity<List<ActivityHistory>>(data, HttpStatus.OK);
		}
	}
}
