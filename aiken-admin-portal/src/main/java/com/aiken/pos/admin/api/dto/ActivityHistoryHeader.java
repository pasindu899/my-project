package com.aiken.pos.admin.api.dto;

import java.util.List;

/**
 * Activity History Header
 *
 * @author Chathuranga Dissanayake
 * @version 1.0
 * @since 2021-09-22
 */

public class ActivityHistoryHeader {

	private List<ActivityHistoryDto> data;

	public List<ActivityHistoryDto> getData() {
		return data;
	}

	public void setData(List<ActivityHistoryDto> data) {
		this.data = data;
	}
	
}
