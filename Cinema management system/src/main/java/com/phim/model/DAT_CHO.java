
package com.phim.model;
import java.time.LocalDateTime;

public class DAT_CHO {
    private String MA_DAT; // MA_DAT_CHO
    private String MA_KH; // MA_KHACH
    private String MA_SUAT;
    private LocalDateTime NGAY_DAT; // THOI_GIAN_DAT

    public DAT_CHO(String maDatCho, String maKhach, String maSuat, LocalDateTime thoiGianDat) {
        this.MA_DAT = maDatCho;
        this.MA_KH = maKhach;
        this.MA_SUAT = maSuat;
        this.NGAY_DAT = thoiGianDat;
    }

    public String getMaDatCho() { return MA_DAT; }
    public void setMaDatCho(String maDatCho) { this.MA_DAT = maDatCho; }

    public String getMaKhach() { return MA_KH; }
    public void setMaKhach(String maKhach) { this.MA_KH = maKhach; }

    public String getMaSuat() { return MA_SUAT; }
    public void setMaSuat(String maSuat) { this.MA_SUAT = maSuat; }

    public LocalDateTime getThoiGianDat() { return NGAY_DAT; }
    public void setThoiGianDat(LocalDateTime thoiGianDat) { this.NGAY_DAT = thoiGianDat; }
}
