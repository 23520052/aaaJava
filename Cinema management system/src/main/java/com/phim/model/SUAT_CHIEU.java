package com.phim.model;
import java.time.LocalDateTime;

public class SUAT_CHIEU {
    private String MA_SUAT; // MA_SUAT
    private String MA_PHIM; // MA_PHIM
    private String MA_PHONG; // MA_PHONG
    private LocalDateTime THOI_GIAN_CHIEU; // THOI_GIAN_CHIEU

    public SUAT_CHIEU(String maSuat, String maPhim, String maPhong, LocalDateTime thoiGianChieu) {
        this.MA_SUAT = maSuat;
        this.MA_PHIM = maPhim;
        this.MA_PHONG = maPhong;
        this.THOI_GIAN_CHIEU = thoiGianChieu;
    }
    
    public SUAT_CHIEU() {
    }

    public String getMaSuat() { return MA_SUAT; }
    public void setMaSuat(String maSuat) { this.MA_SUAT = maSuat; }

    public String getMaPhim() { return MA_PHIM; }
    public void setMaPhim(String maPhim) { this.MA_PHIM = maPhim; }

    public String getMaPhong() { return MA_PHONG; }
    public void setMaPhong(String maPhong) { this.MA_PHONG = maPhong; }

    public LocalDateTime getThoiGianChieu() { return THOI_GIAN_CHIEU; }
    public void setThoiGianChieu(LocalDateTime thoiGianChieu) { this.THOI_GIAN_CHIEU = thoiGianChieu; }
}
