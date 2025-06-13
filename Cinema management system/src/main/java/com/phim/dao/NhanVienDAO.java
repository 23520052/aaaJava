package com.phim.dao;

import com.phim.model.NHAN_VIEN;
import com.phim.configs.DBConnection;
import com.phim.model.ChartDataModel;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class NhanVienDAO {
    public List<NHAN_VIEN> getAllNhanVien() {
        List<NHAN_VIEN> list = new ArrayList<>();
        String query = "SELECT * FROM NHAN_VIEN";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                NHAN_VIEN nv = new NHAN_VIEN(
                        rs.getInt("MaNV"),
                        rs.getString("FULLNAME"),
                        rs.getString("ROLE"),
                        rs.getString("PHONE"),
                        rs.getString("EMAIL"),
                        rs.getDate("HIREDATE").toLocalDate(),
                        rs.getString("PASSWORD")
                );
                list.add(nv);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public NHAN_VIEN getNhanVienById(int manv) {
        String query = "SELECT * FROM NHAN_VIEN WHERE MANV = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, manv);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new NHAN_VIEN(
                            rs.getInt("MaNV"),
                            rs.getString("FULLNAME"),
                            rs.getString("ROLE"),
                            rs.getString("PHONE"),
                            rs.getString("EMAIL"),
                            rs.getDate("HIREDATE").toLocalDate(),
                            rs.getString("PASSWORD")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public NHAN_VIEN getNhanVienByName(String name) {
        String sql = "SELECT * FROM NHAN_VIEN WHERE FULLNAME = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, name.trim());
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new NHAN_VIEN(
                            rs.getInt("MaNV"),
                            rs.getString("FULLNAME"),
                            rs.getString("ROLE"),
                            rs.getString("PHONE"),
                            rs.getString("EMAIL"),
                            rs.getDate("HIREDATE").toLocalDate(),
                            rs.getString("PASSWORD")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean addNhanVien(NHAN_VIEN nv) {
        String sql = "INSERT INTO NHAN_VIEN (FULLNAME, ROLE, PHONE, EMAIL, HIREDATE) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nv.getFullName());
            stmt.setString(2, nv.getRole());
            stmt.setString(3, nv.getPhone());
            stmt.setString(4, nv.getEmail());
            stmt.setDate(5, Date.valueOf(nv.getHireDate()));
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateNhanVien(NHAN_VIEN nv) {
        String sql = "UPDATE NHAN_VIEN SET FULLNAME = ?, ROLE = ?, PHONE = ?, EMAIL = ?, HIREDATE = ? WHERE MANV = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nv.getFullName());
            stmt.setString(2, nv.getRole());
            stmt.setString(3, nv.getPhone());
            stmt.setString(4, nv.getEmail());
            stmt.setDate(5, Date.valueOf(nv.getHireDate()));
            stmt.setInt(6, nv.getMaNv());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteNhanVien(int manv) {
        String sql = "DELETE FROM NHAN_VIEN WHERE MANV = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, manv);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public NHAN_VIEN login(String email, String password) {
        String sql = "SELECT * FROM NHAN_VIEN WHERE EMAIL = ? AND PASSWORD = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);
            stmt.setString(2, password);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new NHAN_VIEN(
                            rs.getInt("MaNV"),
                            rs.getString("FULLNAME"),
                            rs.getString("ROLE"),
                            rs.getString("PHONE"),
                            rs.getString("EMAIL"),
                            rs.getDate("HIREDATE").toLocalDate(),
                            rs.getString("PASSWORD")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean updatePassword(String email, String oldPassword, String newPassword) {
        String sql = "UPDATE NHAN_VIEN SET PASSWORD = ? WHERE EMAIL = ? AND PASSWORD = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, newPassword);
            stmt.setString(2, email);
            stmt.setString(3, oldPassword);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean forgetPassword(String email, String newPassword) {
        String sql = "UPDATE NHAN_VIEN SET PASSWORD = ? WHERE EMAIL = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, newPassword);
            stmt.setString(2, email);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public int countNhanVien() {
        String sql = "SELECT COUNT(*) FROM NHAN_VIEN";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public List<ChartDataModel> getNhanVienCountByRole() {
        List<ChartDataModel> list = new ArrayList<>();
        String sql = "SELECT ROLE, COUNT(*) AS cnt FROM NHAN_VIEN GROUP BY ROLE";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                list.add(new ChartDataModel(
                        rs.getString("ROLE"),
                        BigDecimal.valueOf(rs.getLong("cnt"))
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    
    public List<NHAN_VIEN> getNhanVienByRole(String role) {
        List<NHAN_VIEN> list = new ArrayList<>();
        String sql = "SELECT * FROM NHAN_VIEN WHERE ROLE = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, role.trim());
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    NHAN_VIEN nv = new NHAN_VIEN(
                            rs.getInt("MaNV"),
                            rs.getString("FULLNAME"),
                            rs.getString("ROLE"),
                            rs.getString("PHONE"),
                            rs.getString("EMAIL"),
                            rs.getDate("HIREDATE").toLocalDate(),
                            rs.getString("PASSWORD")
                    );
                    list.add(nv);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    
    public List<ChartDataModel> getNVCountByRole() {
        List<ChartDataModel> list = new ArrayList<>();
        String sql = "SELECT ROLE, COUNT(*) AS cnt FROM NHAN_VIEN GROUP BY ROLE";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                list.add(new ChartDataModel(
                        rs.getString("ROLE"),
                        BigDecimal.valueOf(rs.getLong("cnt"))
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    
    public List<NHAN_VIEN> searchByName(String keyword) {
        List<NHAN_VIEN> list = new ArrayList<>();
        String sql = "SELECT * FROM NHAN_VIEN WHERE LOWER(FullName) LIKE ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "%" + keyword.toLowerCase() + "%");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                NHAN_VIEN nv = new NHAN_VIEN(
                    rs.getInt("MaNV"),
                    rs.getString("FullName"),
                    rs.getString("ROLE"),
                    rs.getString("SDT"),
                    rs.getString("EMAIL"),
                    rs.getDate("HireDate").toLocalDate(),
                    rs.getString("Password")
                );
                list.add(nv);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
