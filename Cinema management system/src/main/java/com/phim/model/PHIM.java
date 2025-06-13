
package com.phim.model;

public class PHIM {
    private String MA_PHIM;       // Mã phim duy nhất (primary key)
    private String TEN_PHIM;      // Tên phim
    private String DAO_DIEN;      // Tên đạo diễn
    private String THE_LOAI;      // Thể loại phim
    private int THOI_LUONG;       // Thời lượng phim (phút), phải > 0
    private String MO_TA;         // Mô tả ngắn về phim
    private String TRANG_THAI;    //'Sắp chiếu', 'Đang chiếu', 'Ngừng chiếu'
    
    // Constructor đầy đủ
    public PHIM(String maPhim, String tenPhim, String daoDien, String theLoai, int thoiLuong, String moTa, String TRANG_THAI) {
        this.MA_PHIM = maPhim;
        this.TEN_PHIM = tenPhim;
        this.DAO_DIEN = daoDien;
        this.THE_LOAI = theLoai;
        this.THOI_LUONG = thoiLuong;
        this.MO_TA = moTa;
        this.TRANG_THAI = TRANG_THAI;
    }
public PHIM() {
        
    }
    // Getters and Setters
    public String getMaPhim() {
        return MA_PHIM;
    }

    public void setMaPhim(String maPhim) {
        this.MA_PHIM = maPhim;
    }

    public String getTenPhim() {
        return TEN_PHIM;
    }

    public void setTenPhim(String tenPhim) {
        this.TEN_PHIM = tenPhim;
    }

    public String getDaoDien() {
        return DAO_DIEN;
    }

    public void setDaoDien(String daoDien) {
        this.DAO_DIEN = daoDien;
    }

    public String getTheLoai() {
        return THE_LOAI;
    }

    public void setTheLoai(String theLoai) {
        this.THE_LOAI = theLoai;
    }

    public int getThoiLuong() {
        return THOI_LUONG;
    }

    public void setThoiLuong(int thoiLuong) {
        this.THOI_LUONG = thoiLuong;
    }

    public String getMoTa() {
        return MO_TA;
    }

    public void setMoTa(String moTa) {
        this.MO_TA = moTa;
    }
    
    public String getTrangThai() {
        return TRANG_THAI;
    }

    public void setTrangThai(String tt) {
        this.TRANG_THAI = tt;
    }
}
