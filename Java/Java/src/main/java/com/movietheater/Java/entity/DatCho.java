package com.movietheater.Java.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "DAT_CHO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DatCho {

    @Id
    @Column(name = "MA_DAT", length = 50)
    private String maDat;

    @Column(name = "NGAY_DAT")
    private LocalDateTime ngayDat;

    @ManyToOne
    @JoinColumn(name = "MA_KH")
    private KhachHang khachHang;

    @ManyToOne
    @JoinColumn(name = "MA_SUAT")
    private SuatChieu suatChieu;

    @OneToMany(mappedBy = "datCho", cascade = CascadeType.ALL)
    private List<ChiTietDatCho> chiTietDatChos;  // mỗi lần đặt có nhiều ghế
}
