package com.cinema.cinema_backend.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "DAT_CHO")
@Data
@NoArgsConstructor
public class DatCho {

    @Id
    @Column(name = "MA_DAT")
    private String maDat;

    @ManyToOne
    @JoinColumn(name = "MA_KH")
    private KhachHang khachHang;

    @ManyToOne
    @JoinColumn(name = "MA_SUAT")
    private SuatChieu suatChieu;

    @Column(name = "MA_GHE")
    private String maGhe;

    @Column(name = "NGAY_DAT")
    private Date ngayDat;
}
