package com.cinema.cinema_backend.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "KHACH_HANG")
@Data
@NoArgsConstructor
public class KhachHang {
    @Id
    @Column(name = "MA_KH")
    private String maKh;

    @Column(name = "TEN_KH")
    private String tenKh;

    @Column(name = "EMAIL", unique = true)
    private String email;

    @Column(name = "SDT")
    private String sdt;

    @Column(name = "MAT_KHAU")
    private String matKhau;

    @Column(name = "DIA_CHI")
    private String diaChi;

}
