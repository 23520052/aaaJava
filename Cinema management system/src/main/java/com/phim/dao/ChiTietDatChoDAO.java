
package com.phim.dao;

import com.phim.model.CHI_TIET_DAT_CHO;
import com.phim.configs.DBConnection;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ChiTietDatChoDAO {
    // Thêm chi tiết và tự động tính giá
    public boolean addChiTiet(CHI_TIET_DAT_CHO ct, String maPhim) {
        BigDecimal giaCoBan = getGiaCoBan(ct.getMaGhe());
        int thoiLuong = new PhimDAO().getThoiLuongPhim(maPhim);
        BigDecimal phuPhi = giaCoBan.multiply(BigDecimal.valueOf(0.25));
        BigDecimal giaFinal = giaCoBan.add(phuPhi);

        String sql = "INSERT INTO CHI_TIET_DAT_CHO (MA_DAT, MA_GHE, GIA) VALUES (?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, ct.getMaDatCho());
            ps.setString(2, ct.getMaGhe());
            ps.setBigDecimal(3, giaFinal);

            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    // Chèn danh sách ghế cho một mã đặt chỗ
    public boolean addDanhSachChiTiet(String maDat, List<String> danhSachMaGhe, String maPhim) {
        String sql = "INSERT INTO CHI_TIET_DAT_CHO (MA_DAT, MA_GHE, GIA) VALUES (?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            for (String maGhe : danhSachMaGhe) {
                BigDecimal giaCoBan = getGiaCoBan(maGhe);
                int thoiLuong = new PhimDAO().getThoiLuongPhim(maPhim);
                BigDecimal phuPhi = giaCoBan.multiply(BigDecimal.valueOf(0.25)); // có thể điều chỉnh theo từng rạp
                BigDecimal giaFinal = giaCoBan.add(phuPhi);

                ps.setString(1, maDat);
                ps.setString(2, maGhe);
                ps.setBigDecimal(3, giaFinal);
                ps.addBatch();
            }

            int[] result = ps.executeBatch();
            return result.length == danhSachMaGhe.size();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    // Cập nhật giá
    public boolean updateChiTiet(CHI_TIET_DAT_CHO ct, String maPhim) {
        BigDecimal giaCoBan = getGiaCoBan(ct.getMaGhe());
        int thoiLuong = new PhimDAO().getThoiLuongPhim(maPhim);
        
        BigDecimal phuPhi;

        // Áp dụng phụ phí theo thời lượng phim
        if (thoiLuong <= 90) {
            phuPhi = BigDecimal.ZERO;
        } else if (thoiLuong <= 120) {
            phuPhi = new BigDecimal("5000"); // 5.000 VNĐ
        } else {
            phuPhi = new BigDecimal("15000"); // 15.000 VNĐ
        }
        
        BigDecimal giaFinal = giaCoBan.add(phuPhi);     
        
        String sql = "UPDATE CHI_TIET_DAT_CHO SET GIA = ? WHERE MA_DAT = ? AND MA_GHE = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setBigDecimal(1, giaFinal);
            ps.setString(2, ct.getMaDatCho());
            ps.setString(3, ct.getMaGhe());

            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // xóa theo mã đặt và mã ghế(có thể null)
    public boolean deleteChiTiet(String maDat, String maGhe) {
        String sql = (maGhe == null)
            ? "DELETE FROM CHI_TIET_DAT_CHO WHERE MA_DAT = ?"
            : "DELETE FROM CHI_TIET_DAT_CHO WHERE MA_DAT = ? AND MA_GHE = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maDat);
            if (maGhe != null) ps.setString(2, maGhe);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // Lấy giá cơ bản dựa trên MA_GHE → GHE → LOAI_GHE → GIA_GHE
    private BigDecimal getGiaCoBan(String maGhe) {
        String sql = """
            SELECT gg.GIA
            FROM GHE g
            JOIN GIA_GHE gg ON g.LOAI_GHE = gg.LOAI_GHE
            WHERE g.MA_GHE = ?
        """;
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maGhe);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getBigDecimal("GIA");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return BigDecimal.ZERO;
    }

    // Lấy toàn bộ chi tiết đặt chỗ
    public List<CHI_TIET_DAT_CHO> getAllChiTiet() {
        return queryList("SELECT * FROM CHI_TIET_DAT_CHO");
    }

    // Lấy theo mã đặt chỗ
    public List<CHI_TIET_DAT_CHO> getByMaDat(String maDat) {
        return queryList("SELECT * FROM CHI_TIET_DAT_CHO WHERE MA_DAT = ?", maDat);
    }

    // Lấy theo mã ghế
    public List<CHI_TIET_DAT_CHO> getByMaGhe(String maGhe) {
        return queryList("SELECT * FROM CHI_TIET_DAT_CHO WHERE MA_GHE = ?", maGhe);
    }

    // Lấy theo ngày đặt (giả định có liên kết đến bảng DAT_CHO)
    public List<CHI_TIET_DAT_CHO> getByNgayDat(LocalDateTime ngay) {
        String sql = """
            SELECT ct.*
            FROM CHI_TIET_DAT_CHO ct
            JOIN DAT_CHO dc ON ct.MA_DAT = dc.MA_DAT
            WHERE TRUNC(dc.NGAY_DAT) = TRUNC(?)
        """;
        return queryList(sql, Timestamp.valueOf(ngay));
    }

    // Hàm ánh xạ ResultSet thành object
    private CHI_TIET_DAT_CHO mapRS(ResultSet rs) throws SQLException {
        return new CHI_TIET_DAT_CHO(
            rs.getString("MA_DAT"),
            rs.getString("MA_GHE"),
            rs.getBigDecimal("GIA")
        );
    }

    // Helper query chung
    private List<CHI_TIET_DAT_CHO> queryList(String sql, Object... params) {
        List<CHI_TIET_DAT_CHO> list = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            for (int i = 0; i < params.length; i++) {
                Object param = params[i];
                if (param instanceof String)
                    ps.setString(i + 1, (String) param);
                else if (param instanceof Timestamp)
                    ps.setTimestamp(i + 1, (Timestamp) param);
            }

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(mapRS(rs));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    
    public boolean themChiTiet(CHI_TIET_DAT_CHO ct) {
        String sql = "INSERT INTO CHI_TIET_DAT_CHO (ma_dat_cho, ma_ghe, don_gia) VALUES (?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, ct.getMaDatCho());
            ps.setString(2, ct.getMaGhe());
            ps.setBigDecimal(3, ct.getgia());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public BigDecimal tinhTongTien(String maDat) {
        String sql = "SELECT SUM(gia) AS tong FROM CHI_TIET_DAT_CHO WHERE ma_dat = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maDat);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getBigDecimal("tong") != null ? rs.getBigDecimal("tong") : BigDecimal.ZERO;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return BigDecimal.ZERO;
    }
}
