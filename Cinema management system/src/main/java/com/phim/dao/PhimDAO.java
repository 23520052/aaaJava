
package com.phim.dao;

import com.phim.configs.DBConnection;
import com.phim.model.PHIM;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

public class PhimDAO {
    public boolean themPhim(PHIM phim) {
        String sql = "INSERT INTO PHIM (TEN_PHIM, DAO_DIEN, THE_LOAI, THOI_LUONG, MO_TA, TRANG_THAI) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, phim.getTenPhim());
            stmt.setString(2, phim.getDaoDien());
            stmt.setString(3, phim.getTheLoai());
            stmt.setInt(4, phim.getThoiLuong());
            stmt.setString(5, phim.getMoTa());
            stmt.setString(6, phim.getTrangThai());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public boolean capNhatPhim(PHIM phim) {
        String sql = "UPDATE PHIM SET TEN_PHIM = ?, DAO_DIEN = ?, THE_LOAI = ?, THOI_LUONG = ?, MO_TA = ?, TRANG_THAI = ? WHERE MA_PHIM = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, phim.getTenPhim());
            stmt.setString(2, phim.getDaoDien());
            stmt.setString(3, phim.getTheLoai());
            stmt.setInt(4, phim.getThoiLuong());
            stmt.setString(5, phim.getMoTa());
            stmt.setString(6, phim.getTrangThai());
            stmt.setString(7, phim.getMaPhim());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    // Xóa phim nếu chưa tồn tại trong bảng SUAT_CHIEU, nếu đã tồn tại thì chỉ chuyển TRANG_THAI thành 'Ngừng chiếu'
    public boolean xoaPhim(String maPhim) {
        String checkSql = "SELECT COUNT(*) FROM SUAT_CHIEU WHERE MA_PHIM = ?";
        String deleteSql = "DELETE FROM PHIM WHERE MA_PHIM = ?";
        String updateTrangThaiSql = "UPDATE PHIM SET TRANG_THAI = 'Ngừng chiếu' WHERE MA_PHIM = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement checkStmt = conn.prepareStatement(checkSql)) {
            checkStmt.setString(1, maPhim);
            ResultSet rs = checkStmt.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) {
                // Có suất chiếu → chỉ đổi trạng thái
                try (PreparedStatement updateStmt = conn.prepareStatement(updateTrangThaiSql)) {
                    updateStmt.setString(1, maPhim);
                    return updateStmt.executeUpdate() > 0;
                }
            } else {
                // Không có suất chiếu → xóa
                try (PreparedStatement deleteStmt = conn.prepareStatement(deleteSql)) {
                    deleteStmt.setString(1, maPhim);
                    return deleteStmt.executeUpdate() > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public List<PHIM> getAllPhim() {
        List<PHIM> ds = new ArrayList<>();
        String sql = "SELECT * FROM PHIM";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                PHIM phim = new PHIM(
                        rs.getString("MA_PHIM"),
                        rs.getString("TEN_PHIM"),
                        rs.getString("DAO_DIEN"),
                        rs.getString("THE_LOAI"),
                        rs.getInt("THOI_LUONG"),
                        rs.getString("MO_TA"),
                        rs.getString("TRANG_THAI")
                );
                ds.add(phim);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ds;
    }

    public PHIM getPhimById(String maPhim) {
        String sql = "SELECT * FROM PHIM WHERE MA_PHIM = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, maPhim);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new PHIM(
                        rs.getString("MA_PHIM"),
                        rs.getString("TEN_PHIM"),
                        rs.getString("DAO_DIEN"),
                        rs.getString("THE_LOAI"),
                        rs.getInt("THOI_LUONG"),
                        rs.getString("MO_TA"),
                        rs.getString("TRANG_THAI")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    // Tìm phim theo trạng thái ('Sắp chiếu', 'Đang chiếu', 'Ngừng chiếu')
    public List<PHIM> getPhimByTrangThai(String trangThai) {
        List<PHIM> ds = new ArrayList<>();
        String sql = "SELECT * FROM PHIM WHERE TRANG_THAI = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, trangThai);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                PHIM phim = new PHIM(
                    rs.getString("MA_PHIM"),
                    rs.getString("TEN_PHIM"),
                    rs.getString("DAO_DIEN"),
                    rs.getString("THE_LOAI"),
                    rs.getInt("THOI_LUONG"),
                    rs.getString("MO_TA"),
                    rs.getString("TRANG_THAI")
                );
                ds.add(phim);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ds;
    }
    
    // Tìm phim theo thể loại
    public List<PHIM> getPhimByTheLoai(String theLoai) {
        List<PHIM> ds = new ArrayList<>();
        String sql = "SELECT * FROM PHIM WHERE THE_LOAI = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, theLoai);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                PHIM phim = new PHIM(
                    rs.getString("MA_PHIM"),
                    rs.getString("TEN_PHIM"),
                    rs.getString("DAO_DIEN"),
                    rs.getString("THE_LOAI"),
                    rs.getInt("THOI_LUONG"),
                    rs.getString("MO_TA"),
                    rs.getString("TRANG_THAI")
                );
                ds.add(phim);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ds;
    }
    
    // Tìm phim theo tên (LIKE gần đúng)
    public List<PHIM> getPhimByTen(String tenPhim) {
        List<PHIM> ds = new ArrayList<>();
        String sql = "SELECT * FROM PHIM WHERE LOWER(TEN_PHIM) LIKE ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "%" + tenPhim.toLowerCase() + "%");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                PHIM phim = new PHIM(
                    rs.getString("MA_PHIM"),
                    rs.getString("TEN_PHIM"),
                    rs.getString("DAO_DIEN"),
                    rs.getString("THE_LOAI"),
                    rs.getInt("THOI_LUONG"),
                    rs.getString("MO_TA"),
                    rs.getString("TRANG_THAI")
                );
                ds.add(phim);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ds;
    }
    
    public int getThoiLuongPhim(String maPhim) {
        String sql = "SELECT THOI_LUONG FROM PHIM WHERE MA_PHIM = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maPhim);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getInt("THOI_LUONG");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
    
    // Trả về danh sách top phim bán chạy (dạng TEN_PHIM + quantity)
    public List<Map<String, Object>> getTopSellingPhim(int limit) {
        List<Map<String, Object>> result = new ArrayList<>();
        String sql = """
            SELECT P.TEN_PHIM, COUNT(*) AS quantity
            FROM PHIM P
            JOIN SUAT_CHIEU SC ON P.MA_PHIM = SC.MA_PHIM
            JOIN DAT_CHO DC ON SC.MA_SUAT = DC.MA_SUAT
            JOIN CHI_TIET_DAT_CHO CT ON DC.MA_DAT = CT.MA_DAT
            JOIN THANH_TOAN TT ON DC.MA_DAT = TT.MA_DAT
            GROUP BY P.TEN_PHIM
            ORDER BY quantity DESC
            FETCH FIRST ? ROWS ONLY
        """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, limit);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Map<String, Object> map = new HashMap<>();
                    map.put("TEN_PHIM", rs.getString("TEN_PHIM"));
                    map.put("quantity", rs.getInt("quantity"));
                    result.add(map);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }
    
    // Trong PhimController
    public String layMaPhimTheoTen(String tenPhim) {
        List<PHIM> danhSachPhim = getAllPhim();
        for (PHIM phim : danhSachPhim) {
            if (phim.getTenPhim().equalsIgnoreCase(tenPhim)) {
                return phim.getMaPhim();
            }
        }
        return null;
    }
}
