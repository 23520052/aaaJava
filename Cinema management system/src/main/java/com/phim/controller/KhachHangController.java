
package com.phim.controller;


import com.phim.dao.KhachHangDAO;
import com.phim.model.KHACH_HANG;

import java.util.List;
public class KhachHangController {
    private KhachHangDAO khachHangDAO;

    public KhachHangController() {
        this.khachHangDAO = new KhachHangDAO(); // Khởi tạo DAO
    }

    // Thêm khách hàng
    public boolean addKhachHang(KHACH_HANG khachHang) {
        return khachHangDAO.insertKhachHang(khachHang);
    }

    // Cập nhật khách hàng
    public boolean updateKhachHang(KHACH_HANG khachHang) {
        return khachHangDAO.updateKhachHang(khachHang);
    }

    // Xóa khách hàng
    public boolean deleteKhachHang(String maKhach) {
        return khachHangDAO.deleteKhachHang(maKhach);
    }

    // Lấy tất cả khách hàng
    public List<KHACH_HANG> getAllKhachHang() {
        return khachHangDAO.getAllKhachHang();
    }

    // Tìm theo mã khách hàng
    public KHACH_HANG getKhachHangByMa(String maKhach) {
        return khachHangDAO.getKhachHangByMa(maKhach);
    }

    // Tìm theo tên khách hàng (LIKE)
    public List<KHACH_HANG> searchKhachHangByTen(String tenKhach) {
        return khachHangDAO.getKhachHangByTen(tenKhach);
    }

    // Tìm theo số điện thoại
    public KHACH_HANG getKhachHangBySDT(String soDienThoai) {
        return khachHangDAO.getKhachHangBySDT(soDienThoai);
    }

    // Kiểm tra khách hàng đã tồn tại theo SDT
    public boolean checkKhachHangExistsBySDT(String sdt) {
        return khachHangDAO.getKhachHangBySDT(sdt) != null;
    }

    // Đếm tổng số khách hàng
    public int countKhachHang() {
        List<KHACH_HANG> list = khachHangDAO.getAllKhachHang();
        return list != null ? list.size() : 0;
    }
}
