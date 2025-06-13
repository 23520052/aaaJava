package com.phim.controller;

import com.phim.dao.DatChoDAO;
import com.phim.model.DAT_CHO;
import com.phim.model.KHACH_HANG;
import java.time.LocalDateTime;
import java.util.List;

public class DatChoController {
    private DatChoDAO datChoDAO = new DatChoDAO();

    // Lấy tất cả đặt chỗ
    public List<DAT_CHO> getAllDatCho() {
        return datChoDAO.getAllDatCho();
    }

    // Lấy danh sách đặt chỗ theo mã khách hàng
    public List<DAT_CHO> getDatChoByKhach(String maKhach) {
        if (maKhach == null || maKhach.trim().isEmpty()) {
            System.out.println("❌ Mã khách hàng không hợp lệ.");
            return null;
        }
        return datChoDAO.getDatChoByKhach(maKhach);
    }

    // Lấy danh sách đặt chỗ theo mã suất chiếu
    public List<DAT_CHO> getDatChoBySuat(String maSuat) {
        if (maSuat == null || maSuat.trim().isEmpty()) {
            System.out.println("❌ Mã suất chiếu không hợp lệ.");
            return null;
        }
        return datChoDAO.getDatChoBySuatChieu(maSuat);
    }

    // Lấy danh sách đặt chỗ theo ngày đặt (không tính giờ)
    public List<DAT_CHO> getDatChoByNgay(LocalDateTime ngayDat) {
        if (ngayDat == null) {
            System.out.println("❌ Ngày đặt không hợp lệ.");
            return null;
        }
        return datChoDAO.getDatChoByNgay(ngayDat);
    }

    // Thêm đặt chỗ mới
    public boolean addDatCho(DAT_CHO datCho) {
        if (datCho == null || datCho.getMaKhach() == null || datCho.getMaSuat() == null || datCho.getThoiGianDat() == null) {
            System.out.println("❌ Dữ liệu đặt chỗ không hợp lệ.");
            return false;
        }
        boolean result = datChoDAO.addDatCho(datCho);
        if (result) {
            System.out.println("✅ Thêm đặt chỗ thành công.");
        } else {
            System.out.println("❌ Thêm đặt chỗ thất bại. Kiểm tra dữ liệu đầu vào.");
        }
        return result;
    }

    // Cập nhật đặt chỗ
    public boolean updateDatCho(DAT_CHO datCho) {
        if (datCho == null || datCho.getMaDatCho() == null || datCho.getMaDatCho().trim().isEmpty()) {
            System.out.println("❌ Mã đặt chỗ không hợp lệ.");
            return false;
        }
        boolean result = datChoDAO.updateDatCho(datCho);
        if (result) {
            System.out.println("✅ Cập nhật đặt chỗ thành công.");
        } else {
            System.out.println("❌ Cập nhật đặt chỗ thất bại.");
        }
        return result;
    }

    // Xóa đặt chỗ
    public boolean deleteDatCho(String maDatCho) {
        if (maDatCho == null || maDatCho.trim().isEmpty()) {
            System.out.println("❌ Mã đặt chỗ không hợp lệ.");
            return false;
        }
        boolean result = datChoDAO.deleteDatCho(maDatCho);
        if (result) {
            System.out.println("✅ Xóa đặt chỗ thành công.");
        } else {
            System.out.println("❌ Xóa đặt chỗ thất bại. Mã không tồn tại hoặc đang được sử dụng.");
        }
        return result;
    }
    
    public boolean themKhachHang(KHACH_HANG kh){return true;};
}
