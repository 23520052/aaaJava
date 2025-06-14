
package com.phim.view;

import com.phim.component.CashierHeader;
import com.phim.controller.ThanhToanController;
import com.phim.controller.PhimController;
import com.phim.controller.ChiTietDatChoController;
import com.phim.model.NHAN_VIEN;
import com.phim.model.PHIM;
import com.phim.utils.PhanQuyenNV;
import com.formdev.flatlaf.FlatLightLaf;
import com.phim.controller.GheController;
import com.phim.controller.GiaGheController;
import com.phim.controller.NhanVienController;
import com.phim.controller.NhanVienDatVeController;
import com.phim.controller.SuatChieuController;
import com.phim.model.CHI_TIET_DAT_CHO;
import com.phim.model.GHE;
import com.phim.model.GIA_GHE;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.List;


public class NhanVienView extends JFrame {
    private JTable tblPhim, tblGheTrong, tblSelectedTickets, tblGheChon;
    private JLabel lblTotalAmount, lblTongTien;
    private JComboBox<String> cboShowTime, cboRoom, cboPaymentMethod, cboPTTT;
    private JButton btnAddTicket, btnRemoveTicket, btnClear, btnPay;
    private JButton btnThem, btnXoa, btnThanhToan;
    private JTextField txtCustomerName, txtPhone, txtDob, txtMaNhanVien, txtSearchPhim, txtTenKH, txtSDT;
    private JList<String> listPhim, listSuat;

    public NhanVienView() {
        setTitle("🎫 Giao diện Đặt Vé - Thanh Toán");
        setSize(1100, 750);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // ===== Top =====
        JPanel topPanel = new JPanel(new BorderLayout(10, 10));
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        txtSearchPhim = new JTextField(20);
        searchPanel.add(new JLabel("🔍 Tìm phim:"));
        searchPanel.add(txtSearchPhim);

        JPanel comboPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        cboShowTime = new JComboBox<>(new String[]{"-- Chọn Suất Chiếu --"});
        cboRoom = new JComboBox<>(new String[]{"-- Chọn Phòng --"});
        comboPanel.add(new JLabel("Suất chiếu:"));
        comboPanel.add(cboShowTime);
        comboPanel.add(new JLabel("Phòng:"));
        comboPanel.add(cboRoom);

        topPanel.add(searchPanel, BorderLayout.NORTH);
        topPanel.add(comboPanel, BorderLayout.SOUTH);

        // ===== Center =====
        JPanel centerPanel = new JPanel(new GridLayout(1, 3, 10, 0));
        tblPhim = new JTable(new DefaultTableModel(new Object[]{"Mã phim", "Tên phim", "Thể loại", "Thời lượng"}, 0));
        tblGheTrong = new JTable(new DefaultTableModel(new Object[]{"Mã ghế", "Loại ghế", "Giá"}, 0));
        tblSelectedTickets = new JTable(new DefaultTableModel(new Object[]{"Ghế", "Loại", "Giá"}, 0));
        tblGheChon = new JTable(new DefaultTableModel(new Object[]{"Mã", "Loại", "Giá"}, 0));

        centerPanel.add(new JScrollPane(tblPhim));
        centerPanel.add(new JScrollPane(tblGheTrong));
        centerPanel.add(new JScrollPane(tblSelectedTickets));

        // ===== Bottom =====
        JPanel bottomPanel = new JPanel(new BorderLayout(10, 10));
        JPanel infoPanel = new JPanel(new GridLayout(2, 4, 10, 10));
        txtCustomerName = new JTextField();
        txtPhone = new JTextField();
        txtDob = new JTextField();
        txtMaNhanVien = new JTextField();
        txtMaNhanVien.setVisible(false);
        txtTenKH = new JTextField();
        txtSDT = new JTextField();

        infoPanel.add(new JLabel("Tên KH:"));
        infoPanel.add(txtCustomerName);
        infoPanel.add(new JLabel("SĐT:"));
        infoPanel.add(txtPhone);
        infoPanel.add(new JLabel("Ngày sinh:"));
        infoPanel.add(txtDob);
        infoPanel.add(new JLabel(""));
        infoPanel.add(txtMaNhanVien);

        JPanel paymentPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        cboPaymentMethod = new JComboBox<>(new String[]{"Tiền mặt", "Chuyển khoản", "Ví điện tử"});
        cboPTTT = new JComboBox<>(new String[]{"Tiền mặt", "Chuyển khoản", "Ví điện tử"});

        paymentPanel.add(new JLabel("Phương thức thanh toán:"));
        paymentPanel.add(cboPaymentMethod);

        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        btnAddTicket = new JButton("➕ Thêm vé");
        btnRemoveTicket = new JButton("➖ Huỷ vé");
        btnClear = new JButton("🧹 Xoá tất cả");
        btnPay = new JButton("💳 Thanh toán");

        btnThem = new JButton("Thêm");
        btnXoa = new JButton("Xoá");
        btnThanhToan = new JButton("Thanh toán");

        actionPanel.add(btnAddTicket);
        actionPanel.add(btnRemoveTicket);
        actionPanel.add(btnClear);
        actionPanel.add(btnPay);

        JPanel totalPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        lblTotalAmount = new JLabel("Tổng tiền: 0 đ");
        lblTongTien = new JLabel("Tổng tiền: 0 đ");
        totalPanel.add(lblTotalAmount);

        JPanel infoActionPanel = new JPanel(new BorderLayout());
        infoActionPanel.add(infoPanel, BorderLayout.NORTH);
        infoActionPanel.add(paymentPanel, BorderLayout.CENTER);
        infoActionPanel.add(actionPanel, BorderLayout.SOUTH);

        bottomPanel.add(infoActionPanel, BorderLayout.CENTER);
        bottomPanel.add(totalPanel, BorderLayout.SOUTH);

        // ===== Main Layout =====
        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);
        add(mainPanel);

        // ===== Danh sách phụ =====
        listPhim = new JList<>();
        listSuat = new JList<>();
    }

    // ===== Getters =====
    public JTable getTblPhim() { return tblPhim; }
    public JTable getTblGheTrong() { return tblGheTrong; }
    public JTable getTblSelectedTickets() { return tblSelectedTickets; }
    public JTable getTblGheChon() { return tblGheChon; }

    public JLabel getLblTotalAmount() { return lblTotalAmount; }
    public JLabel getLblTongTien() { return lblTongTien; }

    public JComboBox<String> getCboShowTime() { return cboShowTime; }
    public JComboBox<String> getCboRoom() { return cboRoom; }
    public JComboBox<String> getCboPaymentMethod() { return cboPaymentMethod; }
    public JComboBox<String> getCboPTTT() { return cboPTTT; }

    public JButton getBtnAddTicket() { return btnAddTicket; }
    public JButton getBtnRemoveTicket() { return btnRemoveTicket; }
    public JButton getBtnClear() { return btnClear; }
    public JButton getBtnPay() { return btnPay; }

    public JButton getBtnThem() { return btnThem; }
    public JButton getBtnXoa() { return btnXoa; }
    public JButton getBtnThanhToan() { return btnThanhToan; }

    public JTextField getTxtCustomerName() { return txtCustomerName; }
    public JTextField getTxtPhone() { return txtPhone; }
    public JTextField getTxtDob() { return txtDob; }
    public JTextField getTxtMaNhanVien() { return txtMaNhanVien; }
    public JTextField getTxtSearchPhim() { return txtSearchPhim; }
    public JTextField getTxtTenKH() { return txtTenKH; }
    public JTextField getTxtSDT() { return txtSDT; }

    public JList<String> getListPhim() { return listPhim; }
    public JList<String> getListSuat() { return listSuat; }

    public int getMaNhanVien() {
        return Integer.parseInt(txtMaNhanVien.getText());
    }

    public void setMaNhanVien(int maNV) {
        txtMaNhanVien.setText(String.valueOf(maNV));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            NhanVienView view = new NhanVienView();
            new NhanVienDatVeController(view); // Gắn controller xử lý
            view.setVisible(true);
        });
    }

} 

 

