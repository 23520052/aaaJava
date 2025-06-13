
package com.phim.model;
import java.time.LocalDate;

public class KHACH_HANG {
    private String MA_KH; // MA_KHACH
    private String TEN_KH; // HO_TEN
    private String SDT; // SO_DIEN_THOAI
    private LocalDate NGAY_SINH; // EMAIL

    public KHACH_HANG(String maKhach, String hoTen, String soDienThoai, LocalDate ngayDangKy) {
        this.MA_KH = maKhach;
        this.TEN_KH = hoTen;
        this.SDT = soDienThoai;
        this.NGAY_SINH = ngayDangKy;
    }

    public String getMaKhach() { return MA_KH; }
    public void setMaKhach(String maKhach) { this.MA_KH = maKhach; }

    public String getHoTen() { return TEN_KH; }
    public void setHoTen(String hoTen) { this.TEN_KH = hoTen; }

    public String getSoDienThoai() { return SDT; }
    public void setSoDienThoai(String soDienThoai) { this.SDT = soDienThoai; }

    public LocalDate getNgayDangKy() { return NGAY_SINH; }
    public void setNgayDangKy(LocalDate ngayDangKy) { this.NGAY_SINH = ngayDangKy; }

}
