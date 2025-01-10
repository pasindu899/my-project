package com.aiken.pos.admin.repository;

import com.aiken.pos.admin.dto.CommunicationDTO;
import com.aiken.pos.admin.dto.MerchantInfoDTO;
import com.aiken.pos.admin.model.Event;
import org.apache.catalina.core.AprLifecycleListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Repository
public class ReportRepository implements GenericRepository<MerchantInfoDTO, String> {

    @Autowired
    private NamedParameterJdbcTemplate template;

    public List<MerchantInfoDTO> findByBank(String bankCode, int offset, int limit) {
        String reportQuery = "SELECT d.bank_code, d.serial_no, d.merchant_name, d.merchant_address, m.category, m.mid, m.tid, m.description " +
                "FROM device AS d " +
                "INNER JOIN merchant AS m ON m.device_id = d.device_id " +
                "WHERE m.category = 'SALE' AND d.bank_code = :bankCode " +
                "LIMIT :limit OFFSET :offset";

        try {
            MapSqlParameterSource namedParameters = new MapSqlParameterSource()
                    .addValue("limit", limit)
                    .addValue("offset", offset)
                    .addValue("bankCode", bankCode);
            return template.query(reportQuery, namedParameters, (rs, rowNum) -> {
                MerchantInfoDTO dto = new MerchantInfoDTO();
                dto.setBankCode(rs.getString("bank_code"));
                dto.setSerialNo(rs.getString("serial_no"));
                dto.setMerchantName(rs.getString("merchant_name"));
                dto.setMerchantAddress(rs.getString("merchant_address"));
                dto.setCategory(rs.getString("category"));
                dto.setMid(rs.getString("mid"));
                dto.setTid(rs.getString("tid"));
                dto.setDescription(rs.getString("description"));
                return dto;
            });
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    public int countByBank(String bankCode) {
        String countQuery = "SELECT COUNT(*) FROM device AS d " +
                "INNER JOIN merchant AS m ON m.device_id = d.device_id " +
                "WHERE m.category = 'SALE' AND d.bank_code = :bankCode ";
        try {
            MapSqlParameterSource namedParameters = new MapSqlParameterSource()
                    .addValue("bankCode", bankCode);
            return template.queryForObject(countQuery, namedParameters, Integer.class);
        } catch (Exception e) {
            return 0;
        }
    }

    public List<MerchantInfoDTO> findByCategoryAndBank(String bankCode, int offset, int limit) {

        String reportQuery = "SELECT d.bank_code, d.serial_no, d.merchant_name, d.merchant_address, " +
                "m.category, m.mid, m.tid, m.description " +
                "FROM merchant AS m " +
                "INNER JOIN device AS d ON m.device_id = d.device_id " +
                "WHERE m.category = 'AMEX' AND d.bank_code = :bankCode " +
                "LIMIT :limit OFFSET :offset";

        if(bankCode.equals("ALL")) {
            reportQuery = "SELECT d.bank_code, d.serial_no, d.merchant_name, d.merchant_address, " +
                    "m.category, m.mid, m.tid, m.description " +
                    "FROM merchant AS m " +
                    "INNER JOIN device AS d ON m.device_id = d.device_id " +
                    "WHERE m.category = 'AMEX' " +
                    "LIMIT :limit OFFSET :offset";
        }

        try {
            MapSqlParameterSource namedParameters = new MapSqlParameterSource()
                    .addValue("limit", limit)
                    .addValue("offset", offset)
                    .addValue("bankCode", bankCode);
            return template.query(reportQuery, namedParameters, (rs, rowNum) -> {
                MerchantInfoDTO dto = new MerchantInfoDTO();
                dto.setBankCode(rs.getString("bank_code"));
                dto.setSerialNo(rs.getString("serial_no"));
                dto.setMerchantName(rs.getString("merchant_name"));
                dto.setMerchantAddress(rs.getString("merchant_address"));
                dto.setCategory(rs.getString("category"));
                dto.setMid(rs.getString("mid"));
                dto.setTid(rs.getString("tid"));
                dto.setDescription(rs.getString("description"));
                return dto;
            });
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    public int countByCategoryAndBank(String bankCode) {
        String reportQuery = "SELECT COUNT(*) " +
                "FROM merchant AS m " +
                "INNER JOIN device AS d ON m.device_id = d.device_id " +
                "WHERE m.category = 'AMEX' AND d.bank_code = :bankCode";

        if(bankCode.equals("ALL")) {
             reportQuery = "SELECT COUNT(*) " +
                    "FROM merchant AS m " +
                    "INNER JOIN device AS d ON m.device_id = d.device_id " +
                    "WHERE m.category = 'AMEX' ";
        }

        try {
            MapSqlParameterSource namedParameters = new MapSqlParameterSource()
                    .addValue("bankCode", bankCode);
            return template.queryForObject(reportQuery, namedParameters, Integer.class);
        } catch (Exception e) {
            return 0;
        }
    }

    public List<MerchantInfoDTO> findByCategoryByDevice(String bankCode, int offset, int limit) {
        String reportQuery = "SELECT d.bank_code, d.serial_no, d.merchant_name, d.merchant_address, d.merchant_Portal, m.category, m.mid, m.tid, m.description " +
                "FROM merchant AS m INNER JOIN device AS d " +
                "ON m.device_id = d.device_id " +
                "WHERE d.bank_code = 'HNB' AND m.category = 'SALE' AND d.serial_no LIKE 'N7%' AND d.merchant_Portal = true " +
                "LIMIT :limit OFFSET :offset";

        if(bankCode.equals("N700_Devices")) {
             reportQuery = "SELECT d.bank_code, d.serial_no, d.merchant_name, d.merchant_address, m.category, m.mid, m.tid, m.description " +
                    "FROM merchant AS m INNER JOIN device AS d " +
                    "ON m.device_id = d.device_id " +
                    "WHERE d.bank_code = 'HNB' AND m.category = 'SALE' AND d.serial_no LIKE 'N6%'" +
                    "LIMIT :limit OFFSET :offset";
        }
        try {
            MapSqlParameterSource namedParameters = new MapSqlParameterSource()
                    .addValue("limit", limit)
                    .addValue("offset", offset)
                    .addValue("bankCode", bankCode);
            return template.query(reportQuery, namedParameters, (rs, rowNum) -> {
                MerchantInfoDTO dto = new MerchantInfoDTO();
                dto.setBankCode(rs.getString("bank_code"));
                dto.setSerialNo(rs.getString("serial_no"));
                dto.setMerchantName(rs.getString("merchant_name"));
                dto.setMerchantAddress(rs.getString("merchant_address"));
                dto.setCategory(rs.getString("category"));
                dto.setMid(rs.getString("mid"));
                dto.setTid(rs.getString("tid"));
                dto.setDescription(rs.getString("description"));
                return dto;
            });
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    public int countByCategoryYDevice(String bankCode) {
        String countQuery = "SELECT COUNT(*) FROM device AS d " +
                "INNER JOIN merchant AS m ON m.device_id = d.device_id " +
                "WHERE d.bank_code = 'HNB' AND m.category = 'SALE' AND d.serial_no LIKE 'N7%' AND d.merchant_Portal = true ";

        if(bankCode.equals("N700_Devices")) {
            countQuery = "SELECT COUNT(*) FROM device AS d " +
                    "INNER JOIN merchant AS m ON m.device_id = d.device_id " +
                    "WHERE d.bank_code = 'HNB' AND m.category = 'SALE' AND d.serial_no LIKE 'N6%'";
        }
        try {
            MapSqlParameterSource namedParameters = new MapSqlParameterSource()
                    .addValue("bankCode", bankCode);
            return template.queryForObject(countQuery, namedParameters, Integer.class);
        } catch (Exception e) {
            return 0;
        }
    }

    public List<MerchantInfoDTO> findByCategoryAndSerialNoN700(String bankCode, int offset, int limit) {

        String reportQuery = "SELECT d.bank_code, d.serial_no, d.merchant_name, d.merchant_address, m.category, m.mid, m.tid, m.description " +
                "FROM merchant AS m INNER JOIN device AS d " +
                "ON m.device_id = d.device_id " +
                "WHERE d.bank_code = 'HNB' AND m.category = 'SALE' AND d.serial_no LIKE 'N6%'" +
                "LIMIT :limit OFFSET :offset";


        try {
            MapSqlParameterSource namedParameters = new MapSqlParameterSource()
                    .addValue("limit", limit)
                    .addValue("offset", offset)
                    .addValue("bankCode", bankCode);
            return template.query(reportQuery, namedParameters, (rs, rowNum) -> {
                MerchantInfoDTO dto = new MerchantInfoDTO();
                dto.setBankCode(rs.getString("bank_code"));
                dto.setSerialNo(rs.getString("serial_no"));
                dto.setMerchantName(rs.getString("merchant_name"));
                dto.setMerchantAddress(rs.getString("merchant_address"));
                dto.setCategory(rs.getString("category"));
                dto.setMid(rs.getString("mid"));
                dto.setTid(rs.getString("tid"));
                dto.setDescription(rs.getString("description"));
                return dto;
            });
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    public int countByCategoryAndSerialNoN700(String bankCode) {
        String countQuery = "SELECT COUNT(*) FROM device AS d " +
                "INNER JOIN merchant AS m ON m.device_id = d.device_id " +
                "WHERE d.bank_code = 'HNB' AND m.category = 'SALE' AND d.serial_no LIKE 'N6%'";
        try {
            MapSqlParameterSource namedParameters = new MapSqlParameterSource()
                    .addValue("bankCode", bankCode);
            return template.queryForObject(countQuery, namedParameters, Integer.class);
        } catch (Exception e) {
            return 0;
        }
    }

    public List<MerchantInfoDTO> findAllMIDTID(String bankCode, int offset, int limit) {
        String reportQuery = "SELECT DISTINCT d.bank_code, d.serial_no, d.merchant_name, d.merchant_address, " +
                "m.mid, m.tid, m.description, m.category " +
                "FROM device AS d " +
                "INNER JOIN merchant AS m ON d.device_id = m.device_id " +
                "INNER JOIN profile AS p ON p.device_id = d.device_id " +
                "WHERE d.bank_code = :bankCode " +
                "LIMIT :limit OFFSET :offset";

        MapSqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("bankCode", bankCode)
                .addValue("limit", limit)
                .addValue("offset", offset);

        try {
            return template.query(reportQuery, namedParameters, (rs, rowNum) -> {
                MerchantInfoDTO dto = new MerchantInfoDTO();
                dto.setBankCode(rs.getString("bank_code"));
                dto.setSerialNo(rs.getString("serial_no"));
                dto.setMerchantName(rs.getString("merchant_name"));
                dto.setMerchantAddress(rs.getString("merchant_address"));
                dto.setCategory(rs.getString("category"));
                dto.setMid(rs.getString("mid"));
                dto.setTid(rs.getString("tid"));
                dto.setDescription(rs.getString("description"));
                return dto;
            });
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    public int countByAllMIDTID(String bankCode) {
        String countQuery = "SELECT DISTINCT COUNT(*) FROM device AS d " +
                "INNER JOIN merchant AS m ON d.device_id = m.device_id " +
                "INNER JOIN profile AS p ON p.device_id = d.device_id " +
                "WHERE d.bank_code = :bankCode ";
        try {
            MapSqlParameterSource namedParameters = new MapSqlParameterSource()
                    .addValue("bankCode", bankCode);
            return template.queryForObject(countQuery, namedParameters, Integer.class);
        } catch (Exception e) {
            return 0;
        }
    }


    public List<MerchantInfoDTO> getReportsByBankCodeForSaleReport(String bankCode) {
        String reportQuery = "SELECT d.bank_code, d.serial_no, d.merchant_name, d.merchant_address, m.category, m.mid, m.tid, m.description " +
                "FROM device AS d " +
                "INNER JOIN merchant AS m ON m.device_id = d.device_id " +
                "WHERE m.category = 'SALE' AND d.bank_code = :bankCode ";

        try {
            MapSqlParameterSource namedParameters = new MapSqlParameterSource()
                    .addValue("bankCode", bankCode);
            return template.query(reportQuery, namedParameters, (rs, rowNum) -> {
                MerchantInfoDTO dto = new MerchantInfoDTO();
                dto.setBankCode(rs.getString("bank_code"));
                dto.setSerialNo(rs.getString("serial_no"));
                dto.setMerchantName(rs.getString("merchant_name"));
                dto.setMerchantAddress(rs.getString("merchant_address"));
                dto.setCategory(rs.getString("category"));
                dto.setMid(rs.getString("mid"));
                dto.setTid(rs.getString("tid"));
                dto.setDescription(rs.getString("description"));
                return dto;
            });
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    public List<MerchantInfoDTO> getReportsByBankCodeForAmexReport(String bankCode) {
        String reportQuery = "SELECT d.bank_code, d.serial_no, d.merchant_name, d.merchant_address, " +
                "m.category, m.mid, m.tid, m.description " +
                "FROM merchant AS m " +
                "INNER JOIN device AS d ON m.device_id = d.device_id " +
                "WHERE m.category = 'AMEX' AND d.bank_code = :bankCode ";

        if(bankCode.equals("ALL")) {
            reportQuery = "SELECT d.bank_code, d.serial_no, d.merchant_name, d.merchant_address, " +
                    "m.category, m.mid, m.tid, m.description " +
                    "FROM merchant AS m " +
                    "INNER JOIN device AS d ON m.device_id = d.device_id " +
                    "WHERE m.category = 'AMEX' ";
        }


        try {
            MapSqlParameterSource namedParameters = new MapSqlParameterSource()
                    .addValue("bankCode", bankCode);
            return template.query(reportQuery, namedParameters, (rs, rowNum) -> {
                MerchantInfoDTO dto = new MerchantInfoDTO();
                dto.setBankCode(rs.getString("bank_code"));
                dto.setSerialNo(rs.getString("serial_no"));
                dto.setMerchantName(rs.getString("merchant_name"));
                dto.setMerchantAddress(rs.getString("merchant_address"));
                dto.setCategory(rs.getString("category"));
                dto.setMid(rs.getString("mid"));
                dto.setTid(rs.getString("tid"));
                dto.setDescription(rs.getString("description"));
                return dto;
            });
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    public List<CommunicationDTO> findComParams(String bankCode, int offset, int limit) {
        String reportQuery = "SELECT d.bank_code, d.serial_no, d.merchant_name, m.mid, m.tid, c.sim1, c.sim2, c.operator1, c.operator2, c.ref1, c.ref2 " +
                "FROM com_config AS c " +
                "INNER JOIN device AS d ON d.serial_no = c.serial_no " +
                "INNER JOIN merchant AS m ON d.device_id = m.device_id " +
                "WHERE d.bank_code = :bankCode " +
                "LIMIT :limit OFFSET :offset";

        MapSqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("bankCode", bankCode)
                .addValue("limit", limit)
                .addValue("offset", offset);

        try {
            return template.query(reportQuery, namedParameters, (rs, rowNum) -> {
                CommunicationDTO dto = new CommunicationDTO();
                dto.setBankCode(rs.getString("bank_code"));
                dto.setSerialNo(rs.getString("serial_no"));
                dto.setMerchantName(rs.getString("merchant_name"));
                dto.setMid(rs.getString("mid"));
                dto.setTid(rs.getString("tid"));
                dto.setOperator1(rs.getString("operator1"));
                dto.setOperator2(rs.getString("operator2"));
                dto.setSim1(rs.getString("sim1"));
                dto.setSim2(rs.getString("sim2"));
                return dto;
            });
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }
    public int countByComParams(String bankCode) {
        String countQuery = "SELECT COUNT(*)  " +
                "FROM com_config AS c " +
                "INNER JOIN device AS d ON d.serial_no = c.serial_no " +
                "INNER JOIN merchant AS m ON d.device_id = m.device_id " +
                "WHERE d.bank_code = :bankCode ";
        try {
            MapSqlParameterSource namedParameters = new MapSqlParameterSource()
                    .addValue("bankCode", bankCode);
            return template.queryForObject(countQuery, namedParameters, Integer.class);
        } catch (Exception e) {
            return 0;
        }
    }

    public List<MerchantInfoDTO> downloadReportForAllMIDTID(String bankCode) {
        String reportQuery = "SELECT DISTINCT d.bank_code, d.serial_no, d.merchant_name, d.merchant_address, " +
                "m.mid, m.tid, m.description, m.category " +
                "FROM device AS d " +
                "INNER JOIN merchant AS m ON d.device_id = m.device_id " +
                "INNER JOIN profile AS p ON p.device_id = d.device_id " +
                "WHERE d.bank_code = :bankCode ";

        try {
            MapSqlParameterSource namedParameters = new MapSqlParameterSource()
                    .addValue("bankCode", bankCode);
            return template.query(reportQuery, namedParameters, (rs, rowNum) -> {
                MerchantInfoDTO dto = new MerchantInfoDTO();
                dto.setBankCode(rs.getString("bank_code"));
                dto.setSerialNo(rs.getString("serial_no"));
                dto.setMerchantName(rs.getString("merchant_name"));
                dto.setMerchantAddress(rs.getString("merchant_address"));
                dto.setCategory(rs.getString("category"));
                dto.setMid(rs.getString("mid"));
                dto.setTid(rs.getString("tid"));
                dto.setDescription(rs.getString("description"));
                return dto;
            });
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    public List<MerchantInfoDTO> downloadReportForDevices(String bankCode) {
        String reportQuery = "SELECT d.bank_code, d.serial_no, d.merchant_name, d.merchant_address, d.merchant_Portal, m.category, m.mid, m.tid, m.description " +
                "FROM merchant AS m INNER JOIN device AS d " +
                "ON m.device_id = d.device_id " +
                "WHERE d.bank_code = 'HNB' AND m.category = 'SALE' AND d.serial_no LIKE 'N7%' AND d.merchant_Portal = true ";

        if(bankCode.equals("N700_Devices")) {
            reportQuery = "SELECT d.bank_code, d.serial_no, d.merchant_name, d.merchant_address, m.category, m.mid, m.tid, m.description " +
                    "FROM merchant AS m INNER JOIN device AS d " +
                    "ON m.device_id = d.device_id " +
                    "WHERE d.bank_code = 'HNB' AND m.category = 'SALE' AND d.serial_no LIKE 'N6%'";
        }

        try {
            MapSqlParameterSource namedParameters = new MapSqlParameterSource()
                    .addValue("bankCode", bankCode);
            return template.query(reportQuery, namedParameters, (rs, rowNum) -> {
                MerchantInfoDTO dto = new MerchantInfoDTO();
                dto.setBankCode(rs.getString("bank_code"));
                dto.setSerialNo(rs.getString("serial_no"));
                dto.setMerchantName(rs.getString("merchant_name"));
                dto.setMerchantAddress(rs.getString("merchant_address"));
                dto.setCategory(rs.getString("category"));
                dto.setMid(rs.getString("mid"));
                dto.setTid(rs.getString("tid"));
                dto.setDescription(rs.getString("description"));
                return dto;
            });
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    public List<CommunicationDTO> downloadReportForComParams(String bankCode) {
        String reportQuery = "SELECT d.bank_code, d.serial_no, d.merchant_name, m.mid, m.tid, c.sim1, c.sim2, c.operator1, c.operator2, c.ref1, c.ref2 " +
                "FROM com_config AS c " +
                "INNER JOIN device AS d ON d.serial_no = c.serial_no " +
                "INNER JOIN merchant AS m ON d.device_id = m.device_id " +
                "WHERE d.bank_code = :bankCode ";
        MapSqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("bankCode", bankCode);

        try {
            return template.query(reportQuery, namedParameters, (rs, rowNum) -> {
                CommunicationDTO dto = new CommunicationDTO();
                dto.setBankCode(rs.getString("bank_code"));
                dto.setSerialNo(rs.getString("serial_no"));
                dto.setMerchantName(rs.getString("merchant_name"));
                dto.setMid(rs.getString("mid"));
                dto.setTid(rs.getString("tid"));
                dto.setOperator1(rs.getString("operator1"));
                dto.setOperator2(rs.getString("operator2"));
                dto.setSim1(rs.getString("sim1"));
                dto.setSim2(rs.getString("sim2"));
                return dto;
            });
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }


    @Override
    public String insert(MerchantInfoDTO model) throws IllegalArgumentException {
        return null;
    }

    @Override
    public List<String> insertAll(List<MerchantInfoDTO> models) throws IllegalArgumentException {
        return null;
    }

    @Override
    public String update(MerchantInfoDTO model) throws IllegalArgumentException {
        return null;
    }

    @Override
    public List<String> updateAll(List<MerchantInfoDTO> models) throws IllegalArgumentException {
        return null;
    }

    @Override
    public String deleteById(String s) throws IllegalArgumentException {
        return null;
    }

    @Override
    public String delete(MerchantInfoDTO entity) throws IllegalArgumentException {
        return null;
    }

    @Override
    public List<String> deleteAll(List<MerchantInfoDTO> models) throws IllegalArgumentException {
        return null;
    }

    @Override
    public List<String> deleteAll() {
        return null;
    }

    @Override
    public List<MerchantInfoDTO> findAll() {
        return null;
    }


    @Override
    public List<MerchantInfoDTO> findAllById(List<String> ids) throws IllegalArgumentException {
        return null;
    }

    @Override
    public Optional<MerchantInfoDTO> findById(String s) throws IllegalArgumentException {
        return Optional.empty();
    }

    @Override
    public Optional<MerchantInfoDTO> findByKey(String key) throws IllegalArgumentException {
        return Optional.empty();
    }

    @Override
    public boolean existsById(String s) throws IllegalArgumentException {
        return false;
    }

    @Override
    public String count() {
        return null;
    }

    @Override
    public boolean existsByParams(MerchantInfoDTO params) throws IllegalArgumentException {
        return false;
    }

    @Override
    public List<MerchantInfoDTO> findAllByDates(List<String> params) throws IllegalArgumentException {
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
