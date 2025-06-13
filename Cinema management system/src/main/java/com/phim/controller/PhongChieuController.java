
package com.phim.controller;

import com.phim.dao.PhongChieuDAO;
import com.phim.model.GHE;
import com.phim.model.PHONG_CHIEU;

import java.util.List;

public class PhongChieuController {
    private PhongChieuDAO phongChieuDAO;

    public PhongChieuController() {
        this.phongChieuDAO = new PhongChieuDAO(); // Khởi tạo DAO
    }

    // Thêm phòng chiếu
    public boolean addPhongChieu(PHONG_CHIEU phong) {
        return phongChieuDAO.addPhongChieu(phong);
    }

    // Cập nhật thông tin phòng chiếu
    public boolean updatePhongChieu(PHONG_CHIEU phong) {
        return phongChieuDAO.updatePhongChieu(phong);
    }

    // Chuyển phòng về trạng thái bảo trì (giả lập xóa)
    public boolean deletePhongChieu(String maPhong) {
        return phongChieuDAO.deletePhong(maPhong);
    }

    // Lấy danh sách tất cả phòng chiếu
    public List<PHONG_CHIEU> getAllPhongChieu() {
        return phongChieuDAO.getAllPhongChieu();
    }

    // Lấy danh sách ghế theo mã phòng
    public List<GHE> getGheByMaPhong(String maPhong) {
        return phongChieuDAO.getGheByPhong(maPhong);
    }

    // Kiểm tra phòng chiếu đã tồn tại chưa
    public boolean isPhongChieuExists(String maPhong) {
        return phongChieuDAO.isPhongChieuExists(maPhong);
    }

    // Đếm số lượng phòng
    public int countPhongChieu() {
        List<PHONG_CHIEU> list = phongChieuDAO.getAllPhongChieu();
        return list != null ? list.size() : 0;
    }
}
