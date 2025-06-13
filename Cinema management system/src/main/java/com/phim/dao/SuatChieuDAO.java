package com.phim.dao;

import com.phim.configs.DBConnection;
import com.phim.model.PHIM;
import com.phim.model.PHONG_CHIEU;
import com.phim.model.SUAT_CHIEU;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class SuatChieuDAO {
    // Thêm suất chiếu
    public boolean addSuatChieu(SUAT_CHIEU suat) {
        
        // Bước 1: Lấy thời lượng phim
        PhimDAO phimDAO = new PhimDAO(); // bạn cần có DAO này
        int thoiLuongPhim = phimDAO.getThoiLuongPhim(suat.getMaPhim());

        // Bước 2: Kiểm tra trùng thời gian chiếu trong cùng phòng
        if (isThoiGianTrung(suat.getMaPhong(), suat.getThoiGianChieu(), thoiLuongPhim, "")) {
            System.out.println("❌ Trùng thời gian chiếu với suất khác trong cùng phòng!");
            return false;
        }
                
        String sql = "INSERT INTO SUAT_CHIEU (MA_PHIM, MA_PHONG, THOI_GIAN_CHIEU) VALUES (?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, suat.getMaPhim());
            ps.setString(2, suat.getMaPhong());
            ps.setTimestamp(3, Timestamp.valueOf(suat.getThoiGianChieu()));

            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    // Xóa suất chiếu
    public boolean deleteSuatChieu(String maSuat) {
        String sql = "DELETE FROM SUAT_CHIEU WHERE MA_SUAT = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, maSuat);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    // Cập nhật suất chiếu
    public boolean updateSuatChieu(SUAT_CHIEU suat) {
        int thoiLuongPhim = getThoiLuongPhim(suat.getMaPhim());
        if (isThoiGianTrung(suat.getMaPhong(), suat.getThoiGianChieu(), thoiLuongPhim, suat.getMaSuat())) {
            System.out.println("❌ Thời gian chiếu bị trùng, không thể cập nhật!");
            return false;
        }
        String sql = "UPDATE SUAT_CHIEU SET MA_PHIM = ?, MA_PHONG = ?, THOI_GIAN_CHIEU = ? WHERE MA_SUAT = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, suat.getMaPhim());
            ps.setString(2, suat.getMaPhong());
            ps.setTimestamp(3, Timestamp.valueOf(suat.getThoiGianChieu()));
            ps.setString(4, suat.getMaSuat());

            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    // Lấy suất chiếu theo mã phim
    public List<SUAT_CHIEU> getSuatChieuByPhim(String maPhim) {
        List<SUAT_CHIEU> list = new ArrayList<>();
        String sql = "SELECT * FROM SUAT_CHIEU WHERE MA_PHIM = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, maPhim);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapResultSetToSuatChieu(rs));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    
    public SUAT_CHIEU getSuatChieuByMaSuat(String maSuat) {
    String sql = "SELECT * FROM SUAT_CHIEU WHERE MA_SUAT = ?";
    try (Connection conn = DBConnection.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setString(1, maSuat);
        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return mapResultSetToSuatChieu(rs);
            }
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return null;
}
    
    // Lấy suất chiếu theo mã phòng
    public List<SUAT_CHIEU> getSuatChieuByPhong(String maPhong) {
        List<SUAT_CHIEU> list = new ArrayList<>();
        String sql = "SELECT * FROM SUAT_CHIEU WHERE MA_PHONG = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, maPhong);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapResultSetToSuatChieu(rs));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    
    // Lấy tất cả suất chiếu
    public List<SUAT_CHIEU> getAllSuatChieu() {
        List<SUAT_CHIEU> list = new ArrayList<>();
        String sql = "SELECT * FROM SUAT_CHIEU";
        try (Connection conn = DBConnection.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                list.add(mapResultSetToSuatChieu(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // Hàm tiện ích ánh xạ dữ liệu ResultSet thành đối tượng SUAT_CHIEU
    private SUAT_CHIEU mapResultSetToSuatChieu(ResultSet rs) throws SQLException {
        return new SUAT_CHIEU(
                rs.getString("MA_SUAT"),
                rs.getString("MA_PHIM"),
                rs.getString("MA_PHONG"),
                rs.getTimestamp("THOI_GIAN_CHIEU").toLocalDateTime()
        );
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
    
    // Kiểm tra xem suất chiếu có bị trùng giờ chiếu trong cùng một phòng không
    public boolean isThoiGianTrung(String maPhong, LocalDateTime thoiGianChieu, int thoiLuongPhut, String maSuatBoQua) {
        String sql = """
            SELECT 1 FROM SUAT_CHIEU sc
            JOIN PHIM p ON sc.MA_PHIM = p.MA_PHIM
            WHERE sc.MA_PHONG = ?
            AND sc.MA_SUAT <> ?
            AND (
                (? BETWEEN sc.THOI_GIAN_CHIEU AND (sc.THOI_GIAN_CHIEU + NUMTODSINTERVAL(p.THOI_LUONG, 'MINUTE')))
                OR
                ((? + NUMTODSINTERVAL(?, 'MINUTE')) BETWEEN sc.THOI_GIAN_CHIEU AND (sc.THOI_GIAN_CHIEU + NUMTODSINTERVAL(p.THOI_LUONG, 'MINUTE')))
                OR
                (sc.THOI_GIAN_CHIEU BETWEEN ? AND (? + NUMTODSINTERVAL(?, 'MINUTE')))
            )
            """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            Timestamp start = Timestamp.valueOf(thoiGianChieu);

            ps.setString(1, maPhong);
            ps.setString(2, maSuatBoQua); // bỏ qua nếu đang cập nhật chính nó
            ps.setTimestamp(3, start);
            ps.setTimestamp(4, start);
            ps.setInt(5, thoiLuongPhut);
            ps.setTimestamp(6, start);
            ps.setTimestamp(7, start);
            ps.setInt(8, thoiLuongPhut);

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next(); // có bản ghi nghĩa là bị trùng
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true; // nếu lỗi thì coi như trùng để an toàn
    }
    
    public String getMaSuatChieu(String maPhim, LocalDate ngay, LocalTime gio) {
        String sql = "SELECT MA_SUAT FROM SUAT_CHIEU WHERE MA_PHIM = ? AND NGAY_CHIEU = ? AND GIO_CHIEU = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maPhim);
            ps.setDate(2, Date.valueOf(ngay));
            ps.setTime(3, Time.valueOf(gio));
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString("MA_SUAT");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public List<LocalTime> getGioChieuTheoNgay(String maPhim, LocalDate ngayChieu) {
        List<LocalTime> gioChieuList = new ArrayList<>();
        String sql = "SELECT THOI_GIAN_CHIEU FROM SUAT_CHIEU WHERE MA_PHIM = ? AND TRUNC(THOI_GIAN_CHIEU) = ? ORDER BY THOI_GIAN_CHIEU";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, maPhim);
            stmt.setDate(2, java.sql.Date.valueOf(ngayChieu));

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Timestamp timestamp = rs.getTimestamp("THOI_GIAN_CHIEU");
                    if (timestamp != null) {
                        gioChieuList.add(timestamp.toLocalDateTime().toLocalTime());
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return gioChieuList;
    }
    
    public List<SUAT_CHIEU> getSuatChieuByTenPhim(String tenPhim) {
        List<SUAT_CHIEU> danhSachSuatChieu = new ArrayList<>();
        String sql = "SELECT SC.MA_SUAT, SC.MA_PHIM, SC.MA_PHONG, SC.THOI_GIAN_CHIEU " +
                     "FROM SUAT_CHIEU SC JOIN PHIM P ON SC.MA_PHIM = P.MA_PHIM " +
                     "WHERE LOWER(P.TEN_PHIM) = LOWER(?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, tenPhim);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                SUAT_CHIEU sc = new SUAT_CHIEU();
                sc.setMaSuat(rs.getString("MA_SUAT"));
                sc.setMaPhim(rs.getString("MA_PHIM"));
                sc.setMaPhong(rs.getString("MA_PHONG"));
                sc.setThoiGianChieu(rs.getTimestamp("THOI_GIAN_CHIEU").toLocalDateTime());

                danhSachSuatChieu.add(sc);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return danhSachSuatChieu;
    }
    
    public String getFilmNameById(String maPhim) {
        String sql = "SELECT TEN_PHIM FROM PHIM WHERE MA_PHIM = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, maPhim);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString("TEN_PHIM");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "Không rõ";
    }
    
    // Lấy tên phòng từ mã phòng
    public String getRoomNameById(String maPhong) {
        String sql = "SELECT TEN_PHONG FROM PHONG_CHIEU WHERE MA_PHONG = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, maPhong);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString("TEN_PHONG");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "Không rõ";
    }
    
    
    public List<PHONG_CHIEU> getAllPhongChieu() {
        List<PHONG_CHIEU> list = new ArrayList<>();
        String sql = "SELECT MA_PHONG, TEN_PHONG FROM PHONG_CHIEU";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                PHONG_CHIEU pc = new PHONG_CHIEU();
                pc.setMaPhong(rs.getString("MA_PHONG"));
                pc.setTenPhong(rs.getString("TEN_PHONG"));
                list.add(pc);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<PHIM> getAllPhim() {
        List<PHIM> list = new ArrayList<>();
        String sql = "SELECT MA_PHIM, TEN_PHIM FROM PHIM";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                PHIM phim = new PHIM();
                phim.setMaPhim(rs.getString("MA_PHIM"));
                phim.setTenPhim(rs.getString("TEN_PHIM"));
                list.add(phim);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    
    public List<SUAT_CHIEU> getSuatChieuByTenPhong(String tenPhong) {
    List<SUAT_CHIEU> list = new ArrayList<>();
    String sql = "SELECT SC.MA_SUAT, SC.MA_PHIM, SC.MA_PHONG, SC.THOI_GIAN_CHIEU " +
                 "FROM SUAT_CHIEU SC JOIN PHONG_CHIEU PC ON SC.MA_PHONG = PC.MA_PHONG " +
                 "WHERE LOWER(PC.TEN_PHONG) = LOWER(?)";
    try (Connection conn = DBConnection.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setString(1, tenPhong);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            SUAT_CHIEU sc = new SUAT_CHIEU();
            sc.setMaSuat(rs.getString("MA_SUAT"));
            sc.setMaPhim(rs.getString("MA_PHIM"));
            sc.setMaPhong(rs.getString("MA_PHONG"));
            sc.setThoiGianChieu(rs.getTimestamp("THOI_GIAN_CHIEU").toLocalDateTime());
            list.add(sc);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return list;
}
    
    
}
