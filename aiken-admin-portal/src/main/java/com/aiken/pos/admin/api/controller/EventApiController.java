package com.aiken.pos.admin.api.controller;


import com.aiken.common.util.validation.StringUtil;
import com.aiken.pos.admin.api.dto.CustomEventDto;
import com.aiken.pos.admin.api.dto.EventResponseDto;
import com.aiken.pos.admin.config.UserRoleMapper;
import com.aiken.pos.admin.constant.Endpoint;
import com.aiken.pos.admin.model.Event;
import com.aiken.pos.admin.service.EventService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;


/**
 * Event API Controller
 *
 * @author Asela Liyanage
 * @version 1.0
 * @since 2021-08-10
 */
@RestController
@RequestMapping(value = Endpoint.URL_EVENTS)
public class EventApiController {

    private Logger logger = LoggerFactory.getLogger(EventApiController.class);

    @Autowired
    private EventService eventService;

    // Check availability of the new updates
    @GetMapping("/{serialNo}/{dateTime}")
    @PreAuthorize(UserRoleMapper.PRE_AUTH_ADMIN_OR_POS_USER)
    public ResponseEntity<EventResponseDto> getEvents(@PathVariable("serialNo") String serialNo,
                                                      @PathVariable("dateTime") String dateTime) throws Exception {

        if (StringUtil.isNullOrWhiteSpace(serialNo) || StringUtil.isNullOrWhiteSpace(dateTime)) {
            // invalid params
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        List<Event> events = eventService.findAllEventsByParams(serialNo.trim(), dateTime.trim());
        // not found
        if (events == null || events.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(packEventObject(events, serialNo), HttpStatus.OK);
    }

    @PutMapping
    @PreAuthorize(UserRoleMapper.PRE_AUTH_ADMIN_OR_POS_USER)
    public ResponseEntity<EventResponseDto> updateEventStatus(@Valid @RequestBody EventResponseDto eventResponseDto) throws Exception {
        logger.info("Serial No:" + eventResponseDto.getSerialNo());
        logger.info("Event Time:" + eventResponseDto.getDateTime());

        if (eventResponseDto == null
                || StringUtil.isNullOrWhiteSpace(eventResponseDto.getSerialNo())
                || StringUtil.isNullOrWhiteSpace(eventResponseDto.getDateTime())
                || eventResponseDto.getEvents().isEmpty()) {
            logger.warn("Not Found");
            return new ResponseEntity<EventResponseDto>(HttpStatus.BAD_REQUEST);

        } else {
            List<Event> events = new ArrayList<Event>();
            for (CustomEventDto dto : eventResponseDto.getEvents()) {
                logger.info("Event ID:" + dto.getEventId());
                if (eventService.existsById(dto.getEventId())) {
                    Event event = new Event(dto.getEventId(), dto.getStatus());
                    // call to one by one
                    eventService.updateEvent(false, event);
                    events.add(eventService.findEventbyId(dto.getEventId()));
                    logger.info("Event Updated Successfully");
                } else {
                    logger.warn("Event Not Found with given ID");
                }
            }

            return new ResponseEntity<EventResponseDto>(packEventObject(events, eventResponseDto.getSerialNo()), HttpStatus.OK);
        }
    }

    private EventResponseDto packEventObject(List<Event> events, String serialNo) {
        List<CustomEventDto> response = new ArrayList<>();
        events.forEach(event -> response.add(new CustomEventDto(event.getEventId(), event.getEventType(), event.getStatus(), event.getExecuteType())));

        EventResponseDto responseDto = new EventResponseDto();
        responseDto.setSerialNo(serialNo);
        responseDto.setEvents(response);

        return responseDto;
    }
}
