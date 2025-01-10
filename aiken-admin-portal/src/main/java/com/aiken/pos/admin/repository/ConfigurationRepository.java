package com.aiken.pos.admin.repository;

import com.aiken.pos.admin.model.CommonConfig;
import com.aiken.pos.admin.model.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.Optional;

/**
 * @author Sajith Alahakoon
 * @version 1.0
 * @since 2024-12-03
 */
@Repository
public class ConfigurationRepository implements GenericRepository<CommonConfig, Integer> {

    private final String FIND_COMMON_CONGIGURATION = "SELECT * FROM public.common_config";

    private final String UPDATE_COMMON_CONGIGURATION = "UPDATE public.common_config"
            + "	SET profile_validation=:profile_validation "
            + "	WHERE config_id=:config_id";

    @Autowired
    private NamedParameterJdbcTemplate template;

    public void updateCommonConfig(CommonConfig commonConfig) {

        template.update(UPDATE_COMMON_CONGIGURATION, new MapSqlParameterSource()
                .addValue("profile_validation", commonConfig.isProfileValidation())
                .addValue("config_id", 1)
        );

    }

    public CommonConfig findCommonConfig() {
        CommonConfig coConfig;
        coConfig = (CommonConfig) template.queryForObject(FIND_COMMON_CONGIGURATION,
                new MapSqlParameterSource(), (rs, rowNum) -> {

                    CommonConfig config = new CommonConfig();
                    config.setProfileValidation(rs.getBoolean("profile_validation"));
                    return config;
                });

        return coConfig;
    }

    @Override
    public Integer insert(CommonConfig model) throws IllegalArgumentException {
        return null;
    }

    @Override
    public List<Integer> insertAll(List<CommonConfig> models) throws IllegalArgumentException {
        return null;
    }

    @Override
    public Integer update(CommonConfig model) throws IllegalArgumentException {
        return null;
    }

    @Override
    public List<Integer> updateAll(List<CommonConfig> models) throws IllegalArgumentException {
        return null;
    }

    @Override
    public Integer deleteById(Integer integer) throws IllegalArgumentException {
        return null;
    }

    @Override
    public Integer delete(CommonConfig entity) throws IllegalArgumentException {
        return null;
    }

    @Override
    public List<Integer> deleteAll(List<CommonConfig> models) throws IllegalArgumentException {
        return null;
    }

    @Override
    public List<Integer> deleteAll() {
        return null;
    }

    @Override
    public List<CommonConfig> findAll() {
        return null;
    }

    @Override
    public List<CommonConfig> findAllById(List<String> ids) throws IllegalArgumentException {
        return null;
    }

    @Override
    public Optional<CommonConfig> findById(Integer integer) throws IllegalArgumentException {
        return Optional.empty();
    }

    @Override
    public Optional<CommonConfig> findByKey(String key) throws IllegalArgumentException {
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
    public boolean existsByParams(CommonConfig params) throws IllegalArgumentException {
        return false;
    }

    @Override
    public List<CommonConfig> findAllByDates(List<String> params) throws IllegalArgumentException {
        return null;
    }

    @Override
    public List<Event> findAllByKey(String param) throws IllegalArgumentException {
        return null;
    }

    @Override
    public String deleteByKey(String key) throws IllegalArgumentException {
        return null;
    }

    @Override
    public String deleteByParams(List<String> params) throws IllegalArgumentException {
        return null;
    }
}
