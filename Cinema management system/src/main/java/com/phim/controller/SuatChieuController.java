
package com.phim.controller;

import com.phim.dao.SuatChieuDAO;
import com.phim.model.PHIM;
import com.phim.model.PHONG_CHIEU;
import com.phim.model.SUAT_CHIEU;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public class SuatChieuController {
    private SuatChieuDAO suatChieuDAO;

    public SuatChieuController() {
        this.suatChieuDAO = new SuatChieuDAO();
    }

    // Thêm suất chiếu
    public boolean addSuatChieu(SUAT_CHIEU suat) {
        return suatChieuDAO.addSuatChieu(suat);
    }

    // Xóa suất chiếu
    public boolean deleteSuatChieu(String maSuat) {
        return suatChieuDAO.deleteSuatChieu(maSuat);
    }

    // Cập nhật suất chiếu
    public boolean updateSuatChieu(SUAT_CHIEU suat) {
        return suatChieuDAO.updateSuatChieu(suat);
    }

    // Lấy tất cả suất chiếu
    public List<SUAT_CHIEU> getAllSuatChieu() {
        return suatChieuDAO.getAllSuatChieu();
    }

    // Lấy danh sách suất chiếu theo mã phim
    public List<SUAT_CHIEU> getSuatChieuByPhim(String maPhim) {
        return suatChieuDAO.getSuatChieuByPhim(maPhim);
    }

    // Lấy danh sách suất chiếu theo mã phòng
    public List<SUAT_CHIEU> getSuatChieuByPhong(String maPhong) {
        return suatChieuDAO.getSuatChieuByPhong(maPhong);
    }

    // Kiểm tra trùng giờ chiếu
    public boolean isThoiGianTrung(String maPhong, LocalDateTime thoiGianChieu, int thoiLuongPhut, String maSuatBoQua) {
        return suatChieuDAO.isThoiGianTrung(maPhong, thoiGianChieu, thoiLuongPhut, maSuatBoQua);
    }

    // Lấy thời lượng phim
    public int getThoiLuongPhim(String maPhim) {
        return suatChieuDAO.getThoiLuongPhim(maPhim);
    }
    
    public List<LocalTime> layGioTheoNgay(String maPhim, LocalDate ngayChieu) {
        return suatChieuDAO.getGioChieuTheoNgay(maPhim, ngayChieu);
    }
    
    // Lấy danh sách suất chiếu theo tên phim
    public List<SUAT_CHIEU> laySuatChieuTheoTenPhim(String tenPhim) {
        return suatChieuDAO.getSuatChieuByTenPhim(tenPhim);
    }
    
    public String getFilmNameById(String maPhim) {
        return suatChieuDAO.getFilmNameById(maPhim);
    }
    
    public String getRoomNameById(String maPhong) {
        return suatChieuDAO.getRoomNameById(maPhong);
    }
    
    public List<PHONG_CHIEU> getAllPhongChieu(){
        return suatChieuDAO.getAllPhongChieu();
}

    public List<PHIM> getAllPhim() {
        return suatChieuDAO.getAllPhim();

    }
    
    public SUAT_CHIEU getSuatChieuByMaSuat(String maSuat){
        return suatChieuDAO.getSuatChieuByMaSuat(maSuat);
    }

    public List<SUAT_CHIEU> getSuatChieuByTenPhong(String tenPhong){
        return suatChieuDAO.getSuatChieuByTenPhong(tenPhong);
    }

}




    
    