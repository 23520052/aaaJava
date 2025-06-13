package com.phim.dao;

import com.phim.model.GHE;
import com.phim.configs.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GheDAO {
    // Thêm ghế
    public boolean addGhe(GHE ghe) {
        if (existsGheByHangCot(ghe.getMA_PHONG(), ghe.getHANG(), ghe.getCOT())) {
            System.out.println("Ghế đã tồn tại ở vị trí này!");
            return false;
        }
        
        String sql = "INSERT INTO GHE (MA_PHONG, HANG, COT, TRANG_THAI, LOAI_GHE) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, ghe.getMA_PHONG());
            ps.setString(2, ghe.getHANG());
            ps.setInt(3, ghe.getCOT());
            ps.setString(4, ghe.getTRANG_THAI());
            ps.setString(5, ghe.getLOAI_GHE());
            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // Xóa ghế theo mã ghế
    public boolean deleteGhe(String maGhe) {
        String sql = "DELETE FROM GHE WHERE MA_GHE = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, maGhe);
            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    // Cập nhật ghế
    public boolean updateGhe(GHE ghe) {
        String sql = "UPDATE GHE SET MA_PHONG = ?, HANG = ?, COT = ?, TRANG_THAI = ?, LOAI_GHE = ? WHERE MA_GHE = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, ghe.getMA_PHONG());
            ps.setString(2, ghe.getHANG());
            ps.setInt(3, ghe.getCOT());
            ps.setString(4, ghe.getTRANG_THAI());
            ps.setString(5, ghe.getLOAI_GHE());
            ps.setString(6, ghe.getMA_GHE());
            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    // Lấy tất cả ghế
    public List<GHE> getAllGhe() {
        List<GHE> list = new ArrayList<>();
        String sql = "SELECT * FROM GHE";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                GHE ghe = new GHE(
                        rs.getString("MA_GHE"),
                        rs.getString("MA_PHONG"),
                        rs.getString("HANG"),
                        rs.getInt("COT"),
                        rs.getString("TRANG_THAI"),
                        rs.getString("LOAI_GHE")
                );
                list.add(ghe);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    
    // Lấy danh sách ghế theo loại ghế
    public List<GHE> getGheByLoai(String loaiGhe) {
        List<GHE> list = new ArrayList<>();
        String sql = "SELECT * FROM GHE WHERE LOAI_GHE = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, loaiGhe);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                GHE ghe = new GHE(
                        rs.getString("MA_GHE"),
                        rs.getString("MA_PHONG"),
                        rs.getString("HANG"),
                        rs.getInt("COT"),
                        rs.getString("TRANG_THAI"),
                        rs.getString("LOAI_GHE")
                );
                list.add(ghe);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    
    // Lấy danh sách ghế theo mã phòng
    public List<GHE> getGheByPhong(String maPhong) {
        List<GHE> list = new ArrayList<>();
        String sql = "SELECT * FROM GHE WHERE MA_PHONG = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, maPhong);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                GHE ghe = new GHE(
                        rs.getString("MA_GHE"),
                        rs.getString("MA_PHONG"),
                        rs.getString("HANG"),
                        rs.getInt("COT"),
                        rs.getString("TRANG_THAI"),
                        rs.getString("LOAI_GHE")
                );
                list.add(ghe);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    
    // Lấy ghế theo Hàng và Cột trong phòng
    public GHE getGheByHangCot(String maPhong, String hang, int cot) {
        String sql = "SELECT * FROM GHE WHERE MA_PHONG = ? AND HANG = ? AND COT = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, maPhong);
            ps.setString(2, hang);
            ps.setInt(3, cot);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new GHE(
                        rs.getString("MA_GHE"),
                        rs.getString("MA_PHONG"),
                        rs.getString("HANG"),
                        rs.getInt("COT"),
                        rs.getString("TRANG_THAI"),
                        rs.getString("LOAI_GHE")
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    // Kiểm tra trùng ghế theo hàng - cột - phòng
    public boolean existsGheByHangCot(String maPhong, String hang, int cot) {
        String sql = "SELECT COUNT(*) FROM GHE WHERE MA_PHONG = ? AND HANG = ? AND COT = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, maPhong);
            ps.setString(2, hang);
            ps.setInt(3, cot);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0; // Trả về true nếu đã tồn tại ghế tại vị trí này
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    
    public List<GHE> getGheTrongBySuatChieu(String maSuat) {
        List<GHE> gheList = new ArrayList<>();

        String sql = """
            SELECT g.*
            FROM GHE g
            JOIN SUAT_CHIEU s ON g.MA_PHONG = s.MA_PHONG
            WHERE s.MA_SUAT = ?
            AND g.MA_GHE NOT IN (
                SELECT ct.MA_GHE
                FROM CHI_TIET_DAT_CHO ct
                JOIN DAT_CHO dc ON ct.MA_DAT = dc.MA_DAT
                WHERE dc.MA_SUAT = ?
            )
            ORDER BY g.HANG, g.COT
            """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, maSuat);
            ps.setString(2, maSuat);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                GHE ghe = new GHE(
                    rs.getString("MA_GHE"),
                    rs.getString("MA_PHONG"),
                    rs.getString("HANG"),
                    rs.getInt("COT"),
                    rs.getString("TRANG_THAI"),
                    rs.getString("LOAI_GHE")
                );
                gheList.add(ghe);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return gheList;
    }
    
    public String layMaGheTheoTen(String tenGhe) {
        String sql = "SELECT MA_GHE FROM GHE WHERE ten_ghe = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, tenGhe);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString("ma_ghe");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
