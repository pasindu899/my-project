package com.aiken.pos.admin.repository;

import com.aiken.pos.admin.constant.ActionType;
import com.aiken.pos.admin.constant.EventStatus;
import com.aiken.pos.admin.model.Event;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Event Repository
 *
 * @author Asela Liyanage
 * @version 1.0
 * @since 2021-08-10
 */
@Repository
public class EventRepository implements GenericRepository<Event, Integer> {

    private Logger logger = LoggerFactory.getLogger(EventRepository.class);

    private final String INSERT_NEW_EVENT = "INSERT INTO public.event "
            + "(serial_no, event_type, from_date, to_date, added_date, added_by, last_update, updated_by, status,report_from_date,report_to_date,type,event_desc,merch_trans_type,batch_clear_merchant,execute_type,onetime_password)"
            + "	VALUES (:serial_no, :event_type, :from_date, :to_date, :added_date, :added_by, :last_update, :updated_by, :status,:report_from_date,:report_to_date,:type,:event_desc,:merch_trans_type,:batch_clear_merchant,:execute_type,:onetime_password)";

    private final String UPDATE_EVENT = "UPDATE public.event"
            + "	SET last_update = :last_update, updated_by = :updated_by, status = :status"
            + " where event_id = :event_id and status!='DONE'";
    private final String UPDATE_EVENT_BY_WEB = "UPDATE public.event"
            + "	SET last_update = :last_update, updated_by = :updated_by, status = :status,"
            + " event_type=:event_type, from_date=:from_date, to_date=:to_date, report_from_date=:report_from_date, report_to_date=:report_to_date,"
            + " type=:type, event_desc =:event_desc,merch_trans_type=:merch_trans_type,batch_clear_merchant=:batch_clear_merchant,execute_type=:execute_type,onetime_password=:onetime_password"
            + " where event_id = :event_id and status!='DONE'";

    private final String DELETE_EVENT = "delete from public.event where event_id = :event_id";
    private final String FIND_ALL_EVENTS = "select e.event_id, e.serial_no, e.event_type, e.from_date, e.to_date, e.added_date,"
            + " e.added_by, e.last_update,e.updated_by, e.status, e.report_from_date, e.report_to_date,d.merchant_name,e.type,e.event_desc,e.merch_trans_type,e.batch_clear_merchant,e.execute_type,e.onetime_password"
            + " FROM event e INNER JOIN device d ON e.serial_no = d.serial_no order by e.added_date DESC";

    private final String FIND_EVENT_BY_EVENT_ID = "select e.event_id, e.serial_no, e.event_type, e.from_date, e.to_date, e.added_date,"
            + "  e.added_by, e.last_update,e.updated_by, e.status, e.report_from_date, e.report_to_date,d.merchant_name, e.type,e.event_desc,e.merch_trans_type,e.batch_clear_merchant,e.execute_type,e.onetime_password"
            + "  FROM event e INNER JOIN device d ON e.serial_no = d.serial_no where e.event_id = :event_id";

    private final String FIND_EVENTS_BY_SERIAL_NO = "select * from public.event where serial_no = :serial_no";
    private final String EVENT_EXISTS_BY_EVENT_ID = "select exists(select from public.event where event_id = ?)";

    private final String FIND_EVENT_BY_PARAM = "select e.event_id, e.serial_no, e.event_type, e.from_date, e.to_date, e.added_date,"
            + " e.added_by, e.last_update,e.updated_by, e.status, e.report_from_date, e.report_to_date,d.merchant_name,e.type,e.event_desc,e.merch_trans_type,e.batch_clear_merchant,e.execute_type,e.onetime_password"
            + " FROM event e INNER JOIN device d ON e.serial_no = d.serial_no where"
            + " upper(e.serial_no) LIKE ('%' || upper(:key) || '%') or upper(e.event_type) LIKE ('%' || upper(:key) || '%')"
            + " or upper(e.status) LIKE ('%' || upper(:key) || '%') or upper(d.merchant_name) LIKE ('%' || upper(:key) || '%')";

    private final String FIND_EVENTS_BY_SERIAL_NO_AND_DATE = "select e.event_id, e.serial_no, e.event_type, e.from_date, e.to_date, e.added_date,"
            + " e.added_by, e.last_update,e.updated_by, e.status, e.report_from_date, e.report_to_date,d.merchant_name, e.type,e.event_desc,e.merch_trans_type,e.batch_clear_merchant,e.execute_type,e.onetime_password"
            + " FROM event e INNER JOIN device d ON e.serial_no = d.serial_no where e.serial_no = :serial_no"
            + " and TO_TIMESTAMP(:currentDate,'YYYY/MM/DD HH24:MI:SS') between TO_TIMESTAMP(e.from_date,'YYYY/MM/DD HH24:MI:SS') and"
            + " TO_TIMESTAMP(e.to_date,'YYYY/MM/DD HH24:MI:SS') and e.status!='DONE'";


    private final String FIND_EXIST_EVENT_BY_PARAMS = "select exists(select from public.event where serial_no=? AND event_id !=?  AND status !='DONE' AND"
            + " ((TO_TIMESTAMP(?,'YYYY/MM/DD HH24:MI:SS')>=TO_TIMESTAMP(from_date,'YYYY/MM/DD HH24:MI:SS') AND"
            + "	TO_TIMESTAMP(?,'YYYY/MM/DD HH24:MI:SS')<=TO_TIMESTAMP(to_date,'YYYY/MM/DD HH24:MI:SS')) OR"
            + "	(TO_TIMESTAMP(?,'YYYY/MM/DD HH24:MI:SS')<=TO_TIMESTAMP(to_date,'YYYY/MM/DD HH24:MI:SS') AND"
            + "	TO_TIMESTAMP(?,'YYYY/MM/DD HH24:MI:SS')>=TO_TIMESTAMP(from_date,'YYYY/MM/DD HH24:MI:SS'))) AND"
            + "(CASE WHEN (? = 'AUTO_SETTLE') OR (? = 'FORCE_SETTLE') OR (? = 'NO_SETTLE') THEN "
            + " ((event_type = 'AUTO_SETTLE') OR (event_type = 'FORCE_SETTLE') OR (event_type = 'NO_SETTLE')) ELSE event_type=? END))";

    private final String FIND_EXIST_EVENT_BY_SERIAL_NO = "select exists(select from public.event where serial_no=?  AND status!='DONE' AND"
            + " ((TO_TIMESTAMP(?,'YYYY/MM/DD HH24:MI:SS')>=TO_TIMESTAMP(from_date,'YYYY/MM/DD HH24:MI:SS') and"
            + "	TO_TIMESTAMP(?,'YYYY/MM/DD HH24:MI:SS')<=TO_TIMESTAMP(to_date,'YYYY/MM/DD HH24:MI:SS')) or"
            + "	(TO_TIMESTAMP(?,'YYYY/MM/DD HH24:MI:SS')<=TO_TIMESTAMP(to_date,'YYYY/MM/DD HH24:MI:SS') and"
            + "	TO_TIMESTAMP(?,'YYYY/MM/DD HH24:MI:SS')>=TO_TIMESTAMP(from_date,'YYYY/MM/DD HH24:MI:SS'))) AND"
            + "(CASE WHEN (? = 'AUTO_SETTLE') OR (? = 'FORCE_SETTLE') OR (? = 'NO_SETTLE') THEN "
            + " ((event_type = 'AUTO_SETTLE') OR (event_type = 'FORCE_SETTLE') OR (event_type = 'NO_SETTLE')) ELSE event_type=? END))";

    private final String FIND_EVENT_BY_DATE = "select e.event_id, e.serial_no, e.event_type, e.from_date, e.to_date, e.added_date,"
            + "  e.added_by, e.last_update,e.updated_by, e.status, e.report_from_date, e.report_to_date,d.merchant_name, e.type,e.event_desc,e.merch_trans_type,e.batch_clear_merchant,e.execute_type,e.onetime_password"
            + "  FROM event e INNER JOIN device d ON e.serial_no = d.serial_no where"
            + " (TO_TIMESTAMP(e.from_date,'YYYY/MM/DD') between (TO_TIMESTAMP(:from_date,'YYYY/MM/DD')) and (TO_TIMESTAMP(:to_date,'YYYY/MM/DD')))"
            + " or"
            + " (TO_TIMESTAMP(e.to_date,'YYYY/MM/DD') between TO_TIMESTAMP(:from_date,'YYYY/MM/DD') and (TO_TIMESTAMP(:to_date,'YYYY/MM/DD')))"
            + " order by e.added_date DESC";

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Integer insert(Event event) throws IllegalArgumentException {

        logger.info("################## START INSERT ##################");

        KeyHolder holder = new GeneratedKeyHolder();

        namedParameterJdbcTemplate.update(INSERT_NEW_EVENT,
                new MapSqlParameterSource()
                        .addValue("serial_no", event.getSerialNo())
                        .addValue("event_type", event.getEventType())
                        .addValue("from_date", event.getFromDate())
                        .addValue("to_date", event.getToDate())
                        .addValue("added_date", event.getAddedDate())
                        .addValue("added_by", event.getAddedBy())
                        .addValue("last_update", event.getLastUpdate())
                        .addValue("updated_by", event.getUpdatedBy())
                        .addValue("status", event.getStatus())
                        .addValue("report_from_date", event.getReportFromDate())
                        .addValue("report_to_date", event.getReportToDate())
                        .addValue("type", event.getType())
                        .addValue("event_desc", event.getEventDesc())
                        .addValue("merch_trans_type", event.getMerchantTransType())
                        .addValue("batch_clear_merchant", event.getClearMerchant())
                        .addValue("execute_type", event.getExecuteType())
                        .addValue("onetime_password", event.getOnetimePassword()),
                holder, new String[]{"event_id"});
        logger.info("merch_trans_type :" + event.getMerchantTransType());
        logger.info("################## END INSERT ##################");

        return holder.getKey().intValue();

    }

    @Override
    public List<Integer> insertAll(List<Event> models) throws IllegalArgumentException {
        return null;
    }

    @Override
    public Integer update(Event event) throws IllegalArgumentException {

        if (event.getStatus().equals(EventStatus.UPDATED)) {
            namedParameterJdbcTemplate.update(UPDATE_EVENT_BY_WEB,
                    new MapSqlParameterSource().addValue("last_update", event.getLastUpdate())
                            .addValue("updated_by", event.getUpdatedBy())
                            .addValue("event_id", event.getEventId())
                            .addValue("status", event.getStatus())
                            .addValue("event_type", event.getEventType())
                            .addValue("from_date", event.getFromDate())
                            .addValue("to_date", event.getToDate())
                            .addValue("report_from_date", event.getReportFromDate())
                            .addValue("report_to_date", event.getReportToDate())
                            .addValue("type", event.getType())
                            .addValue("event_desc", event.getEventDesc())
                            .addValue("merch_trans_type", event.getMerchantTransType())
                            .addValue("batch_clear_merchant", event.getClearMerchant())
                            .addValue("execute_type", event.getExecuteType())
                            .addValue("onetime_password", event.getOnetimePassword()));

            return event.getEventId();
        }

        namedParameterJdbcTemplate.update(UPDATE_EVENT,
                new MapSqlParameterSource().addValue("last_update", event.getLastUpdate())
                        .addValue("updated_by", event.getUpdatedBy())
                        .addValue("status", event.getStatus())
                        .addValue("event_id", event.getEventId()));

        return event.getEventId();
    }

    @Override
    public List<Integer> updateAll(List<Event> models) throws IllegalArgumentException {
        return null;
    }

    @Override
    public Integer deleteById(Integer eventId) throws IllegalArgumentException {
        namedParameterJdbcTemplate.update(DELETE_EVENT, new MapSqlParameterSource().addValue("event_id", eventId));

        return eventId;
    }

    @Override
    public Integer delete(Event entity) throws IllegalArgumentException {
        return null;
    }

    @Override
    public List<Integer> deleteAll(List<Event> models) throws IllegalArgumentException {
        return null;
    }

    @Override
    public List<Integer> deleteAll() {
        return null;
    }

    @Override
    public List<Event> findAll() {
        return namedParameterJdbcTemplate.query(FIND_ALL_EVENTS, (rs, rowNum) -> {

            Event event = new Event();
            event.setEventId(rs.getInt("event_id"));
            event.setSerialNo(rs.getString("serial_no"));
            event.setEventType(rs.getString("event_type"));
            event.setFromDate(rs.getString("from_date"));
            event.setToDate(rs.getString("to_date"));
            event.setAddedDate(rs.getString("added_date"));
            event.setAddedBy(rs.getString("added_by"));
            event.setLastUpdate(rs.getString("last_update"));
            event.setUpdatedBy(rs.getString("updated_by"));
            event.setStatus(rs.getString("status"));
            event.setReportFromDate(rs.getString("report_from_date"));
            event.setReportToDate(rs.getString("report_to_date"));
            event.setMerchantName(rs.getString("merchant_name"));
            event.setType(rs.getString("type"));
            event.setEventDesc(rs.getString("event_desc"));
            event.setMerchantTransType(rs.getString("merch_trans_type"));
            event.setClearMerchant(rs.getString("batch_clear_merchant"));
            event.setExecuteType(rs.getString("execute_type"));
            event.setOnetimePassword(rs.getString("onetime_password"));

            return event;
        });
    }

    @Override
    public List<Event> findAllById(List<String> serialNos) throws IllegalArgumentException {
        logger.info("Serial Nos: " + serialNos);
        SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("serial_no", serialNos.get(0))
                .addValue("currentDate", serialNos.get(1));

        return namedParameterJdbcTemplate.query(FIND_EVENTS_BY_SERIAL_NO_AND_DATE, namedParameters, (rs, rowNum) -> {

            Event event = new Event();
            event.setEventId(rs.getInt("event_id"));
            event.setSerialNo(rs.getString("serial_no"));
            event.setEventType(rs.getString("event_type"));
            event.setFromDate(rs.getString("from_date"));
            event.setToDate(rs.getString("to_date"));
            event.setAddedDate(rs.getString("added_date"));
            event.setAddedBy(rs.getString("added_by"));
            event.setLastUpdate(rs.getString("last_update"));
            event.setUpdatedBy(rs.getString("updated_by"));
            event.setStatus(rs.getString("status"));
            event.setReportFromDate(rs.getString("report_from_date"));
            event.setReportToDate(rs.getString("report_to_date"));
            event.setMerchantName(rs.getString("merchant_name"));
            event.setType(rs.getString("type"));
            event.setEventDesc(rs.getString("event_desc"));
            event.setMerchantTransType(rs.getString("merch_trans_type"));
            event.setClearMerchant(rs.getString("batch_clear_merchant"));
            event.setExecuteType(rs.getString("execute_type"));
            event.setOnetimePassword(rs.getString("onetime_password"));

            return event;
        });
    }

    @Override
    public List<Event> findAllByDates(List<String> dates) throws IllegalArgumentException {

        SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("from_date", dates.get(0))
                .addValue("to_date", dates.get(1));

        return namedParameterJdbcTemplate.query(FIND_EVENT_BY_DATE, namedParameters, (rs, rowNum) -> {

            Event event = new Event();
            event.setEventId(rs.getInt("event_id"));
            event.setSerialNo(rs.getString("serial_no"));
            event.setEventType(rs.getString("event_type"));
            event.setFromDate(rs.getString("from_date"));
            event.setToDate(rs.getString("to_date"));
            event.setAddedDate(rs.getString("added_date"));
            event.setAddedBy(rs.getString("added_by"));
            event.setLastUpdate(rs.getString("last_update"));
            event.setUpdatedBy(rs.getString("updated_by"));
            event.setStatus(rs.getString("status"));
            event.setReportFromDate(rs.getString("report_from_date"));
            event.setReportToDate(rs.getString("report_to_date"));
            event.setMerchantName(rs.getString("merchant_name"));
            event.setType(rs.getString("type"));
            event.setEventDesc(rs.getString("event_desc"));
            event.setMerchantTransType(rs.getString("merch_trans_type"));
            event.setClearMerchant(rs.getString("batch_clear_merchant"));
            event.setExecuteType(rs.getString("execute_type"));
            event.setOnetimePassword(rs.getString("onetime_password"));

            return event;
        });
    }


    @Override
    public List<Event> findAllByKey(String key) throws IllegalArgumentException {
        logger.info("Search Key: " + key);
        SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("key", key);

        return namedParameterJdbcTemplate.query(FIND_EVENT_BY_PARAM, namedParameters, (rs, rowNum) -> {

            Event event = new Event();
            event.setEventId(rs.getInt("event_id"));
            event.setSerialNo(rs.getString("serial_no"));
            event.setEventType(rs.getString("event_type"));
            event.setFromDate(rs.getString("from_date"));
            event.setToDate(rs.getString("to_date"));
            event.setAddedDate(rs.getString("added_date"));
            event.setAddedBy(rs.getString("added_by"));
            event.setLastUpdate(rs.getString("last_update"));
            event.setUpdatedBy(rs.getString("updated_by"));
            event.setStatus(rs.getString("status"));
            event.setReportFromDate(rs.getString("report_from_date"));
            event.setReportToDate(rs.getString("report_to_date"));
            event.setMerchantName(rs.getString("merchant_name"));
            event.setType(rs.getString("type"));
            event.setEventDesc(rs.getString("event_desc"));
            event.setMerchantTransType(rs.getString("merch_trans_type"));
            event.setClearMerchant(rs.getString("batch_clear_merchant"));
            event.setExecuteType(rs.getString("execute_type"));
            event.setOnetimePassword(rs.getString("onetime_password"));

            return event;
        });
    }

    @Override
    public Optional<Event> findById(Integer eventId) throws IllegalArgumentException {

        logger.info("findById - Event ID: " + eventId);
        SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("event_id", eventId);

        return Optional
                .of(namedParameterJdbcTemplate.queryForObject(FIND_EVENT_BY_EVENT_ID, namedParameters, (rs, rowNum) -> {
                    Event event = new Event();
                    event.setEventId(rs.getInt("event_id"));
                    event.setSerialNo(rs.getString("serial_no"));
                    event.setEventType(rs.getString("event_type"));
                    event.setFromDate(rs.getString("from_date"));
                    event.setToDate(rs.getString("to_date"));
                    event.setAddedDate(rs.getString("added_date"));
                    event.setAddedBy(rs.getString("added_by"));
                    event.setLastUpdate(rs.getString("last_update"));
                    event.setUpdatedBy(rs.getString("updated_by"));
                    event.setStatus(rs.getString("status"));
                    event.setReportFromDate(rs.getString("report_from_date"));
                    event.setReportToDate(rs.getString("report_to_date"));
                    event.setMerchantName(rs.getString("merchant_name"));
                    event.setType(rs.getString("type"));
                    event.setEventDesc(rs.getString("event_desc"));
                    event.setMerchantTransType(rs.getString("merch_trans_type"));
                    event.setClearMerchant(rs.getString("batch_clear_merchant"));
                    event.setExecuteType(rs.getString("execute_type"));
                    event.setOnetimePassword(rs.getString("onetime_password"));

                    return event;
                }));
    }

    @Override
    public Optional<Event> findByKey(String key) throws IllegalArgumentException {
        return Optional.empty();
    }

    @Override
    public boolean existsById(Integer eventId) throws IllegalArgumentException {
        return jdbcTemplate.queryForObject(EVENT_EXISTS_BY_EVENT_ID, Boolean.class, eventId);
    }

    @Override
    public Integer count() {
        return null;
    }

    @Override
    public boolean existsByParams(Event params) {

        if (params.getAction().contains(ActionType.INSERT)) {
            return jdbcTemplate.queryForObject(FIND_EXIST_EVENT_BY_SERIAL_NO, Boolean.class,
                    new Object[]{
                            new String(params.getSerialNo()),
                            new String(params.getFromDate()), new String(params.getFromDate()),
                            new String(params.getToDate()), new String(params.getToDate()),
                            new String(params.getEventType()), new String(params.getEventType()),
                            new String(params.getEventType()), new String(params.getEventType()),
                    });

        } else if (params.getAction().contains(ActionType.UPDATE)) {
            return jdbcTemplate.queryForObject(FIND_EXIST_EVENT_BY_PARAMS, Boolean.class,
                    new Object[]{
                            new String(params.getSerialNo()), params.getEventId(),
                            new String(params.getFromDate()), new String(params.getFromDate()),
                            new String(params.getToDate()), new String(params.getToDate()),
                            new String(params.getEventType()), new String(params.getEventType()),
                            new String(params.getEventType()), new String(params.getEventType()),
                    });
        } else
            return true;
    }

    @Override
    public String deleteByKey(String key) throws IllegalArgumentException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String deleteByParams(List<String> params) throws IllegalArgumentException {
        return null;
    }

}
