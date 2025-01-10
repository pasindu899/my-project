package com.aiken.pos.admin.repository;

import com.aiken.common.util.validation.StringUtil;
import com.aiken.pos.admin.model.ConfigSettingData;
import com.aiken.pos.admin.model.Event;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Repository;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import static com.aiken.pos.admin.constant.ParamConst.*;

/**
 * @author Sajith Alahakoon
 * @version aiken-admin-portal / 1.0
 * @since 2024-October-11
 */
@Repository
public class ConfigSettingRepository implements GenericRepository<ConfigSettingData, Integer> {
    private final Logger logger = LoggerFactory.getLogger(ConfigSettingRepository.class);
    private final NamedParameterJdbcTemplate template;

    private static final String INSERT_CONFIG_SETTING = "INSERT INTO public.setting_config " +
            "(serial_no,setting_data,added_date,added_by,update_date,updated_by) " +
            "values(:serial_no,:setting_data,:added_date,:added_by,:update_date,:updated_by)";

    private static final String UPDATE_CONFIG_SETTING = "UPDATE  public.setting_config SET " +
            "setting_data=:setting_data,added_date=:added_date," +
            "added_by=:added_by,update_date=:update_date,updated_by=:updated_by WHERE serial_no=:serial_no ";

    private static final String FIND_CONFIG_SETTING_BY_SERIAL_NO = "SELECT * FROM public.setting_config WHERE serial_no =:serial_no";

    private static final String DELETE_CONFIG_SETTING_BY_SERIAL_NO = "DELETE FROM public.setting_config WHERE serial_no =:serial_no";

    public ConfigSettingRepository(NamedParameterJdbcTemplate template) {
        this.template = template;
    }


    @Override
    public Integer insert(ConfigSettingData model) throws IllegalArgumentException {
        if (model == null) {
            logger.info("ConfigSettingData should not be null or empty");
            throw new BadCredentialsException(" ConfigSettingData should not be null ");
        }

        KeyHolder holder = new GeneratedKeyHolder();
        template.update(INSERT_CONFIG_SETTING, new MapSqlParameterSource()
                        .addValue(SERIAL_NO, model.getSerialNo())
                        .addValue(SETTING_DATA, model.getSettingData(), java.sql.Types.OTHER)
                        .addValue(ADDED_DATE, Calendar.getInstance().getTime())
                        .addValue(ADDED_BY, model.getAddedBy())
                        .addValue(UPDATE_DATA, Calendar.getInstance().getTime())
                        .addValue(UPDATE_BY, model.getUpdatedBy()),
                holder, new String[]{SETTING_ID});
        Number key = holder.getKey();
        return (key != null && key.intValue() >= 1) ? key.intValue() : 0;

    }

    @Override
    public List<Integer> insertAll(List<ConfigSettingData> models) throws IllegalArgumentException {
        return null;
    }

    @Override
    public Integer update(ConfigSettingData model) throws IllegalArgumentException {

        if (model == null) {
            logger.info("ConfigSettingData should not be null or empty");
            throw new BadCredentialsException(" ConfigSettingData should not be null ");
        }

        return template.update(UPDATE_CONFIG_SETTING, new MapSqlParameterSource()
                .addValue(SERIAL_NO, model.getSerialNo())
                .addValue(SETTING_DATA, model.getSettingData(), java.sql.Types.OTHER)
                .addValue(ADDED_DATE, model.getAddedDate())
                .addValue(ADDED_BY, model.getAddedBy())
                .addValue(UPDATE_DATA, Calendar.getInstance().getTime())
                .addValue(UPDATE_BY, model.getUpdatedBy()));

    }

    @Override
    public List<Integer> updateAll(List<ConfigSettingData> models) throws IllegalArgumentException {
        return null;
    }

    @Override
    public Integer deleteById(Integer integer) throws IllegalArgumentException {
        return null;
    }

    @Override
    public Integer delete(ConfigSettingData entity) throws IllegalArgumentException {
        return null;
    }

    @Override
    public List<Integer> deleteAll(List<ConfigSettingData> models) throws IllegalArgumentException {
        return null;
    }

    @Override
    public List<Integer> deleteAll() {
        return null;
    }

    @Override
    public List<ConfigSettingData> findAll() {
        return null;
    }

    @Override
    public List<ConfigSettingData> findAllById(List<String> ids) throws IllegalArgumentException {
        return null;
    }

    @Override
    public Optional<ConfigSettingData> findById(Integer integer) throws IllegalArgumentException {
        return Optional.empty();
    }

    @Override
    public Optional<ConfigSettingData> findByKey(String key) throws IllegalArgumentException {

        if (StringUtil.isNullOrWhiteSpace(key)) {
            logger.info("device serial number should not be null or empty - ConfigSettingRepository");
            throw new BadCredentialsException(" device serial number should not be null or empty");
        }

        List<ConfigSettingData> configSettingData = template.query(FIND_CONFIG_SETTING_BY_SERIAL_NO, new MapSqlParameterSource()
                        .addValue(SERIAL_NO, key), (rs, i) -> {
                    ConfigSettingData dto = new ConfigSettingData();
                    dto.setSettingConfigId(rs.getInt(SETTING_ID));
                    dto.setSerialNo(rs.getString(SERIAL_NO));
                    dto.setAddedBy(rs.getString(ADDED_BY));
                    dto.setAddedDate(rs.getString(ADDED_DATE));
                    dto.setUpdatedBy(rs.getString(UPDATE_BY));
                    dto.setUpdateDate(rs.getString(UPDATE_DATA));
                    dto.setSettingData(rs.getString(SETTING_DATA));
                    return dto;
                }
        );
        if (!configSettingData.isEmpty() && configSettingData.get(0) != null) {
            return Optional.of(configSettingData.get(0));
        }
        return Optional.empty();
    }

    @Override
    public boolean existsById(Integer integer) throws IllegalArgumentException {
        return false;
    }

    @Override
    public Integer count() {
        return null;
    }

    @Override
    public boolean existsByParams(ConfigSettingData params) throws IllegalArgumentException {
        return false;
    }

    @Override
    public List<ConfigSettingData> findAllByDates(List<String> params) throws IllegalArgumentException {
        return null;
    }

    @Override
    public List<Event> findAllByKey(String param) throws IllegalArgumentException {
        return null;
    }

    @Override
    public String deleteByKey(String key) throws IllegalArgumentException {
        if (StringUtil.isNullOrWhiteSpace(key)) {
            logger.info("device serial number should not be null or empty - ConfigSettingRepository ");
            throw new BadCredentialsException("device serial number should not be null or empty");
        }
        int update = template.update(DELETE_CONFIG_SETTING_BY_SERIAL_NO, new MapSqlParameterSource().addValue(SERIAL_NO, key));
        return String.valueOf(update);
    }

    @Override
    public String deleteByParams(List<String> params) throws IllegalArgumentException {
        return null;
    }
}
