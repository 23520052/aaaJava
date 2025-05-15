package com.movietheater.Java.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "SUAT_CHIEU")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SuatChieu {
    @Id
    @Column(name = "MA_SUAT", length = 50)
    private String maSuat;

    @Column(name = "NGAY_CHIEU")
    private LocalDate ngayChieu;

    @Column(name = "GIO_CHIEU", length = 20)
    private String gioChieu;

    @ManyToOne
    @JoinColumn(name = "MA_PHIM")
    private Phim phim;

    @ManyToOne
    @JoinColumn(name = "MA_PHONG")
    private PhongChieu phongChieu;
}
