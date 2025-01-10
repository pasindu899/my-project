package com.aiken.pos.admin.web.controller;

import com.aiken.pos.admin.dto.MerchantInfoDTO;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

public class DownloadReport {
    public static ByteArrayInputStream generateCsvReport(List<MerchantInfoDTO> merchantInfoDTOS) {
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            StringBuilder csvContent = new StringBuilder();
            csvContent.append("Bank Code,Serial Number,Merchant Name,Merchant Address,Category,MID,TID,Description\n");
            for (MerchantInfoDTO merchantInfoDTO : merchantInfoDTOS) {
                        csvContent.append(merchantInfoDTO.getBankCode()).append(",")
                        .append(merchantInfoDTO.getSerialNo()).append(",")
                        .append(merchantInfoDTO.getMerchantName()).append(",")
                        .append(quoteField(merchantInfoDTO.getMerchantAddress())).append(",")
                        .append(merchantInfoDTO.getCategory()).append(",")
                        .append(merchantInfoDTO.getMid()).append(",")
                        .append(merchantInfoDTO.getTid()).append(",")
                        .append(merchantInfoDTO.getDescription()).append("\n");
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

