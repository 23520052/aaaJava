
package com.phim.dao;

import com.phim.configs.DBConnection;
import com.phim.model.GHE;
import com.phim.model.PHONG_CHIEU;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PhongChieuDAO {
    // Lấy danh sách tất cả phòng chiếu
    public List<PHONG_CHIEU> getAllPhongChieu() {
        List<PHONG_CHIEU> list = new ArrayList<>();
        String sql = "SELECT MA_PHONG, TEN_PHONG, TRANG_THAI FROM PHONG_CHIEU";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                String maPhong = rs.getString("MA_PHONG");
                String tenPhong = rs.getString("TEN_PHONG");
                String trangThai = rs.getString("TRANG_THAI");
                list.add(new PHONG_CHIEU(maPhong, tenPhong, trangThai));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    
    // Lấy danh sách ghế theo mã phòng
    public List<GHE> getGheByPhong(String maPhong) {
        List<GHE> gheList = new ArrayList<>();
        String sql = "SELECT MA_GHE, MA_PHONG, HANG, COT, LOAI_GHE, TRANG_THAI FROM GHE WHERE MA_PHONG = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, maPhong);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    gheList.add(new GHE(
                            rs.getString("MA_GHE"),
                            rs.getString("MA_PHONG"),
                            rs.getString("HANG"),
                            rs.getInt("COT"),
                            rs.getString("LOAI_GHE"),
                            rs.getString("TRANG_THAI")
                    ));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return gheList;
    }
    
    public boolean addPhongChieu(PHONG_CHIEU phong) {
        // Kiểm tra xem mã phòng đã tồn tại chưa
        if (isPhongChieuExists(phong.getMaPhong())) {
            System.out.println("❗ Mã phòng \"" + phong.getMaPhong() + "\" đã tồn tại. Không thể thêm mới.");
            return false;
        }
        
        String sql = "INSERT INTO PHONG_CHIEU (TEN_PHONG, TRANG_THAI) VALUES (?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, phong.getTenPhong());
            ps.setString(2, phong.getTRANGTHAI());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public boolean updatePhongChieu(PHONG_CHIEU phong) {
        String sql = "UPDATE PHONG_CHIEU SET TEN_PHONG = ?, TRANG_THAI = ? WHERE MA_PHONG = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, phong.getTenPhong());
            ps.setString(2, phong.getTRANGTHAI());
            ps.setString(3, phong.getMaPhong());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public boolean isPhongChieuExists(String maPhong) {
        String sql = "SELECT COUNT(*) FROM PHONG_CHIEU WHERE MA_PHONG = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maPhong);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    // Chuyển phòng và ghế về trạng thái 'Bảo trì'
    public boolean deletePhong(String maPhong) {
        String updatePhongSql = "UPDATE PHONG_CHIEU SET TRANG_THAI = 'Bảo trì' WHERE MA_PHONG = ?";
        String updateGheSql = "UPDATE GHE SET TRANG_THAI = 'Bảo trì' WHERE MA_PHONG = ?";

        try (Connection conn = DBConnection.getConnection();
            PreparedStatement psPhong = conn.prepareStatement(updatePhongSql);
            PreparedStatement psGhe = conn.prepareStatement(updateGheSql)) {

            conn.setAutoCommit(false); // bắt đầu transaction

            psPhong.setString(1, maPhong);
            psGhe.setString(1, maPhong);

            int rowsPhong = psPhong.executeUpdate();
            int rowsGhe = psGhe.executeUpdate();

            conn.commit(); // nếu không lỗi thì commit
            return rowsPhong > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

}
