package com.aiken.pos.admin.web.controller;

import com.aiken.pos.admin.dto.CommunicationDTO;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
public class DownloadComParamReport {
    public static ByteArrayInputStream generateCsvReportForComParams(List<CommunicationDTO> communicationDTOs) {
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            StringBuilder csvContent = new StringBuilder();
            csvContent.append("Bank Code,Serial Number,Merchant Name,Merchant Address,Category,MID,TID,Description\n");
            for (CommunicationDTO communicationDTO : communicationDTOs) {
                csvContent.append(communicationDTO.getBankCode()).append(",")
                        .append(communicationDTO.getSerialNo()).append(",")
                        .append(communicationDTO.getMerchantName()).append(",")
                        .append(communicationDTO.getMid()).append(",")
                        .append(communicationDTO.getTid()).append(",")
                        .append(communicationDTO.getSim1()).append(",")
                        .append(communicationDTO.getSim2()).append(",")
                        .append(communicationDTO.getOperator1()).append(",")
                        .append(communicationDTO.getOperator2()).append(",")
                        .append(communicationDTO.getRef1()).append(",")
                        .append(communicationDTO.getRef2()).append("\n");
            }
            out.write(csvContent.toString().getBytes());
            return new ByteArrayInputStream(out.toByteArray());
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }
    // Method to enclose a field in double quotes if it contains commas
    private static String quoteField(String field) {
        if (field != null && field.contains(",")) {
            return "\"" + field + "\"";
        }
        return field;
    }
}
