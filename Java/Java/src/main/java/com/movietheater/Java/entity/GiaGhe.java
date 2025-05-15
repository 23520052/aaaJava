package com.movietheater.Java.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "GIA_GHE")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GiaGhe {
    @Id
    @Column(name = "LOAI_GHE", length = 20)
    private String loaiGhe;

    @Column(name = "GIA")
    private Double gia;
}
