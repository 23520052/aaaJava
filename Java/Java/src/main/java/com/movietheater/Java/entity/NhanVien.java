package com.movietheater.Java.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "NHANVIEN")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NhanVien{
    @Id
    @Column(name = "MA_NV", length = 10)
    private String maNv;

    @Column(name = "TEN_NV", length = 100)
    private String tenNv;

    @Column(name = "EMAIL", length = 100, unique = true)
    private String email;

    @Column(name = "SDT", length = 15)
    private String sdt;

    @Column(name = "MAT_KHAU", length = 100)
    private String matKhau;

    @Column(name = "CHUC_VU", length = 20)
    private String chucVu;
}
