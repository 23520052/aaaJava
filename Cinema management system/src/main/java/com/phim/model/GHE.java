package com.phim.model;

import java.math.BigDecimal;

public class GHE {
    private String MA_GHE; // MA_GHE
    private String MA_PHONG;// MA_PHONG
    private String HANG;
    private int COT;
    private String LOAI_GHE; // LOAI_GHE
    private String TRANG_THAI; 

    public GHE(String maGhe, String maPhong,String hang, int cot,  String trangthai, String loaiGhe) {
        this.MA_GHE = maGhe;
        this.HANG = hang;
        this.COT = cot;
        this.MA_PHONG = maPhong;
        this.LOAI_GHE = loaiGhe;
        this.TRANG_THAI = trangthai;
    }
// Getter & Setter
    public String getMA_GHE() {
        return MA_GHE;
    }

    public void setMA_GHE(String MA_GHE) {
        this.MA_GHE = MA_GHE;
    }

    public String getMA_PHONG() {
        return MA_PHONG;
    }

    public void setMA_PHONG(String MA_PHONG) {
        this.MA_PHONG = MA_PHONG;
    }

    public String getHANG() {
        return HANG;
    }

    public void setHANG(String HANG) {
        this.HANG = HANG;
    }

    public int getCOT() {
        return COT;
    }

    public void setCOT(int COT) {
        this.COT = COT;
    }

    public String getLOAI_GHE() {
        return LOAI_GHE;
    }

    public void setLOAI_GHE(String LOAI_GHE) {
        this.LOAI_GHE = LOAI_GHE;
    }

    public String getTRANG_THAI() {
        return TRANG_THAI;
    }

    public void setTRANG_THAI(String TRANG_THAI) {
        this.TRANG_THAI = TRANG_THAI;
    }
    
    public String getTenGhe() {
        return HANG + COT; // Ví dụ: A5
    }
   
    
}
