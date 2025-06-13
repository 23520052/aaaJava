package com.phim.controller;

import com.phim.dao.ThanhToanDAO;
import com.phim.model.ChartDataModel;
import com.phim.model.THANH_TOAN;
import java.math.BigDecimal;
import java.util.List;
import java.time.LocalDate;
import java.util.Map;

public class ThanhToanController {
    private ThanhToanDAO thanhToanDAO = new ThanhToanDAO();

    
    
    // Lấy tất cả thanh toán
    public List<THANH_TOAN> getAllThanhToan() {
        return thanhToanDAO.getAll();
    }

    // Lấy danh sách thanh toán theo mã đặt chỗ
    public THANH_TOAN getThanhToanByMaDatCho(String maDatCho) {
        if (maDatCho == null || maDatCho.trim().isEmpty()) {
            System.out.println("❌ Mã đặt chỗ không hợp lệ.");
            return null;
        }
        return thanhToanDAO.getByMaDat(maDatCho);
    }

    // Thêm thanh toán mới
    public boolean addThanhToan(THANH_TOAN thanhToan) {
        if (thanhToan == null || thanhToan.getMaDatCho() == null || thanhToan.getPhuongThuc() == null || thanhToan.getSoTien() < 0) {
            System.out.println("❌ Dữ liệu thanh toán không hợp lệ.");
            return false;
        }
        boolean result = thanhToanDAO.insert(thanhToan);
        if (result) {
            System.out.println("✅ Thêm thanh toán thành công.");
        } else {
            System.out.println("❌ Thêm thanh toán thất bại. Kiểm tra dữ liệu đầu vào.");
        }
        return result;
    }

    // Cập nhật thanh toán (chỉ cập nhật MANV và phương thức)
    public boolean updateThanhToan(String maThanhToan, int maNV, String phuongThuc) {
        if (maThanhToan == null || maThanhToan.trim().isEmpty() || phuongThuc == null || phuongThuc.trim().isEmpty()) {
            System.out.println("❌ Dữ liệu cập nhật không hợp lệ.");
            return false;
        }
        boolean result = thanhToanDAO.update(maThanhToan, maNV, phuongThuc);
        if (result) {
            System.out.println("✅ Cập nhật thanh toán thành công.");
        } else {
            System.out.println("❌ Cập nhật thất bại. Kiểm tra mã thanh toán.");
        }
        return result;
    }

    // Xóa thanh toán
    public boolean deleteThanhToan(String maThanhToan) {
        if (maThanhToan == null || maThanhToan.trim().isEmpty()) {
            System.out.println("❌ Mã thanh toán không hợp lệ.");
            return false;
        }
        boolean result = thanhToanDAO.delete(maThanhToan);
        if (result) {
            System.out.println("✅ Xóa thanh toán thành công.");
        } else {
            System.out.println("❌ Xóa thanh toán thất bại. Mã không tồn tại hoặc đang được sử dụng.");
        }
        return result;
    }
    
    // Thống kê tổng tiền theo ngày/tháng/năm
    public double getTongTienTheoNgay() {
        LocalDate today = LocalDate.now();
        return thanhToanDAO.getTongTienTheoNgay(today.getDayOfMonth(), today.getMonthValue(), today.getYear());
    }

    public double getTongTienTheoThangNam() {
        LocalDate today = LocalDate.now();  // Lấy ngày hiện tại
        int thang = today.getMonthValue(); // Tháng hiện tại
        int nam = today.getYear();         // Năm hiện tại
        return thanhToanDAO.getTongTienTheoThangNam(thang, nam);
    }

    public double getTongTienTheoNam(int nam) {
        return thanhToanDAO.getTongTienTheoNam(nam);
    }

    // Lọc danh sách theo thời gian tùy chọn
    public List<THANH_TOAN> getTheoThoiGian(Integer ngay, Integer thang, Integer nam) {
        return thanhToanDAO.getByThoiGian(ngay, thang, nam);
    }

    // Lọc theo phương thức thanh toán
    public List<THANH_TOAN> getTheoPhuongThuc(String phuongThuc) {
        if (phuongThuc == null || phuongThuc.trim().isEmpty()) {
            System.out.println("❌ Phương thức thanh toán không hợp lệ.");
            return null;
        }
        return thanhToanDAO.getByPhuongThuc(phuongThuc);
    }
    
    // Đếm tổng số thanh toán
    public int getSoLuongThanhToan() {
        return thanhToanDAO.countAll();
    }
    
    //Tổng toàn bộ doanh thu 
    public double getTongDoanhThu() {
        return thanhToanDAO.getTongDoanhThu();
    }
    
    public List<Integer> getAvailableYears() {
        return thanhToanDAO.getAvailableYears();
    }
    
    public List<ChartDataModel> getYearlyRevenue() {
        return thanhToanDAO.getDoanhThuTheoNam();
    }

    public List<ChartDataModel> getMonthlyRevenue(int year) {
        return thanhToanDAO.getDoanhThuTheoThangTrongNam(year);
    }
    
    public List<ChartDataModel> getWeeklyRevenueByYear(int year) {
        return thanhToanDAO.getDoanhThuTheoTuanTrongNam(year);
    }
    
    public List<Map<String, Object>> getTopEmployeesByRevenue(int topN) {
        return thanhToanDAO.getTopEmployeesByRevenue(topN);
    }  
    
    public THANH_TOAN getById(String maThanhToan) {
        if (maThanhToan == null || maThanhToan.trim().isEmpty()) {
            System.out.println("❌ Mã thanh toán không hợp lệ.");
            return null;
        }
        return thanhToanDAO.getById(maThanhToan);
    }
    
    // Tính tổng tiền đặt chỗ theo mã đặt
    public BigDecimal getTongTienDatCho(String maDatCho) {
        if (maDatCho == null || maDatCho.trim().isEmpty()) {
            System.out.println("❌ Mã đặt chỗ không hợp lệ.");
            return BigDecimal.ZERO;
        }
        return thanhToanDAO.tinhTongTienDatCho(maDatCho);
    }
    
    public List<THANH_TOAN> search(String keyword){
        return thanhToanDAO.search(keyword);
    }
    
    public void update(THANH_TOAN tt) {
    }
}
