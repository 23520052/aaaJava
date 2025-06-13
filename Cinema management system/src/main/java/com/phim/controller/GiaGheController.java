package com.phim.controller;

import com.phim.dao.GiaGheDAO;
import com.phim.model.GIA_GHE;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public class GiaGheController {
    private GiaGheDAO giaGheDAO = new GiaGheDAO();

    // Lấy toàn bộ danh sách giá ghế
    public List<GIA_GHE> getAllGiaGhe() {
        return giaGheDAO.getAllGiaGhe();
    }

    // Lấy giá theo loại ghế
    public GIA_GHE getGiaByLoaiGhe(String loaiGhe) {
        if (loaiGhe == null || loaiGhe.trim().isEmpty()) {
            System.out.println("❌ Loại ghế không hợp lệ.");
            return null;
        }
        return giaGheDAO.getGiaByLoaiGhe(loaiGhe);
    }

    // Thêm loại ghế mới
    public boolean addGiaGhe(String loaiGhe, BigDecimal gia) {
        if (loaiGhe == null || loaiGhe.trim().isEmpty() || gia == null || gia.compareTo(BigDecimal.ZERO) < 0) {
            System.out.println("❌ Dữ liệu không hợp lệ khi thêm loại ghế.");
            return false;
        }

        boolean result = giaGheDAO.addGiaGhe(loaiGhe, gia);
        if (result) {
            System.out.println("✅ Thêm loại ghế thành công.");
        } else {
            System.out.println("❌ Thêm loại ghế thất bại (có thể đã tồn tại).");
        }
        return result;
    }

    // Cập nhật giá cho loại ghế
    public boolean updateGiaByLoaiGhe(String loaiGhe, BigDecimal giaMoi) {
        if (loaiGhe == null || loaiGhe.trim().isEmpty() || giaMoi == null || giaMoi.compareTo(BigDecimal.ZERO) < 0) {
            System.out.println("❌ Dữ liệu không hợp lệ khi cập nhật giá.");
            return false;
        }

        boolean result = giaGheDAO.updateGiaByLoaiGhe(loaiGhe, giaMoi);
        if (result) {
            System.out.println("✅ Cập nhật giá ghế thành công.");
        } else {
            System.out.println("❌ Cập nhật giá thất bại. Kiểm tra lại loại ghế.");
        }
        return result;
    }
    
    public Map<String, BigDecimal> layTatCaGia() {
        return giaGheDAO.layTatCaGia();
    }
}
