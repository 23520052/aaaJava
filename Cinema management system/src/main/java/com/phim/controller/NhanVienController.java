package com.phim.controller;

import com.phim.dao.NhanVienDAO;
import com.phim.model.ChartDataModel;
import com.phim.model.NHAN_VIEN;
import com.phim.configs.EmailSender;
import java.util.List;
public class NhanVienController {
    private final NhanVienDAO nhanVienDAO;
    
    public NhanVienController() {
        nhanVienDAO = new NhanVienDAO();
    }

    // 1. Đếm tổng số nhân viên
    public int countNhanVien() {
        return nhanVienDAO.countNhanVien();
    }

    // 2. Lấy danh sách tất cả nhân viên
    public List<NHAN_VIEN> getAllNhanVien() {
        return nhanVienDAO.getAllNhanVien();
    }

    // 3. Thêm nhân viên
    public boolean addNhanVien(NHAN_VIEN nv) {
        if (nv.getFullName() == null || nv.getFullName().trim().isEmpty()) {
            return false;
        }
        if (nv.getEmail() == null || nv.getEmail().trim().isEmpty()) {
            return false;
        }
        return nhanVienDAO.addNhanVien(nv);
    }

    // 4. Cập nhật nhân viên
    public boolean updateNhanVien(NHAN_VIEN nv) {
        if (nv.getMaNv() <= 0) return false;
        if (nv.getFullName() == null || nv.getFullName().trim().isEmpty()) {
            return false;
        }
        return nhanVienDAO.updateNhanVien(nv);
    }

    // 5. Xóa nhân viên
    public boolean deleteNhanVien(int manv) {
        if (manv <= 0) return false;
        return nhanVienDAO.deleteNhanVien(manv);
    }

    // 6. Lấy nhân viên theo ID
    public NHAN_VIEN getNhanVienById(int manv) {
        if (manv <= 0) return null;
        return nhanVienDAO.getNhanVienById(manv);
    }

    // 7. Lấy nhân viên theo ID (overload kiểu Long)
    public NHAN_VIEN getNhanVienById(Long manv) {
        if (manv == null || manv <= 0) return null;
        return nhanVienDAO.getNhanVienById(manv.intValue());
    }

    // 8. Lấy nhân viên theo tên
    public NHAN_VIEN getNhanVienByName(String name) {
        if (name == null || name.trim().isEmpty()) return null;
        return nhanVienDAO.getNhanVienByName(name);
    }

    // 9. Đăng nhập
    public NHAN_VIEN login(String email, String password) {
        if (email != null) email = email.trim();
        if (password != null) password = password.trim();
        if (email.isEmpty() || password.isEmpty()) return null;
        return nhanVienDAO.login(email, password);
    }

    // 10. Quên mật khẩu (reset và gửi email)
    public boolean resetPassword(String email) {
        if (email == null || email.trim().isEmpty()) return false;
        email = email.trim();

        String newPassword = generateNewPassword();

        try {
            nhanVienDAO.forgetPassword(email, newPassword);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        try {
            EmailSender mailSender = new EmailSender();
            String subject = "MovieTheater AAA - Mật khẩu mới";
            String content = "Mật khẩu tạm thời của bạn là: " + newPassword + "\n"
                           + "Vui lòng đăng nhập và đổi mật khẩu ngay.";
            mailSender.sendEmail(email, subject, content);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // 11. Đổi mật khẩu
    public boolean updatePassword(String email, String oldPassword, String newPassword) {
        if (email == null || email.trim().isEmpty() ||
            oldPassword == null || newPassword == null ||
            oldPassword.trim().isEmpty() || newPassword.trim().isEmpty()) {
            return false;
        }
        return nhanVienDAO.updatePassword(email.trim(), oldPassword.trim(), newPassword.trim());
    }

    // 12. Lấy thống kê số lượng nhân viên theo vai trò
    public List<ChartDataModel> getNhanVienCountByRole() {
        return nhanVienDAO.getNhanVienCountByRole();
    }

    // 13. Hàm tạo mật khẩu ngẫu nhiên
    private String generateNewPassword() {
        String basePassword = "123456";
        int randomNumber = (int) (Math.random() * 900) + 100;
        return basePassword + randomNumber;
    }
    
    public List<NHAN_VIEN> getNhanVienByRole(String role) {
        return nhanVienDAO.getNhanVienByRole(role);
    }
  
    public List<ChartDataModel> getEmployeeCountByRole() {
        return nhanVienDAO.getNhanVienCountByRole();
    }
    
    public List<NHAN_VIEN> searchNhanVienByName(String keyword) {
        return nhanVienDAO.searchByName(keyword);
    }
}

