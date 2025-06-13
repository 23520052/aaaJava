package com.phim.model;

import java.math.BigDecimal;

public class CHI_TIET_DAT_CHO {
    private String MA_DAT; // MA_DAT_CHO
    private String MA_GHE; // MA_GHE
    private BigDecimal GIA; // GIA

    public CHI_TIET_DAT_CHO(String maDatCho, String maGhe, BigDecimal maGia) {
        this.MA_DAT = maDatCho;
        this.MA_GHE = maGhe;
        this.GIA = maGia;
    }

    public String getMaDatCho() { return MA_DAT; }
    public void setMaDatCho(String maDatCho) { this.MA_DAT = maDatCho; }

    public String getMaGhe() { return MA_GHE; }
    public void setMaGhe(String maGhe) { this.MA_GHE = maGhe; }

    public BigDecimal getgia() { return GIA; }
    public void setgia(BigDecimal maGia) { this.GIA = maGia; }
}
