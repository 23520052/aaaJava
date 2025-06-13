package com.phim.model;

import java.math.BigDecimal;

public class GIA_GHE {
    private String LOAI_GHE; // LOAI_GHE
    private BigDecimal GIA; // GIA

    public GIA_GHE(String loaiGhe, BigDecimal gia) {
        this.LOAI_GHE = loaiGhe;
        this.GIA = gia;
    }

    public String getLoaiGhe() { return LOAI_GHE; }
    public void setLoaiGhe(String loaiGhe) { this.LOAI_GHE = loaiGhe; }

    public BigDecimal getGia() { return GIA; }
    public void setGia(BigDecimal gia) { this.GIA = gia; }
}
