
package com.phim.controller;

import com.phim.dao.GheDAO;
import com.phim.dao.SuatChieuDAO;
import com.phim.model.GHE;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

import java.util.List;

public class GheController {
    private GheDAO gheDAO = new GheDAO();

    // Lấy tất cả ghế
    public List<GHE> getAllGhe() {
        return gheDAO.getAllGhe();
    }

    // Lấy danh sách ghế theo mã phòng
    public List<GHE> getGheByMaPhong(String maPhong) {
        if (maPhong == null || maPhong.trim().isEmpty()) {
            System.out.println("❌ Mã phòng không hợp lệ.");
            return null;
        }
        return gheDAO.getGheByPhong(maPhong);
    }

    // Lấy danh sách ghế theo loại ghế
    public List<GHE> getGheByLoai(String loaiGhe) {
        if (loaiGhe == null || loaiGhe.trim().isEmpty()) {
            System.out.println("❌ Loại ghế không hợp lệ.");
            return null;
        }
        return gheDAO.getGheByLoai(loaiGhe);
    }

    // Thêm ghế mới
    public boolean addGhe(GHE ghe) {
        if (ghe == null || ghe.getMA_PHONG() == null || ghe.getHANG() == null || ghe.getCOT() < 0) {
            System.out.println("❌ Dữ liệu ghế không hợp lệ.");
            return false;
        }
        boolean result = gheDAO.addGhe(ghe);
        if (result) {
            System.out.println("✅ Thêm ghế thành công.");
        } else {
            System.out.println("❌ Thêm ghế thất bại. Có thể vị trí đã tồn tại.");
        }
        return result;
    }

    // Cập nhật ghế
    public boolean updateGhe(GHE ghe) {
        if (ghe == null || ghe.getMA_GHE() == null || ghe.getMA_GHE().trim().isEmpty()) {
            System.out.println("❌ Mã ghế không hợp lệ khi cập nhật.");
            return false;
        }
        boolean result = gheDAO.updateGhe(ghe);
        if (result) {
            System.out.println("✅ Cập nhật ghế thành công.");
        } else {
            System.out.println("❌ Cập nhật ghế thất bại. Kiểm tra lại mã ghế.");
        }
        return result;
    }

    // Xóa ghế theo mã
    public boolean deleteGhe(String maGhe) {
        if (maGhe == null || maGhe.trim().isEmpty()) {
            System.out.println("❌ Mã ghế không hợp lệ.");
            return false;
        }
        boolean result = gheDAO.deleteGhe(maGhe);
        if (result) {
            System.out.println("✅ Xóa ghế thành công.");
        } else {
            System.out.println("❌ Xóa ghế thất bại. Mã ghế không tồn tại hoặc đang được sử dụng.");
        }
        return result;
    }
    
    public List<GHE> layGheTrong(String maPhim, LocalDate ngay, LocalTime gio) {
        SuatChieuDAO suatChieuDAO = new SuatChieuDAO();
        GheDAO gheDAO = new GheDAO();

        String maSuat = suatChieuDAO.getMaSuatChieu(maPhim, ngay, gio);
        if (maSuat != null) {
            return gheDAO.getGheTrongBySuatChieu(maSuat);
        }
        return new ArrayList<>(); // Trả về danh sách rỗng nếu không tìm thấy suất chiếu
    }
    
    
    public List<GHE> layGheTrongTheoSuatChieu(String maSuat) {
        return gheDAO.getGheTrongBySuatChieu(maSuat);
    }
    
    // Trong GheController
    public String layMaGheTheoTen(String tenGhe){
        return gheDAO.layMaGheTheoTen(tenGhe);
    }
    
}
