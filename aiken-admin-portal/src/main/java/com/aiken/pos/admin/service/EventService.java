package com.aiken.pos.admin.service;

import com.aiken.common.util.validation.DateUtil;
import com.aiken.pos.admin.constant.EventStatus;
import com.aiken.pos.admin.constant.EventTypes;
import com.aiken.pos.admin.constant.TransactionTypes;
import com.aiken.pos.admin.exception.GenericException;
import com.aiken.pos.admin.exception.ResourceNotFoundException;
import com.aiken.pos.admin.model.Event;
import com.aiken.pos.admin.model.EventType;
import com.aiken.pos.admin.repository.EventRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
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
 * @since 2021-08-10
 */
@Service
public class EventService {

    private Logger logger = LoggerFactory.getLogger(EventService.class);

    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private EventTypeService eventTypeService;

    // save events
    @Transactional
    public Integer saveEvent(Event event) throws GenericException {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        event.setAddedDate(DateUtil.getCurrentTime());
        event.setAddedBy(userDetails.getUsername());
        event.setLastUpdate(DateUtil.getCurrentTime());
        event.setUpdatedBy(userDetails.getUsername());
        event.setStatus(EventStatus.NEW);
        List<EventType> eventTypeList = eventTypeService.getEventTypes();

        eventTypeList.forEach(m -> {
            if (m.getCode().equals(event.getEventType())) {
                event.setEventDesc(m.getName());
            }
        });

        if (event.getType().equals(TransactionTypes.TODAY_ALL)) {
            event.setReportFromDate(DateUtil.getCurrentTime().substring(0, 10));
            event.setReportToDate(DateUtil.getCurrentTime().substring(0, 10));
        }
        if (!event.getEventType().equals(EventTypes.ACTIVITY_REPORT)) {
            event.setType("");
        }
        logger.info("Event Save: " + event.getMerchantTransType());
        return eventRepository.insert(event);
    }

    @Transactional
    public Integer updateEvent(boolean isWeb, Event event) throws GenericException {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        event.setLastUpdate(DateUtil.getCurrentTime());
        event.setUpdatedBy(userDetails.getUsername());
        List<EventType> eventTypeList = eventTypeService.getEventTypes();
        if (isWeb) {
            if (event.getType().equals(TransactionTypes.TODAY_ALL)) {
                event.setReportFromDate(DateUtil.getCurrentTime().substring(0, 10));
                event.setReportToDate(DateUtil.getCurrentTime().substring(0, 10));
            }
            eventTypeList.forEach(m -> {
                if (m.getCode().equals(event.getEventType())) {
                    event.setEventDesc(m.getName());
                }
            });
            if (!event.getEventType().equals(EventTypes.ACTIVITY_REPORT)) {
                event.setType("");
            }
            event.setStatus(EventStatus.UPDATED);
        }
        return eventRepository.update(event);
    }

    // delete event
    @Transactional
    public void deleteEvent(Integer eventId) throws GenericException {
        // No response required
        eventRepository.deleteById(eventId);
    }

    // find all events
    @Transactional(readOnly = true)
    public List<Event> findAllEvents() {

        return eventRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Event> findAllEventsByDate(String fromDate, String toDate) {

        logger.info("findAllEventsByDate: fromDate:" + fromDate + " | toDate:" + toDate);

        List<String> dates = new ArrayList<String>();
        dates.add(fromDate);
        dates.add(toDate);

        return eventRepository.findAllByDates(dates);
    }

    @Transactional(readOnly = true)
    public List<Event> findAllEventsByParam(String key) {

        logger.info("find All Events By search key:" + key);
        return eventRepository.findAllByKey(key);
    }

    // find all events
    @Transactional(readOnly = true)
    public List<Event> findAllEventsByParams(String serialNo, String currentDateTime) throws GenericException {

        List<String> ids = new ArrayList<String>();
        ids.add(serialNo);
        ids.add(currentDateTime);

        return eventRepository.findAllById(ids);
    }

    public Event findEventbyId(Integer eventId) throws GenericException {

        try {
            Optional<Event> event = eventRepository.findById(eventId);
            if (event.isPresent()) {
                return event.get();
            } else {
                logger.warn("Event Not Found");
                throw new ResourceNotFoundException("Event Not Found with given Event ID: " + eventId);
            }
        } catch (EmptyResultDataAccessException e) {
            logger.error("Error: Device Not Found with given Serial No : " + e);
            throw new ResourceNotFoundException("Event Not Found with given eventId: " + eventId);
        } catch (Exception e) {
            logger.error("Error: " + e);
            throw new GenericException("Error Finding Event by eventId");
        }

    }

    /*
     * public Optional<Event> findEventById(Integer eventId) throws GenericException
     * { return eventRepository.findById(eventId); }
     */

    public boolean existsById(Integer eventId) throws GenericException {
        return eventRepository.existsById(eventId);
    }

    public boolean existsByParams(Event event) throws GenericException {
        return eventRepository.existsByParams(event);
    }
}
