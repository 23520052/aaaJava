package com.phim.dao;

import com.phim.configs.DBConnection;
import com.phim.model.GIA_GHE;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GiaGheDAO {
    // Lấy giá ghế theo loại
    public GIA_GHE getGiaByLoaiGhe(String loaiGhe) {
        GIA_GHE giaGhe = null;
        String sql = "SELECT * FROM GIA_GHE WHERE LOAI_GHE = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, loaiGhe);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                giaGhe = new GIA_GHE(
                    rs.getString("LOAI_GHE"),
                    rs.getBigDecimal("GIA")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return giaGhe;
    }
    
    //cập nhật giá ghế theo loại ghế
    public boolean updateGiaByLoaiGhe(String loaiGhe, BigDecimal giaMoi) {
        String sql = "UPDATE GIA_GHE SET GIA = ? WHERE LOAI_GHE = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setBigDecimal(1, giaMoi);
            stmt.setString(2, loaiGhe);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    // Hiển thị toàn bộ các loại ghế
    public List<GIA_GHE> getAllGiaGhe() {
        List<GIA_GHE> list = new ArrayList<>();
        String sql = "SELECT * FROM GIA_GHE";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                GIA_GHE giaGhe = new GIA_GHE(
                    rs.getString("LOAI_GHE"),
                    rs.getBigDecimal("GIA")
                );
                list.add(giaGhe);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    
    // Thêm loại ghế mới
    public boolean addGiaGhe(String loaiGhe, BigDecimal gia) {
        String sql = "INSERT INTO GIA_GHE (LOAI_GHE, GIA) VALUES (?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, loaiGhe);
            stmt.setBigDecimal(2, gia);
            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            if (e.getMessage().contains("unique constraint")) {
                System.out.println("Loại ghế đã tồn tại.");
            } else {
                e.printStackTrace();
            }
        }
        return false;
    }
    
    public Map<String, BigDecimal> layTatCaGia() {
        Map<String, BigDecimal> giaMap = new HashMap<>();
        String sql = "SELECT LOAI_GHE, GIA FROM GIA_GHE";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String loaiGhe = rs.getString("LOAI_GHE");
                BigDecimal gia = rs.getBigDecimal("GIA");
                giaMap.put(loaiGhe, gia);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return giaMap;
    }
}
