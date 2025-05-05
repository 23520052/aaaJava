package com.cinema.cinema_backend.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "SUAT_CHIEU")
@Data
@NoArgsConstructor
public class SuatChieu {
    @Id
    @Column(name = "MA_SUAT")
    private String maSuat;

    @Column(name = "MA_PHIM")
    private String maPhim;

    @Column(name = "MA_PHONG")
    private String maPhong;

    @Column(name = "NGAY_CHIEU")
    private Date ngayChieu;

    @Column(name = "GIO_CHIEU")
    private String gioChieu;

}
