package com.movietheater.Java.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "GHE")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Ghe {
    @Id
    @Column(name = "MA_GHE", length = 50)
    private String maGhe;

    @Column(name = "HANG", length = 5)
    private String hang;

    @Column(name = "COT")
    private Integer cot;

    @Column(name = "TRANG_THAI", length = 50)
    private String trangThai;

    @ManyToOne
    @JoinColumn(name = "MA_PHONG")
    private PhongChieu phongChieu;

    @ManyToOne
    @JoinColumn(name = "LOAI_GHE")
    private GiaGhe giaGhe;

    @OneToMany(mappedBy = "ghe")
    private List<ChiTietDatCho> chiTietDatChos;
}
