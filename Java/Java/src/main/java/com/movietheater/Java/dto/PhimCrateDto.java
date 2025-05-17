// PhimCreateDto.java
package com.movietheater.Java.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PhimCreateDto {
    private String maPhim;
    private String tenPhim;
    private String daoDien;
    private String theLoai;
    private Integer thoiLuong;
    private String moTa;
}