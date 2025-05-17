// SuatChieuResponseDto.java
package com.movietheater.Java.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SuatChieuResponseDto {
    private String maSuat;
    private LocalDate ngayChieu;
    private String gioChieu;
    private String maPhim;
    private String maPhong;
}