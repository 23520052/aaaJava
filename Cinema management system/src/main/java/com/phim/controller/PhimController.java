
package com.phim.controller;

import com.phim.dao.PhimDAO;
import com.phim.model.PHIM;

import java.util.List;
import java.util.Map;
public class PhimController {
    private PhimDAO phimDAO;

    public PhimController() {
        this.phimDAO = new PhimDAO(); // Khởi tạo DAO
    }

    // Thêm phim mới
    public boolean addPhim(PHIM phim) {
        return phimDAO.themPhim(phim);
    }

    // Cập nhật thông tin phim
    public boolean updatePhim(PHIM phim) {
        return phimDAO.capNhatPhim(phim);
    }

    // Xóa phim
    public boolean deletePhim(String maPhim) {
        return phimDAO.xoaPhim(maPhim);
    }

    // Lấy danh sách tất cả phim
    public List<PHIM> getAllPhim() {
        return phimDAO.getAllPhim();
    }

    // Lấy phim theo mã
    public PHIM getPhimByMa(String maPhim) {
        return phimDAO.getPhimById(maPhim);
    }

    // Tìm phim theo tên gần đúng
    public List<PHIM> searchPhimByTen(String tenPhim) {
        return phimDAO.getPhimByTen(tenPhim);
    }

    // Lọc phim theo thể loại
    public List<PHIM> getPhimByTheLoai(String theLoai) {
        return phimDAO.getPhimByTheLoai(theLoai);
    }

    // Lọc phim theo trạng thái (ví dụ: Đang chiếu, Sắp chiếu)
    public List<PHIM> getPhimByTrangThai(String trangThai) {
        return phimDAO.getPhimByTrangThai(trangThai);
    }

    // Lấy thời lượng phim
    public int getThoiLuongPhim(String maPhim) {
        return phimDAO.getThoiLuongPhim(maPhim);
    }

    // Đếm tổng số phim
    public int countPhim() {
        List<PHIM> list = phimDAO.getAllPhim();
        return list != null ? list.size() : 0;
    }
    
    public List<Map<String, Object>> getTopSellingPhim(int limit) {
        return phimDAO.getTopSellingPhim(limit);
    }
    
    // Trong PhimController
    public String layMaPhimTheoTen(String tenPhim){
        return phimDAO.layMaPhimTheoTen(tenPhim);
    }
}
