package com.movietheater.Java.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class PaymentResultDTO {
    private String maThanhToan;
    private LocalDate ngayThanhToan;
    private double tongTien;
    private String phuongThuc;
    private List<String> gheDaDat;

    public PaymentResultDTO(String maThanhToan, LocalDate ngayThanhToan, Double tongTien, String phuongThuc, List<String> gheIds) {
        this.maThanhToan = maThanhToan;
        this.ngayThanhToan = ngayThanhToan;
        this.tongTien = tongTien;
        this.phuongThuc = phuongThuc;
        this.gheDaDat = gheIds;
    }
}
