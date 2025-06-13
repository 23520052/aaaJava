
package com.phim.model;


public class PHONG_CHIEU {
    private String MA_PHONG; // MA_PHONG
    private String TEN_PHONG; // TEN_PHONG
    private String TRANG_THAI;//'Hoạt động', 'Bảo trì'

    public PHONG_CHIEU(String maPhong, String tenPhong, String TRANG_THAI) {
        this.MA_PHONG = maPhong;
        this.TEN_PHONG = tenPhong;
        this.TRANG_THAI = TRANG_THAI;
    }
    
    public PHONG_CHIEU() {
        
    }

    public String getMaPhong() { return MA_PHONG; }
    public void setMaPhong(String maPhong) { this.MA_PHONG = maPhong; }

    public String getTenPhong() { return TEN_PHONG; }
    public void setTenPhong(String tenPhong) { this.TEN_PHONG = tenPhong; }

    public String getTRANGTHAI() { return TRANG_THAI; }
    public void setTRANG_THAI(String TRANG_THAI) { this.TRANG_THAI = TRANG_THAI; }

}
