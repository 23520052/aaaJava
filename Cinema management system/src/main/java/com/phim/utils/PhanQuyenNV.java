package com.phim.utils;

import com.phim.model.NHAN_VIEN;
        
public class PhanQuyenNV {
    private static PhanQuyenNV instance;  // Biến singleton
    private NHAN_VIEN loggedInNHANVIEN;       // Lưu trữ thông tin người dùng đã đăng nhập

    // Constructor riêng tư để tránh khởi tạo từ bên ngoài
    private PhanQuyenNV() {
    }

    // Phương thức để lấy instance duy nhất của SessionManager
    public static PhanQuyenNV getInstance() {
        if (instance == null) {
            instance = new PhanQuyenNV();
        }
        return instance;
    }

    // Lấy thông tin người dùng đã đăng nhập (alias cho getLoggedInEmployee)
    public NHAN_VIEN getLoggedInUser() {
        return loggedInNHANVIEN;
    }

    // Lấy thông tin người dùng đã đăng nhập (tên cũ)
    public NHAN_VIEN getLoggedInEmployee() {
        return loggedInNHANVIEN;
    }

    // Thiết lập người dùng đăng nhập
    public void setLoggedInUser(NHAN_VIEN loggedInEmployee) {
        this.loggedInNHANVIEN = loggedInEmployee;
    }

    // Đăng xuất (reset trạng thái người dùng)
    public void logout() {
        this.loggedInNHANVIEN = null;
    }

    // Kiểm tra trạng thái đăng nhập
    public boolean isUserLoggedIn() {
        return loggedInNHANVIEN != null;
    }

}
