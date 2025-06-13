
package com.phim.dao;
import com.phim.configs.DBConnection;
import com.phim.model.ChartDataModel;
import com.phim.model.THANH_TOAN;
import java.math.BigDecimal;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ThanhToanDAO {
    private static final Logger logger = Logger.getLogger(ThanhToanDAO.class.getName());
    
    // Thêm mới thanh toán
    public boolean insert(THANH_TOAN tt) {
        String sql = "INSERT INTO THANH_TOAN (MA_DAT, MANV, TONG_TIEN, NGAY_THANH_TOAN, PHUONG_THUC) " +
                     "VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)) {
     
            ps.setString(1, tt.getMaDatCho());
            ps.setInt(2, tt.getMANV());
            ps.setDouble(3, tt.getSoTien());
            ps.setTimestamp(4, Timestamp.valueOf(tt.getThoiGianThanhToan()));
            ps.setString(5, tt.getPhuongThuc());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Lỗi khi thêm thanh toán", e);
        }
        return false;
    }
    
    // Xóa thanh toán theo mã thanh toán
    public boolean delete(String maThanhToan) {
        String sql = "DELETE FROM THANH_TOAN WHERE MA_THANH_TOAN = ?";
        try (Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maThanhToan);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Lỗi khi xóa thanh toán", e);

        }
        return false;
    }
    
    // Cập nhật nhân viên và phương thức thanh toán
    public boolean update(String maThanhToan, int maNV, String phuongThuc) {
        String sql = "UPDATE THANH_TOAN SET MANV = ?, PHUONG_THUC = ? WHERE MA_THANH_TOAN = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, maNV);
            ps.setString(2, phuongThuc);
            ps.setString(3, maThanhToan);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Lỗi khi cập nhật thanh toán", e);
        }
        return false;
    }
    
    public BigDecimal tinhTongTienDatCho(String maDatCho) {
        String sql = "SELECT SUM(GIA) FROM CHI_TIET_DAT_CHO WHERE MA_DAT = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maDatCho);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    BigDecimal tong = rs.getBigDecimal(1);
                    return tong != null ? tong : BigDecimal.ZERO;
                }
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Lỗi khi tính tổng tiền đặt chỗ", e);
        }
        return BigDecimal.ZERO;
    }
    
    // Lấy tất cả
    public List<THANH_TOAN> getAll() {
        List<THANH_TOAN> list = new ArrayList<>();
        String sql = "SELECT * FROM THANH_TOAN";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(mapResultSetToThanhToan(rs));
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Lỗi khi lấy danh sách thanh toán", e);
        }
        return list;
    }
    
    // Lấy theo mã đặt chỗ
    public THANH_TOAN getByMaDat(String maDat) {
        String sql = "SELECT * FROM THANH_TOAN WHERE MA_DAT = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maDat);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToThanhToan(rs);
                }
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Lỗi khi lấy thanh toán theo mã đặt chỗ", e);
        }
        return null;
    }
    
    // Lấy theo phương thức thanh toán
    public List<THANH_TOAN> getByPhuongThuc(String phuongThuc) {
        List<THANH_TOAN> list = new ArrayList<>();
        String sql = "SELECT * FROM THANH_TOAN WHERE PHUONG_THUC = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, phuongThuc);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapResultSetToThanhToan(rs));
                }
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Lỗi khi lấy theo phương thức thanh toán", e);
        }
        return list;
    }
    
    // Lấy theo ngày, tháng, năm cụ thể
    public List<THANH_TOAN> getByNgayThangNam(int ngay, int thang, int nam) {
        List<THANH_TOAN> list = new ArrayList<>();
        String sql = "SELECT * FROM THANH_TOAN WHERE EXTRACT(DAY FROM NGAY_THANH_TOAN) = ? " +
                     "AND EXTRACT(MONTH FROM NGAY_THANH_TOAN) = ? AND EXTRACT(YEAR FROM NGAY_THANH_TOAN) = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, ngay);
            ps.setInt(2, thang);
            ps.setInt(3, nam);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapResultSetToThanhToan(rs));
                }
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Lỗi khi lấy theo ngày-tháng-năm", e);
        }
        return list;
    }
    
    // Lấy theo chỉ năm hoặc tháng hoặc ngày
    public List<THANH_TOAN> getByThoiGian(Integer ngay, Integer thang, Integer nam) {
        List<THANH_TOAN> list = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT * FROM THANH_TOAN WHERE 1=1");
        List<Object> params = new ArrayList<>();

        if (ngay != null) {
            sql.append(" AND EXTRACT(DAY FROM NGAY_THANH_TOAN) = ?");
            params.add(ngay);
        }
        if (thang != null) {
            sql.append(" AND EXTRACT(MONTH FROM NGAY_THANH_TOAN) = ?");
            params.add(thang);
        }
        if (nam != null) {
            sql.append(" AND EXTRACT(YEAR FROM NGAY_THANH_TOAN) = ?");
            params.add(nam);
        }

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql.toString())) {

            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i + 1, params.get(i));
            }

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapResultSetToThanhToan(rs));
                }
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Lỗi khi lấy theo thời gian", e);
        }
        return list;
    }
    
    // Tổng tiền theo năm
    public double getTongTienTheoNam(int nam) {
        String sql = "SELECT SUM(TONG_TIEN) FROM THANH_TOAN WHERE EXTRACT(YEAR FROM NGAY_THANH_TOAN) = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, nam);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getDouble(1);
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Lỗi khi tính tổng tiền theo năm", e);
        }
        return 0;
    }
    
     // Tổng tiền theo tháng và năm
    public double getTongTienTheoThangNam(int thang, int nam) {
        String sql = "SELECT SUM(TONG_TIEN) FROM THANH_TOAN WHERE EXTRACT(MONTH FROM NGAY_THANH_TOAN) = ? AND EXTRACT(YEAR FROM NGAY_THANH_TOAN) = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, thang);
            ps.setInt(2, nam);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getDouble(1);
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Lỗi khi tính tổng tiền theo tháng và năm", e);
        }
        return 0;
    }

    // Tổng tiền theo ngày cụ thể
    public double getTongTienTheoNgay(int ngay, int thang, int nam) {
        String sql = "SELECT SUM(TONG_TIEN) FROM THANH_TOAN WHERE EXTRACT(DAY FROM NGAY_THANH_TOAN) = ? AND EXTRACT(MONTH FROM NGAY_THANH_TOAN) = ? AND EXTRACT(YEAR FROM NGAY_THANH_TOAN) = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, ngay);
            ps.setInt(2, thang);
            ps.setInt(3, nam);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getDouble(1);
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Lỗi khi tính tổng tiền theo ngày cụ thể", e);
        }
        return 0;
    }
    
    // Hàm hỗ trợ ánh xạ
    private THANH_TOAN mapResultSetToThanhToan(ResultSet rs) throws SQLException {
        return new THANH_TOAN(
            rs.getString("MA_THANH_TOAN"),
            rs.getString("MA_DAT"),
            rs.getInt("MANV"),
            rs.getDouble("TONG_TIEN"),
            rs.getTimestamp("NGAY_THANH_TOAN").toLocalDateTime(),
            rs.getString("PHUONG_THUC")
        );
    }
    
    // Đếm tổng số thanh toán
    public int countAll() {
        String sql = "SELECT COUNT(*) FROM THANH_TOAN";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Lỗi khi đếm tổng số thanh toán", e);
        }
        return 0;

    }
    
    //Tổng toàn bộ doanh thu 
    public double getTongDoanhThu() {
        double tong = 0;
        String sql = "SELECT SUM(TONG_TIEN) FROM THANH_TOAN";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                tong = rs.getDouble(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tong;
    }
    
    // Lấy các năm có doanh thu 
    public List<Integer> getAvailableYears() {
        List<Integer> years = new ArrayList<>();
        String sql = "SELECT DISTINCT EXTRACT(YEAR FROM NGAY_THANH_TOAN) AS NAM FROM THANH_TOAN ORDER BY NAM DESC";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                years.add(rs.getInt("NAM"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return years;
    }
    
    // Doanh thu theo tuần trong năm
    public List<ChartDataModel> getDoanhThuTheoTuanTrongNam(int year) {
        List<ChartDataModel> list = new ArrayList<>();
        String sql = "SELECT TO_CHAR(NGAY_THANH_TOAN, 'WW') AS TUAN, SUM(TONG_TIEN) AS DOANHTHU " +
                     "FROM THANH_TOAN WHERE TO_CHAR(NGAY_THANH_TOAN, 'YYYY') = ? " +
                     "GROUP BY TO_CHAR(NGAY_THANH_TOAN, 'WW') ORDER BY TUAN";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, String.valueOf(year));
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    String tuan = "Tuần " + rs.getString("TUAN");
                    list.add(new ChartDataModel(tuan, rs.getBigDecimal("DOANHTHU")));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // Doanh thu theo năm
    public List<ChartDataModel> getDoanhThuTheoNam() {
        List<ChartDataModel> list = new ArrayList<>();
        String sql = "SELECT TO_CHAR(NGAY_THANH_TOAN, 'YYYY') AS NAM, SUM(TONG_TIEN) AS DOANHTHU " +
                     "FROM THANH_TOAN GROUP BY TO_CHAR(NGAY_THANH_TOAN, 'YYYY') ORDER BY NAM";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(new ChartDataModel(rs.getString("NAM"), rs.getBigDecimal("DOANHTHU")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // Doanh thu theo tháng trong năm
    public List<ChartDataModel> getDoanhThuTheoThangTrongNam(int year) {
        List<ChartDataModel> list = new ArrayList<>();
        String sql = "SELECT TO_CHAR(NGAY_THANH_TOAN, 'MM') AS THANG, SUM(TONG_TIEN) AS DOANHTHU " +
                     "FROM THANH_TOAN WHERE TO_CHAR(NGAY_THANH_TOAN, 'YYYY') = ? " +
                     "GROUP BY TO_CHAR(NGAY_THANH_TOAN, 'MM') ORDER BY THANG";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, String.valueOf(year));
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    String thang = "Tháng " + rs.getString("THANG");
                    list.add(new ChartDataModel(thang, rs.getBigDecimal("DOANHTHU")));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    
    public List<Map<String, Object>> getTopEmployeesByRevenue(int topN) {
        List<Map<String, Object>> result = new ArrayList<>();
        String sql = "SELECT NV.MaNV, NV.FullName, SUM(TT.TONG_TIEN) AS DOANHTHU " +
                     "FROM THANH_TOAN TT " +
                     "JOIN NHAN_VIEN NV ON TT.MaNV = NV.MaNV " +
                     "GROUP BY NV.MaNV, NV.FullName " +
                     "ORDER BY DOANHTHU DESC FETCH FIRST ? ROWS ONLY";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, topN);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Map<String, Object> map = new HashMap<>();
                    map.put("MaNV", rs.getInt("MaNV"));
                    map.put("FullName", rs.getString("FullName"));
                    map.put("DOANHTHU", rs.getBigDecimal("DOANHTHU"));
                    result.add(map);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }
    
    public THANH_TOAN getById(String maThanhToan) {
        String sql = "SELECT * FROM THANH_TOAN WHERE MA_THANH_TOAN = ?";
        try (
            Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)
        ) {
            ps.setString(1, maThanhToan);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String maDat = rs.getString("MA_DAT");
                    int maNV = rs.getInt("MANV");
                    double tongTien = rs.getDouble("TONG_TIEN");
                    Timestamp ts = rs.getTimestamp("NGAY_THANH_TOAN");
                    LocalDateTime ngayThanhToan = (ts != null) ? ts.toLocalDateTime() : null;
                    String phuongThuc = rs.getString("PHUONG_THUC");

                    return new THANH_TOAN(maThanhToan, maDat, maNV, tongTien, ngayThanhToan, phuongThuc);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public void update(THANH_TOAN tt) {
        String sql = "UPDATE THANH_TOAN SET TONG_TIEN = ?, PHUONG_THUC = ? WHERE MA_THANH_TOAN = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDouble(1, tt.getSoTien());
            ps.setString(2, tt.getPhuongThuc());
            ps.setString(3, tt.getMaThanhToan());
            ps.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException("Lỗi khi cập nhật thanh toán: " + e.getMessage(), e);
        }
    }
    
    public List<THANH_TOAN> search(String keyword) {
        List<THANH_TOAN> list = new ArrayList<>();
        String sql = "SELECT * FROM THANH_TOAN WHERE LOWER(MA_THANH_TOAN) LIKE ? OR LOWER(PHUONG_THUC) LIKE ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            String pattern = "%" + keyword.toLowerCase() + "%";
            ps.setString(1, pattern);
            ps.setString(2, pattern);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    THANH_TOAN tt = new THANH_TOAN(
                        rs.getString("MA_THANH_TOAN"),
                        rs.getString("MA_DAT"),
                        rs.getInt("MANV"),
                        rs.getDouble("TONG_TIEN"),
                        rs.getTimestamp("NGAY_THANH_TOAN").toLocalDateTime(),
                        rs.getString("PHUONG_THUC")
                    );
                    list.add(tt);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Lỗi khi tìm kiếm thanh toán: " + e.getMessage(), e);
        }
        return list;
    }
}