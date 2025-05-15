package com.movietheater.Java.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class BookingInfoDTO {
    private String maDat;
    private LocalDateTime ngayDat;
    private String suatChieuId;
    private String khachHangId;
    private String trangThaiThanhToan;
    private List<String> danhSachGhe;
}
