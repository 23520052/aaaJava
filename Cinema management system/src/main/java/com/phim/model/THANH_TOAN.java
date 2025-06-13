
package com.phim.model;

import java.time.LocalDateTime;

public class THANH_TOAN {
    private String MA_THANH_TOAN; // MA_THANH_TOAN
    private String MA_DAT; // MA_DAT_CHO
    private int MaNV;
    private double TONG_TIEN; // SO_TIEN >= 0
    private LocalDateTime NGAY_THANH_TOAN; // THOI_GIAN_THANH_TOAN
    private String PHUONG_THUC; // Tiền mặt, Thẻ tín dụng, Ví điện tử, Chuyển khoản


    public THANH_TOAN(String maThanhToan, String maDatCho,int MaNV, double soTien, LocalDateTime thoiGianThanhToan, String phuongThuc) {
        this.MA_THANH_TOAN = maThanhToan;
        this.MA_DAT = maDatCho;
        this.MaNV = MaNV;
        this.TONG_TIEN = soTien;
        this.NGAY_THANH_TOAN = thoiGianThanhToan;
        this.PHUONG_THUC = phuongThuc;
    }

    public String getMaThanhToan() { return MA_THANH_TOAN; }
    public void setMaThanhToan(String maThanhToan) { this.MA_THANH_TOAN = maThanhToan; }

    public String getMaDatCho() { return MA_DAT; }
    public void setMaDatCho(String maDatCho) { this.MA_DAT = maDatCho; }

    public int getMANV() { return MaNV;}
    public void setMaNV(int manv) {this.MaNV = manv;}
    
    public double getSoTien() { return TONG_TIEN; }
    public void setSoTien(double soTien) { this.TONG_TIEN = soTien; }

    public LocalDateTime getThoiGianThanhToan() { return NGAY_THANH_TOAN; }
    public void setThoiGianThanhToan(LocalDateTime thoiGianThanhToan) { this.NGAY_THANH_TOAN = thoiGianThanhToan; }

    public String getPhuongThuc() { return PHUONG_THUC; }
    public void setPhuongThuc(String phuongThuc) { this.PHUONG_THUC = phuongThuc; }
    
}
