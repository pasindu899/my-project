package com.aiken.pos.admin.service;

import com.aiken.pos.admin.dto.CommunicationDTO;
import com.aiken.pos.admin.dto.MerchantInfoDTO;
import com.aiken.pos.admin.repository.ReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReportService {
    private final ReportRepository reportRepository;

    @Autowired
    public ReportService(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

    public List<MerchantInfoDTO> getReportsByBankCode(String bankCode, int limit, int page) {
        int offset = page * limit;
        return reportRepository.findByBank(bankCode, offset, limit);
    }

    public int getTotalRecords(String bankCode) {
        return reportRepository.countByBank(bankCode);
    }

    public List<MerchantInfoDTO> getReportsByCategoryAndBankCode(String bankCode, int limit, int page) {
        int offset = page * limit;
        return reportRepository.findByCategoryAndBank(bankCode, offset, limit);
    }

    public int getTotalRecordsByCategoryAndBankCode(String bankCode) {
        return reportRepository.countByCategoryAndBank(bankCode);
    }

    public List<MerchantInfoDTO> getDevice(String bankCode, int limit, int page) {
        int offset = page * limit;
        return reportRepository.findByCategoryByDevice(bankCode, offset, limit);
    }

    public List<CommunicationDTO> getComParam(String bankCode, int limit, int page) {
        int offset = page * limit;
        return reportRepository.findComParams(bankCode, offset, limit);
    }

    public int getTotalComParms(String bankCode) {
        return reportRepository.countByComParams(bankCode);
    }

    public int getTotalDevice(String bankCode) {
        return reportRepository.countByCategoryYDevice(bankCode);
    }




    public List<MerchantInfoDTO> getAllMIDTID(String bankCode, int limit, int page) {
        int offset = page * limit;
        return reportRepository.findAllMIDTID(bankCode, offset, limit);
    }

    public int getTotalCountOfMIDTID(String bankCode) {
        return reportRepository.countByAllMIDTID(bankCode);
    }

    public List<MerchantInfoDTO> GetReportForSale(String bankCode) {
        return reportRepository.getReportsByBankCodeForSaleReport(bankCode);
    }

    public List<MerchantInfoDTO> GetReportForAmex(String bankCode) {
        return reportRepository.getReportsByBankCodeForAmexReport(bankCode);
    }

    public List<MerchantInfoDTO> getReportForDevices(String bankCode) {
        return reportRepository.downloadReportForDevices(bankCode);
    }

    public List<CommunicationDTO> getReportForComParams(String bankCode) {
        return reportRepository.downloadReportForComParams(bankCode);
    }



    public List<MerchantInfoDTO> GetReportForAllMIDTID(String bankCode) {
        return reportRepository.downloadReportForAllMIDTID(bankCode);
    }
}
