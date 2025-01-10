package com.aiken.pos.admin.api.dto;

import java.util.List;

/**
 *
 * @author Nandana Basnayake
 * @version 1.0
 */

public class EventResponseDto {

    private String serialNo;
    private String dateTime;
    private List<CustomEventDto> events;

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public List<CustomEventDto> getEvents() {
        return events;
    }

    public void setEvents(List<CustomEventDto> events) {
        this.events = events;
    }
}
