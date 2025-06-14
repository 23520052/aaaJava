package com.phim.component;

import javax.swing.JPanel;
import com.phim.controller.ChiTietDatChoController;
import com.phim.controller.DatChoController;
import com.phim.controller.ThanhToanController;
import com.phim.controller.NhanVienController;
import com.phim.controller.KhachHangController;
import com.phim.controller.GheController;
import com.phim.controller.SuatChieuController;
import com.phim.controller.PhongChieuController;
import com.phim.controller.PhimController;

import com.phim.model.CHI_TIET_DAT_CHO;
import com.phim.model.THANH_TOAN;
import com.phim.model.NHAN_VIEN;
import com.phim.model.PHIM;
import com.phim.model.DAT_CHO;
import com.phim.model.GHE;
import com.phim.model.SUAT_CHIEU;
import com.phim.model.PHONG_CHIEU;

import com.formdev.flatlaf.FlatLightLaf;
import com.phim.dao.ThanhToanDAO;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class HoaDonPanel extends JPanel {
    private JTable table;
    private DefaultTableModel model;
    private ThanhToanController controller;

    public HoaDonPanel(ThanhToanController controller) {
        this.controller = controller;
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        // ===== NORTH: Nút và tìm kiếm =====
        JPanel topPanel = new JPanel(new BorderLayout());
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        buttonPanel.setBackground(Color.WHITE);

        JButton addButton = createIconButton("Thêm", "Thêm.png");
        JButton updateButton = createIconButton("Sửa", "sửa.png");
        JButton deleteButton = createIconButton("Xóa", "xóa.png");
        JButton refreshButton = createIconButton("Làm mới", "làm mới.png");

        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(refreshButton);

        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        searchPanel.setBackground(Color.WHITE);
        searchPanel.add(new JLabel("Tìm kiếm:"));
        JTextField searchField = new JTextField(15);
        searchPanel.add(searchField);

        topPanel.add(buttonPanel, BorderLayout.WEST);
        topPanel.add(searchPanel, BorderLayout.EAST);
        add(topPanel, BorderLayout.NORTH);

        // ===== CENTER: Bảng danh sách =====
        model = new DefaultTableModel(new String[]{
            "Mã TT", "Mã Đặt", "Mã NV", "Tổng tiền", "Thời gian", "Phương thức"
        }, 0);
        table = new JTable(model);
        styleModernTable(table);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // ===== Action Listeners =====
        addButton.addActionListener(e -> ThanhToanDialog.showCreateDialog(this, controller, this::loadData));
        updateButton.addActionListener(e -> ThanhToanDialog.showUpdateDialog(this, table, controller, this::loadData));
        deleteButton.addActionListener(e -> ThanhToanDialog.showDeleteDialog(this, table, controller, this::loadData));
        refreshButton.addActionListener(e -> loadData());
        searchField.addActionListener(e -> ThanhToanDialog.showSearchDialog(this, table, controller));

        loadData();
    }

    private void loadData() {
        model.setRowCount(0);
        List<THANH_TOAN> list = controller.getAllThanhToan();
        for (THANH_TOAN tt : list) {
            model.addRow(new Object[]{
                tt.getMaThanhToan(),
                tt.getMaDatCho(),
                tt.getMANV(),
                tt.getSoTien(),
                tt.getThoiGianThanhToan(),
                tt.getPhuongThuc()
            });
        }
    }

    private JButton createIconButton(String text, String iconName) {
        JButton btn = new JButton(text);
        try {
            ImageIcon icon = new ImageIcon(getClass().getResource("/" + iconName));
            btn.setIcon(icon);
        } catch (Exception e) {
            System.err.println("Không tìm thấy icon: " + iconName);
        }
        btn.setFocusPainted(false);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        return btn;
    }

    private void styleModernTable(JTable table) {
        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 14));
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        table.setRowHeight(28);
    }
}
    
    
 
