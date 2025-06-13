package com.phim.dao;

import com.phim.model.DAT_CHO;
import com.phim.configs.DBConnection;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class DatChoDAO {
    // Thêm đặt chỗ
    public boolean addDatCho(DAT_CHO datCho) {
        String sql = "INSERT INTO DAT_CHO (MA_KH, MA_SUAT, NGAY_DAT) VALUES (?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, datCho.getMaKhach());
            ps.setString(2, datCho.getMaSuat());
            ps.setTimestamp(3, Timestamp.valueOf(datCho.getThoiGianDat()));
            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // Cập nhật đặt chỗ
    public boolean updateDatCho(DAT_CHO datCho) {
        String sql = "UPDATE DAT_CHO SET MA_KH = ?, MA_SUAT = ?, NGAY_DAT = ? WHERE MA_DAT = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, datCho.getMaKhach());
            ps.setString(2, datCho.getMaSuat());
            ps.setTimestamp(3, Timestamp.valueOf(datCho.getThoiGianDat()));
            ps.setString(4, datCho.getMaDatCho());
            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // Xóa đặt chỗ
    public boolean deleteDatCho(String maDatCho) {
        String sql = "DELETE FROM DAT_CHO WHERE MA_DAT = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, maDatCho);
            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // Lấy tất cả đặt chỗ
    public List<DAT_CHO> getAllDatCho() {
        List<DAT_CHO> list = new ArrayList<>();
        String sql = "SELECT * FROM DAT_CHO";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                DAT_CHO dat = new DAT_CHO(
                        rs.getString("MA_DAT"),
                        rs.getString("MA_KH"),
                        rs.getString("MA_SUAT"),
                        rs.getTimestamp("NGAY_DAT").toLocalDateTime()
                );
                list.add(dat);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // Lấy theo mã khách hàng
    public List<DAT_CHO> getDatChoByKhach(String maKhach) {
        List<DAT_CHO> list = new ArrayList<>();
        String sql = "SELECT * FROM DAT_CHO WHERE MA_KH = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, maKhach);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                DAT_CHO dat = new DAT_CHO(
                        rs.getString("MA_DAT"),
                        rs.getString("MA_KH"),
                        rs.getString("MA_SUAT"),
                        rs.getTimestamp("NGAY_DAT").toLocalDateTime()
                );
                list.add(dat);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // Lấy theo mã suất chiếu
    public List<DAT_CHO> getDatChoBySuatChieu(String maSuat) {
        List<DAT_CHO> list = new ArrayList<>();
        String sql = "SELECT * FROM DAT_CHO WHERE MA_SUAT = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, maSuat);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                DAT_CHO dat = new DAT_CHO(
                        rs.getString("MA_DAT"),
                        rs.getString("MA_KH"),
                        rs.getString("MA_SUAT"),
                        rs.getTimestamp("NGAY_DAT").toLocalDateTime()
                );
                list.add(dat);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // Lấy theo ngày đặt (chỉ lấy phần ngày, không lấy giờ)
    public List<DAT_CHO> getDatChoByNgay(LocalDateTime ngayDat) {
        List<DAT_CHO> list = new ArrayList<>();
        String sql = "SELECT * FROM DAT_CHO WHERE TRUNC(NGAY_DAT) = TRUNC(?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setTimestamp(1, Timestamp.valueOf(ngayDat));
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                DAT_CHO dat = new DAT_CHO(
                        rs.getString("MA_DAT"),
                        rs.getString("MA_KH"),
                        rs.getString("MA_SUAT"),
                        rs.getTimestamp("NGAY_DAT").toLocalDateTime()
                );
                list.add(dat);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
