package com.movietheater.Java.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "KHACH_HANG")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class KhachHang {
    @Id
    @Column(name = "MA_KH", length = 50)
    private String maKh;

    @Column(name = "TEN_KH", length = 100)
    private String tenKh;

    @Column(name = "EMAIL", length = 100, unique = true)
    private String email;

    @Column(name = "SDT", length = 20)
    private String sdt;

    @Column(name = "MAT_KHAU", length = 100)
    private String matKhau;

    @Column(name = "DIA_CHI", length = 200)
    private String diaChi;
}
