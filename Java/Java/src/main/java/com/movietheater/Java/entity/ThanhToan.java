package com.movietheater.Java.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "THANH_TOAN")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ThanhToan {
    @Id
    @Column(name = "MA_THANH_TOAN", length = 50)
    private String maThanhToan;

    @Column(name = "PHUONG_THUC", length = 50)
    private String phuongThuc;

    @Column(name = "TONG_TIEN")
    private Double tongTien;

    @Column(name = "NGAY_THANH_TOAN")
    private LocalDate ngayThanhToan;

    @OneToOne
    @JoinColumn(name = "MA_DAT", unique = true)
    private DatCho datCho;
}
