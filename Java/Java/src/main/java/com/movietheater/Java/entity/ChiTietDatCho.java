package com.movietheater.Java.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "CHI_TIET_DAT_CHO")
@Data
@NoArgsConstructor
@AllArgsConstructor
@IdClass(ChiTietDatChoId.class)
public class ChiTietDatCho {

    @Id
    @ManyToOne
    @JoinColumn(name = "MA_DAT", referencedColumnName = "MA_DAT")
    private DatCho datCho;

    @Id
    @ManyToOne
    @JoinColumn(name = "MA_GHE", referencedColumnName = "MA_GHE")
    private Ghe ghe;

    @Column(name = "GIA")
    private Double gia;
}
