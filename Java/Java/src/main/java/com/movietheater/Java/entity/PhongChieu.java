package com.movietheater.Java.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "PHONG_CHIEU")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PhongChieu {
    @Id
    @Column(name = "MA_PHONG", length = 10)
    private String maPhong;

    @Column(name = "TEN_PHONG", length = 50)
    private String tenPhong;

    @Column(name = "SO_GHE")
    private Integer soGhe;
}
