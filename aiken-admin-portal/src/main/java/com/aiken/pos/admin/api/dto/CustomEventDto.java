package com.aiken.pos.admin.api.dto;

/**
 * Rest API DTO
 *
 * @author Asela Liyanage
 * @version 1.0
 * @since 2021-08-09
 */
public class CustomEventDto {

    private Integer eventId;
    private String eventType;
    private String status;
    private String executeType;

    public CustomEventDto() {

    }

    public CustomEventDto(Integer eventId, String eventType, String status, String executeType) {
        this.eventId = eventId;
        this.eventType = eventType;
        this.status = status;
        this.executeType = executeType;
    }

    public Integer getEventId() {
        return eventId;
    }

    public void setEventId(Integer eventId) {
        this.eventId = eventId;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getExecuteType() {
        return executeType;
    }

    public void setExecuteType(String executeType) {
        this.executeType = executeType;
    }
}
