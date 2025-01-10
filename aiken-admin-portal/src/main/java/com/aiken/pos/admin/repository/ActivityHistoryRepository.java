package com.aiken.pos.admin.repository;

import com.aiken.pos.admin.model.ActivityHistory;
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

import java.util.*;

/**
 * Activity History Repository
 *
 * @author Chathuranga Dissanayake
 * @version 1.0
 * @since 2021-09-22
 */
@Repository
public class ActivityHistoryRepository implements GenericRepository<ActivityHistory, Long> {

	private Logger logger = LoggerFactory.getLogger(ActivityHistoryRepository.class);

	// Insert Script
	private final String INSERT_ACTIVITY_HISTORY = "insert into public.activity_history "
			+ "(serial_no,typ, step, description, actn, status, data1, data2, data3, date, time, is_data)"
			+ " values(:serial_no, :typ, :step, :description, :actn, :status, :data1,:data2, :data3, :date, :time, :is_data)";

	private final String FIND_ACTIVITY_HISTORY_BY_ID = "select * from public.activity_history where id = :id";

	private final String FIND_ALL_BY_SERIAL_NO = "select * from public.activity_history where serial_no = :serial_no";

	private final String DELETE_ACTIVITY_ID = "delete from public.activity_history where id = :id";

	private final String DELETE_ALL_ACTIVITY = "delete from public.activity_history";

	private final String FIND_ALL_ACTIVITIES = "select * from public.activity_history order by date desc";
	
	private final String DELETE_ACTIVITY_BY_SERIAL_NO = "delete from public.activity_history where serial_no = :serial_no";

	private final String DELETE_ACTIVITY_BY_PARAMS = "delete from public.activity_history where serial_no = :serial_no AND typ=:typ AND"
			+ " date=:date AND time=:time";

	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	@Autowired
    private JdbcTemplate jdbcTemplate;

	@Override
	public Long insert(ActivityHistory activityHistory) throws IllegalArgumentException {
		return saveActivityHistory(activityHistory);
	}

	@Override
	public List<Long> insertAll(List<ActivityHistory> models) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long update(ActivityHistory model) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Long> updateAll(List<ActivityHistory> models) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Long delete(ActivityHistory entity) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		Long id =	deleteById(entity.getId());
		return id;
	}

	@Override
	public List<Long> deleteAll(List<ActivityHistory> models) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		List<Long> data = new ArrayList<>();
		for(ActivityHistory act : models) {
			Long id =	deleteById(act.getId());
			data.add(id);
		}
		return data;
	}

	@Override
	public List<Long> deleteAll() {
		List<Long> data = new ArrayList<>();
		data.add((long) jdbcTemplate.update(DELETE_ALL_ACTIVITY));
		  return data;
	}

	@Override
	public List<ActivityHistory> findAll() {
		return namedParameterJdbcTemplate.query(FIND_ALL_ACTIVITIES, (rs, rowNum) -> {
			ActivityHistory activity = new ActivityHistory();
			activity.setId(rs.getLong("id"));
			activity.setSerialNo(rs.getString("serial_no"));
			activity.setType(rs.getString("typ"));
			activity.setStep(rs.getString("step"));
			activity.setDesc(rs.getString("description"));
			activity.setAction(rs.getString("actn"));
			activity.setStatus(rs.getString("status"));
			activity.setDate(rs.getString("date"));
			activity.setTime(rs.getString("time"));
			logger.info("Activity Id" + activity.getId());
			return activity;
		});
	}

	@Override
	public List<ActivityHistory> findAllById(List<String> ids) throws IllegalArgumentException {

		SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("serial_no", ids.get(0));

		return namedParameterJdbcTemplate.query(FIND_ALL_BY_SERIAL_NO, namedParameters, (rs, rowNum) -> {

			ActivityHistory activity = new ActivityHistory();
			activity.setId(rs.getLong("id"));
			activity.setSerialNo(rs.getString("serial_no"));
			activity.setType(rs.getString("typ"));
			activity.setStep(rs.getString("step"));
			activity.setDesc(rs.getString("description"));
			activity.setAction(rs.getString("actn"));
			activity.setStatus(rs.getString("status"));
			activity.setDate(rs.getString("date"));
			activity.setTime(rs.getString("time"));
			return activity;
		});
	}

	@Override
	public Optional<ActivityHistory> findById(Long id) throws IllegalArgumentException {

		logger.info("Activity History ID: " + id);
		SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("id", id);

		return Optional.ofNullable(
				namedParameterJdbcTemplate.queryForObject(FIND_ACTIVITY_HISTORY_BY_ID, namedParameters, (rs, rowNum) -> {
					ActivityHistory activity = new ActivityHistory();
					activity.setId(rs.getLong("id"));
					activity.setSerialNo(rs.getString("serial_no"));
					activity.setType(rs.getString("typ"));
					activity.setStep(rs.getString("step"));
					activity.setDesc(rs.getString("description"));
					activity.setAction(rs.getString("actn"));
					activity.setStatus(rs.getString("status"));
					activity.setDate(rs.getString("date"));
					activity.setTime(rs.getString("time"));
					return activity;
				}));
	}

	@Override
	public Optional<ActivityHistory> findByKey(String key) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		
		return null;
	}

	@Override
	public boolean existsById(Long id) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Long count() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean existsByParams(ActivityHistory params) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<ActivityHistory> findAllByDates(List<String> params) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Event> findAllByKey(String param) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}

	// save activity history
	private Long saveActivityHistory(ActivityHistory activityHistory) {
		KeyHolder holder = new GeneratedKeyHolder();
		logger.debug("Desc"+ activityHistory.getSerialNo());
		namedParameterJdbcTemplate.update(INSERT_ACTIVITY_HISTORY,
				new MapSqlParameterSource().addValue("serial_no", activityHistory.getSerialNo())
						.addValue("typ", activityHistory.getType())
						.addValue("step", activityHistory.getStep())
						.addValue("description", activityHistory.getDesc())
						.addValue("actn", activityHistory.getAction())
						.addValue("status", activityHistory.getStatus())
						.addValue("data1", activityHistory.getData1())
						.addValue("data2", activityHistory.getData2())
						.addValue("data3", activityHistory.getData3())
						.addValue("date", activityHistory.getDate())
						.addValue("time", activityHistory.getTime())
						.addValue("is_data", activityHistory.getIsData()),
				holder, new String[] { "id" });

		return holder.getKey().longValue();
	}

	@Override
	public Long deleteById(Long id) throws IllegalArgumentException {
		namedParameterJdbcTemplate.update(DELETE_ACTIVITY_ID, new MapSqlParameterSource().addValue("id", id));
		return id;
	}

	@Override
	public String deleteByKey(String key) throws IllegalArgumentException {
		namedParameterJdbcTemplate.update(DELETE_ACTIVITY_BY_SERIAL_NO, new MapSqlParameterSource().addValue("serial_no", key));
		return key;
	}
	@Override
	public String deleteByParams(List<String> params) throws IllegalArgumentException {
		logger.debug("DELETE_ACTIVITY_BY_PARAMS>> SerialNo:"+ params.get(0));
		namedParameterJdbcTemplate.update(DELETE_ACTIVITY_BY_PARAMS,
				new MapSqlParameterSource().addValue("serial_no", params.get(0))
						.addValue("typ", params.get(1))
						.addValue("date", params.get(2))
						.addValue("time", params.get(3)));
		return params.get(0);
	}
}
