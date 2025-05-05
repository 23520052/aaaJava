package com.cinema.cinema_backend.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "THANH_TOAN")
@Data
@NoArgsConstructor
public class ThanhToan {

    @Id
    @Column(name = "MA_TT")
    private String maTt;

    @ManyToOne
    @JoinColumn(name = "MA_DAT")
    private DatCho datCho;

    @Column(name = "PHUONG_THUC")
    private String phuongThuc;

    @Column(name = "SO_TIEN")
    private Double soTien;

    @Column(name = "NGAY_TT")
    private Date ngayThanhToan;



}
