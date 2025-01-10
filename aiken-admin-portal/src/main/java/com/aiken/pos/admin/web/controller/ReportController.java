package com.aiken.pos.admin.web.controller;

import com.aiken.pos.admin.constant.Endpoint;
import com.aiken.pos.admin.dto.CommunicationDTO;
import com.aiken.pos.admin.dto.MerchantInfoDTO;
import com.aiken.pos.admin.helper.SessionHelper;
import com.aiken.pos.admin.service.ReportService;
import com.aiken.pos.admin.util.LoginUserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayInputStream;
import java.util.List;

@Controller
public class ReportController {
    private final ReportService reportService;

    @Autowired
    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping(value = Endpoint.URL_VIEW_REPORTS)
    public String viewReportsPage1(Model model) {
        // load login user
        LoginUserUtil.loadLoginUser(model);
        // Add model attribute for form data
        model.addAttribute("formData", new ReportForm());

        return Endpoint.PAGES_VIEW_REPORTS;
    }
    public static class ReportForm {
        private String bankName;

        public String getBankName() {
            return bankName;
        }

        public void setBankName(String bankName) {
            this.bankName = bankName;
        }
    }

    //View reports for default Sale MID/TIDs   -> Report 1
    @PostMapping(value = Endpoint.URL_VIEW_REPORT_FOR_SALE_MID_TID)
    public String showReportByBankCode(@ModelAttribute("formData") ReportForm formData, Model model, HttpServletRequest request,
                                       @RequestParam(value = "limit", defaultValue = "10") String limit,
                                       @RequestParam(value = "page", defaultValue = "0") String page) {
        // Load login user
        LoginUserUtil.loadLoginUser(model);
        SessionHelper.removeFormInSession(request);

        // Get total number of records
        int totalRecords = reportService.getTotalRecords(formData.getBankName());

        // Calculate total number of pages
        int totalPages = (int) Math.ceil((double) totalRecords / Integer.parseInt(limit));

        // Get reports for the specified bank code, limit, and page
        List<MerchantInfoDTO> merchantInfoDTO = reportService.getReportsByBankCode(formData.getBankName(), Integer.parseInt(limit), Integer.parseInt(page));
        // Add attributes to the model
        model.addAttribute("merchantInfoDTO", merchantInfoDTO);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("currentPage", Integer.parseInt(page));

        return Endpoint.PAGES_VIEW_REPORTS_SALEMIDTID;
    }

    //View reports for default Sale MID/TIDs   -> Report 1
    @GetMapping(value = Endpoint.URL_VIEW_REPORT_FOR_SALE_MID_TID)
    public String showReportByBankCode(Model model, HttpServletRequest request,
                                       @RequestParam(value = "bank") String bank,
                                       @RequestParam(value = "limit", defaultValue = "10") String limit,
                                       @RequestParam(value = "page", defaultValue = "1") String page) {
        // Load login user
        LoginUserUtil.loadLoginUser(model);
        SessionHelper.removeFormInSession(request);

        // Get total number of records
        int totalRecords = reportService.getTotalRecords(bank);

        // Calculate total number of pages
        int totalPages = (int) Math.ceil((double) totalRecords / Integer.parseInt(limit));

        // Get reports for the specified bank code, limit, and page
        List<MerchantInfoDTO> merchantInfoDTO = reportService.getReportsByBankCode(bank, Integer.parseInt(limit), Integer.parseInt(page));
        // Add attributes to the model
        model.addAttribute("merchantInfoDTO", merchantInfoDTO);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("currentPage", Integer.parseInt(page));

        return Endpoint.PAGES_VIEW_REPORTS_SALEMIDTID;
    }
    @PostMapping(value = Endpoint.URL_VIEW_REPORTS_BY_BANK_CODE_REPORT2)
    public String showReportByBankCodeAndCategory(@ModelAttribute("formData") ReportForm formData, Model model, HttpServletRequest request,
                                                  @RequestParam(value = "limit", defaultValue = "10") String limit,
                                                  @RequestParam(value = "page", defaultValue = "0") String page) {
        // Load login user
        LoginUserUtil.loadLoginUser(model);
        SessionHelper.removeFormInSession(request);

        // Get total number of records
        int totalRecords = reportService.getTotalRecordsByCategoryAndBankCode(formData.getBankName());

        // Calculate total number of pages
        int totalPages = (int) Math.ceil((double) totalRecords / Integer.parseInt(limit));

        // Get reports for the specified bank code, limit, and page
        List<MerchantInfoDTO> merchantInfoDTO = reportService.getReportsByCategoryAndBankCode(formData.getBankName(), Integer.parseInt(limit), Integer.parseInt(page));
        // Add attributes to the model
        model.addAttribute("merchantInfoDTO", merchantInfoDTO);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("currentPage", Integer.parseInt(page));
        model.addAttribute("selectedBank", formData.getBankName());

        return Endpoint.PAGES_VIEW_REPORTS_AMEXMIDTID;
    }

    @GetMapping(value = Endpoint.URL_VIEW_REPORTS_BY_BANK_CODE_REPORT2)
    public String showReportByBankCodeAndCategory(Model model, HttpServletRequest request,
                                                  @RequestParam(value = "bank") String bank,
                                                  @RequestParam(value = "limit", defaultValue = "10") String limit,
                                                  @RequestParam(value = "page", defaultValue = "0") String page) {

        // Load login user
        LoginUserUtil.loadLoginUser(model);
        SessionHelper.removeFormInSession(request);

        // Get total number of records
        int totalRecords = reportService.getTotalRecordsByCategoryAndBankCode(bank);

        // Calculate total number of pages
        int totalPages = (int) Math.ceil((double) totalRecords / Integer.parseInt(limit));

        // Get reports for the specified bank code, limit, and page
        List<MerchantInfoDTO> merchantInfoDTO = reportService.getReportsByCategoryAndBankCode(bank, Integer.parseInt(limit), Integer.parseInt(page));
        // Add attributes to the model
        model.addAttribute("merchantInfoDTO", merchantInfoDTO);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("currentPage", Integer.parseInt(page));
        model.addAttribute("selectedBank", bank);

        return Endpoint.PAGES_VIEW_REPORTS_AMEXMIDTID;
    }


    //View reports for default Amex MID/TIDs   -> Report 3
    @PostMapping(value = Endpoint.URL_VIEW_REPORT_FOR_ALL_MID_ID)
    public String showReportAllMIDTID(@ModelAttribute("formData") ReportForm formData, Model model, HttpServletRequest request,
                                       @RequestParam(value = "limit", defaultValue = "10") String limit,
                                       @RequestParam(value = "page", defaultValue = "0") String page) {
        // Load login user
        LoginUserUtil.loadLoginUser(model);
        SessionHelper.removeFormInSession(request);

        // Get total number of records
        int totalRecords = reportService.getTotalCountOfMIDTID(formData.getBankName());

        // Calculate total number of pages
        int totalPages = (int) Math.ceil((double) totalRecords / Integer.parseInt(limit));

        // Get reports for the specified bank code, limit, and page
        List<MerchantInfoDTO> merchantInfoDTO = reportService.getAllMIDTID(formData.getBankName(), Integer.parseInt(limit), Integer.parseInt(page));
        // Add attributes to the model
        model.addAttribute("merchantInfoDTO", merchantInfoDTO);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("currentPage", Integer.parseInt(page));

        return Endpoint.PAGES_VIEW_REPORTS_ALLMIDTID;
    }


    //View reports for default Amex MID/TIDs   -> Report 3
    @GetMapping(value = Endpoint.URL_VIEW_REPORT_FOR_ALL_MID_ID)
    public String showReportAllMIDTID(Model model, HttpServletRequest request,
                                       @RequestParam(value = "bank") String bank,
                                       @RequestParam(value = "limit", defaultValue = "10") String limit,
                                       @RequestParam(value = "page", defaultValue = "1") String page) {
        // Load login user
        LoginUserUtil.loadLoginUser(model);
        SessionHelper.removeFormInSession(request);

        // Get total number of records
        int totalRecords = reportService.getTotalCountOfMIDTID(bank);

        // Calculate total number of pages
        int totalPages = (int) Math.ceil((double) totalRecords / Integer.parseInt(limit));

        // Get reports for the specified bank code, limit, and page
        List<MerchantInfoDTO> merchantInfoDTO = reportService.getAllMIDTID(bank, Integer.parseInt(limit), Integer.parseInt(page));
        // Add attributes to the model
        model.addAttribute("merchantInfoDTO", merchantInfoDTO);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("currentPage", Integer.parseInt(page));

        return Endpoint.PAGES_VIEW_REPORTS_ALLMIDTID;
    }

    //View reports for default Devices   -> Report 4
    @PostMapping(value = Endpoint.URL_VIEW_REPORTS_BY_BANK_CODE_REPORT4)
    public String showReportByDevice(@ModelAttribute("formData") ReportForm formData, Model model, HttpServletRequest request,
                                                  @RequestParam(value = "limit", defaultValue = "10") String limit,
                                                  @RequestParam(value = "page", defaultValue = "0") String page) {
        // Load login user
        LoginUserUtil.loadLoginUser(model);
        SessionHelper.removeFormInSession(request);

        // Get total number of records
        int totalRecords = reportService.getTotalDevice(formData.getBankName());

        // Calculate total number of pages
        int totalPages = (int) Math.ceil((double) totalRecords / Integer.parseInt(limit));

        // Get reports for the specified bank code, limit, and page
        List<MerchantInfoDTO> merchantInfoDTO = reportService.getDevice(formData.getBankName(), Integer.parseInt(limit), Integer.parseInt(page));
        // Add attributes to the model
        model.addAttribute("merchantInfoDTO", merchantInfoDTO);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("currentPage", Integer.parseInt(page));
        model.addAttribute("selectedBank", formData.getBankName());

        return Endpoint.PAGE_VIEW_REPORTS_DEVICES;
    }


    //View reports for default Devices   -> Report 4
    @GetMapping(value = Endpoint.URL_VIEW_REPORTS_BY_BANK_CODE_REPORT4)
    public String showReportByDevice(Model model, HttpServletRequest request,
                                                  @RequestParam(value = "bank") String bank,
                                                  @RequestParam(value = "limit", defaultValue = "10") String limit,
                                                  @RequestParam(value = "page", defaultValue = "0") String page) {

        // Load login user
        LoginUserUtil.loadLoginUser(model);
        SessionHelper.removeFormInSession(request);

        // Get total number of records
        int totalRecords = reportService.getTotalDevice(bank);

        // Calculate total number of pages
        int totalPages = (int) Math.ceil((double) totalRecords / Integer.parseInt(limit));

        // Get reports for the specified bank code, limit, and page
        List<MerchantInfoDTO> merchantInfoDTO = reportService.getDevice(bank, Integer.parseInt(limit), Integer.parseInt(page));
        // Add attributes to the model
        model.addAttribute("merchantInfoDTO", merchantInfoDTO);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("currentPage", Integer.parseInt(page));
        model.addAttribute("selectedBank", bank);

        return Endpoint.PAGE_VIEW_REPORTS_DEVICES;
    }

    //View reports for default CommunicationParams   -> Report 5
    @PostMapping(value = Endpoint.URL_VIEW_REPORTS_BY_BANK_CODE_REPORT5)
    public String showReportByCommunicationParams(@ModelAttribute("formData") ReportForm formData, Model model, HttpServletRequest request,
                                     @RequestParam(value = "limit", defaultValue = "10") String limit,
                                     @RequestParam(value = "page", defaultValue = "0") String page) {
        // Load login user
        LoginUserUtil.loadLoginUser(model);
        SessionHelper.removeFormInSession(request);

        // Get total number of records
        int totalRecords = reportService.getTotalComParms(formData.getBankName());

        // Calculate total number of pages
        int totalPages = (int) Math.ceil((double) totalRecords / Integer.parseInt(limit));

        // Get reports for the specified bank code, limit, and page
        List<CommunicationDTO> communicationDTO = reportService.getComParam(formData.getBankName(), Integer.parseInt(limit), Integer.parseInt(page));
        // Add attributes to the model
        model.addAttribute("communicationDTO", communicationDTO);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("currentPage", Integer.parseInt(page));
        model.addAttribute("selectedBank", formData.getBankName());

        return Endpoint.PAGE_VIEW_REPORTS_COM_PARAMS;
    }


    //View reports for default CommunicationParams   -> Report 5
    @GetMapping(value = Endpoint.URL_VIEW_REPORTS_BY_BANK_CODE_REPORT5)
    public String showReportByCommunicationParams(Model model, HttpServletRequest request,
                                     @RequestParam(value = "bank") String bank,
                                     @RequestParam(value = "limit", defaultValue = "10") String limit,
                                     @RequestParam(value = "page", defaultValue = "0") String page) {

        // Load login user
        LoginUserUtil.loadLoginUser(model);
        SessionHelper.removeFormInSession(request);

        // Get total number of records
        int totalRecords = reportService.getTotalComParms(bank);

        // Calculate total number of pages
        int totalPages = (int) Math.ceil((double) totalRecords / Integer.parseInt(limit));

        // Get reports for the specified bank code, limit, and page
        List<CommunicationDTO> communicationDTO = reportService.getComParam(bank, Integer.parseInt(limit), Integer.parseInt(page));
        // Add attributes to the model
        model.addAttribute("communicationDTO", communicationDTO);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("currentPage", Integer.parseInt(page));
        model.addAttribute("selectedBank", bank);

        return Endpoint.PAGE_VIEW_REPORTS_COM_PARAMS;
    }


    //Download reports for default Sale MID/TIDs   -> Report 1
    @GetMapping(value = Endpoint.URL_DOWNLOAD_REPORT_FOR_SALE_MID_TID)
    public ResponseEntity<InputStreamResource> generateReportForSale(@RequestParam(value = "bank") String bank) {
        List<MerchantInfoDTO> report = reportService.GetReportForSale(bank);

        ByteArrayInputStream bis = DownloadReport.generateCsvReport(report);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=SALE_MID/TID_REPORT.csv");

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(new InputStreamResource(bis));
    }


    //Download reports for default Amex MID/TIDs   -> Report 2
    @GetMapping(value = Endpoint.URL_VIEW_REPORTS_BY_BANK_CODE_REPORT2_DOWNLOAD)
    public ResponseEntity<InputStreamResource> generateReportForAmex(@RequestParam(value = "bank") String bank,Model model) {
        List<MerchantInfoDTO> report = reportService.GetReportForAmex(bank);

        ByteArrayInputStream bis = DownloadReport.generateCsvReport(report);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=AMEX_MID/TID_REPORT.csv");

        model.addAttribute("selectedBank", bank);

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(new InputStreamResource(bis));

    }

    //Download reports for default All MID/TIDs   -> Report 3
    @GetMapping(value = Endpoint.URL_DOWNLOAD_REPORT_FOR_ALL_MID_TID)
    public ResponseEntity<InputStreamResource> generateReportForAllMIDTID(@RequestParam(value = "bank") String bank) {
        List<MerchantInfoDTO> report = reportService.GetReportForAllMIDTID(bank);

        ByteArrayInputStream bis = DownloadReport.generateCsvReport(report);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=ALL_MID/TID_REPORT.csv");

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(new InputStreamResource(bis));
    }

    //Download reports for default Devices   -> Report 4
    @GetMapping(value = Endpoint.URL_DOWNLOAD_REPORT_FOR_DEVICES)
    public ResponseEntity<InputStreamResource> generateReportForDevices(@RequestParam(value = "deviceType") String bank,Model model) {
        List<MerchantInfoDTO> report = reportService.getReportForDevices(bank);

        ByteArrayInputStream bis = DownloadReport.generateCsvReport(report);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=DEVICE_REPORT.csv");

        model.addAttribute("selectedBank", bank);

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(new InputStreamResource(bis));

    }

//    Download reports for communication Parameters   -> Report 5
    @GetMapping(value = Endpoint.URL_DOWNLOAD_REPORT_FOR_COMPARAMS)
    public ResponseEntity<InputStreamResource> generateReportForCommunicationParams(@RequestParam(value = "bank") String bank,Model model) {
        List<CommunicationDTO> report = reportService.getReportForComParams(bank);

        ByteArrayInputStream bis = DownloadComParamReport.generateCsvReportForComParams(report);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=COMMUNICATIONPARAMETER_REPORT.csv");

        model.addAttribute("selectedBank", bank);

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(new InputStreamResource(bis));

    }


}