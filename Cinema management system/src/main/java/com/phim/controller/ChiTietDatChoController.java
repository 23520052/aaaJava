package com.phim.controller;

import com.phim.dao.ChiTietDatChoDAO;
import com.phim.model.CHI_TIET_DAT_CHO;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class ChiTietDatChoController {
    private ChiTietDatChoDAO chiTietDAO = new ChiTietDatChoDAO();

    // Lấy tất cả chi tiết đặt chỗ
    public List<CHI_TIET_DAT_CHO> getAllChiTiet() {
        return chiTietDAO.getAllChiTiet();
    }

    // Lấy theo mã đặt chỗ
    public List<CHI_TIET_DAT_CHO> getByMaDat(String maDat) {
        if (maDat == null || maDat.trim().isEmpty()) {
            System.out.println("❌ Mã đặt chỗ không hợp lệ.");
            return null;
        }
        return chiTietDAO.getByMaDat(maDat);
    }

    // Lấy theo mã ghế
    public List<CHI_TIET_DAT_CHO> getByMaGhe(String maGhe) {
        if (maGhe == null || maGhe.trim().isEmpty()) {
            System.out.println("❌ Mã ghế không hợp lệ.");
            return null;
        }
        return chiTietDAO.getByMaGhe(maGhe);
    }

    // Lấy theo ngày đặt
    public List<CHI_TIET_DAT_CHO> getByNgayDat(LocalDateTime ngay) {
        if (ngay == null) {
            System.out.println("❌ Ngày đặt không hợp lệ.");
            return null;
        }
        return chiTietDAO.getByNgayDat(ngay);
    }

    // Thêm 1 chi tiết đặt chỗ
    public boolean addChiTiet(CHI_TIET_DAT_CHO ct, String maPhim) {
        if (ct == null || ct.getMaDatCho() == null || ct.getMaGhe() == null || maPhim == null) {
            System.out.println("❌ Dữ liệu chi tiết đặt chỗ không hợp lệ.");
            return false;
        }
        boolean result = chiTietDAO.addChiTiet(ct, maPhim);
        if (result) {
            System.out.println("✅ Thêm chi tiết đặt chỗ thành công.");
        } else {
            System.out.println("❌ Thêm chi tiết đặt chỗ thất bại.");
        }
        return result;
    }

    // Thêm danh sách chi tiết
    public boolean addDanhSachChiTiet(String maDat, List<String> dsMaGhe, String maPhim) {
        if (maDat == null || dsMaGhe == null || dsMaGhe.isEmpty() || maPhim == null) {
            System.out.println("❌ Dữ liệu không hợp lệ khi thêm danh sách chi tiết.");
            return false;
        }
        boolean result = chiTietDAO.addDanhSachChiTiet(maDat, dsMaGhe, maPhim);
        if (result) {
            System.out.println("✅ Thêm danh sách chi tiết đặt chỗ thành công.");
        } else {
            System.out.println("❌ Thêm danh sách chi tiết thất bại.");
        }
        return result;
    }

    // Cập nhật chi tiết đặt chỗ
    public boolean updateChiTiet(CHI_TIET_DAT_CHO ct, String maPhim) {
        if (ct == null || ct.getMaDatCho() == null || ct.getMaGhe() == null || maPhim == null) {
            System.out.println("❌ Dữ liệu cập nhật không hợp lệ.");
            return false;
        }
        boolean result = chiTietDAO.updateChiTiet(ct, maPhim);
        if (result) {
            System.out.println("✅ Cập nhật chi tiết đặt chỗ thành công.");
        } else {
            System.out.println("❌ Cập nhật chi tiết thất bại.");
        }
        return result;
    }

    // Xóa chi tiết (toàn bộ hoặc 1 ghế)
    public boolean deleteChiTiet(String maDat, String maGhe) {
        if (maDat == null || maDat.trim().isEmpty()) {
            System.out.println("❌ Mã đặt chỗ không hợp lệ.");
            return false;
        }
        boolean result = chiTietDAO.deleteChiTiet(maDat, maGhe);
        if (result) {
            if (maGhe == null) {
                System.out.println("✅ Đã xóa toàn bộ chi tiết theo mã đặt chỗ.");
            } else {
                System.out.println("✅ Đã xóa chi tiết đặt chỗ theo mã ghế.");
            }
        } else {
            System.out.println("❌ Xóa chi tiết đặt chỗ thất bại.");
        }
        return result;
    }
    
    public boolean themChiTiet(CHI_TIET_DAT_CHO ct){
        return chiTietDAO.themChiTiet(ct);
    }
    
    public BigDecimal tinhTongTien(String maDat){
        return chiTietDAO.tinhTongTien(maDat);
    }
}
