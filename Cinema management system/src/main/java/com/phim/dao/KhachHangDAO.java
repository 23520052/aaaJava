
package com.phim.dao;

import com.phim.configs.DBConnection;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import com.phim.model.KHACH_HANG;

public class KhachHangDAO {
    // Lấy toàn bộ danh sách khách hàng
    public List<KHACH_HANG> getAllKhachHang() {
        List<KHACH_HANG> ds = new ArrayList<>();
        String sql = "SELECT * FROM KHACH_HANG";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                KHACH_HANG kh = new KHACH_HANG(
                    rs.getString("MA_KH"),
                    rs.getString("HO_TEN"),
                    rs.getString("SDT"),
                    rs.getDate("NGAY_SINH").toLocalDate() 
                );
                ds.add(kh);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ds;
    }
    
    // Thêm khách hàng mới
    public boolean insertKhachHang(KHACH_HANG kh) {
        String sql = "INSERT INTO KHACH_HANG (HO_TEN, SDT, NGAY_SINH) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, kh.getHoTen());
            stmt.setString(2, kh.getSoDienThoai());
            stmt.setDate(3, Date.valueOf(kh.getNgayDangKy())); 
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    // Xóa khách hàng theo mã
    public boolean deleteKhachHang(String maKhach) {
        String sql = "DELETE FROM KHACH_HANG WHERE MA_KH = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, maKhach);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Cập nhật khách hàng
    public boolean updateKhachHang(KHACH_HANG kh) {
        String sql = "UPDATE KHACH_HANG SET HO_TEN = ?, SDT = ?, NGAY_SINH = ? WHERE MA_KH = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, kh.getHoTen());
            stmt.setString(2, kh.getSoDienThoai());
            stmt.setDate(3, Date.valueOf(kh.getNgayDangKy()));
            stmt.setString(4, kh.getMaKhach());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    // Tìm khách hàng theo mã
    public KHACH_HANG getKhachHangByMa(String maKhach) {
        String sql = "SELECT * FROM KHACH_HANG WHERE MA_KH = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, maKhach);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new KHACH_HANG(
                    rs.getString("MA_KH"),
                    rs.getString("HO_TEN"),
                    rs.getString("SDT"),
                    rs.getDate("NGAY_SINH").toLocalDate()
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    // Tìm khách hàng theo tên (LIKE, không phân biệt hoa thường nếu cần)
    public List<KHACH_HANG> getKhachHangByTen(String tenKhach) {
        List<KHACH_HANG> ds = new ArrayList<>();
        String sql = "SELECT * FROM KHACH_HANG WHERE LOWER(HO_TEN) LIKE LOWER(?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "%" + tenKhach + "%");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                KHACH_HANG kh = new KHACH_HANG(
                    rs.getString("MA_KH"),
                    rs.getString("HO_TEN"),
                    rs.getString("SDT"),
                    rs.getDate("NGAY_SINH").toLocalDate()
                );
                ds.add(kh);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ds;
    }

    // Tìm khách hàng theo số điện thoại (chính xác)
    public KHACH_HANG getKhachHangBySDT(String soDienThoai) {
        String sql = "SELECT * FROM KHACH_HANG WHERE SDT = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, soDienThoai);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new KHACH_HANG(
                    rs.getString("MA_KH"),
                    rs.getString("HO_TEN"),
                    rs.getString("SDT"),
                    rs.getDate("NGAY_SINH").toLocalDate()
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
