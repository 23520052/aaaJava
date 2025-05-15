package com.movietheater.Java.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "PHIM")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Phim  {
    @Id
    @Column(name = "MA_PHIM", length = 10)
    private String maPhim;

    @Column(name = "TEN_PHIM", length = 100)
    private String tenPhim;

    @Column(name = "DAO_DIEN", length = 100)
    private String daoDien;

    @Column(name = "THE_LOAI", length = 50)
    private String theLoai;

    @Column(name = "THOI_LUONG")
    private Integer thoiLuong; // phút

    @Column(name = "MO_TA", length = 500)
    private String moTa;
}
