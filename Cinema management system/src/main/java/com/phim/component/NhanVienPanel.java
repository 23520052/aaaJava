package com.phim.component;

import com.formdev.flatlaf.FlatLightLaf;
import com.phim.controller.NhanVienController;
import com.phim.model.NHAN_VIEN;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Window;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;


public class NhanVienPanel extends JPanel {
    private JTable table;
    private DefaultTableModel model;
    private NhanVienController controller;

    public NhanVienPanel(NhanVienController controller) {
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
        model = new DefaultTableModel(new String[]{"Mã", "Họ tên", "Role", "SĐT", "Email", "Ngày nhận việc"}, 0);
        table = new JTable(model);
        styleModernTable(table);

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // ===== Action Listeners =====
        addButton.addActionListener(e -> NhanVienDialog.showCreateDialog(this, controller, this::loadData));
        updateButton.addActionListener(e -> NhanVienDialog.showUpdateDialog(this, table, controller, this::loadData));
        deleteButton.addActionListener(e -> NhanVienDialog.showDeleteDialog(this, table, controller, this::loadData));
        refreshButton.addActionListener(e -> loadData());
        searchField.addActionListener(e -> NhanVienDialog.showSearchDialog(this, table, controller));

        loadData();
    }

    private void loadData() {
        model.setRowCount(0);
        List<NHAN_VIEN> list = controller.getAllNhanVien();
        for (NHAN_VIEN nv : list) {
            model.addRow(new Object[]{
                nv.getMaNv(),
                nv.getFullName(),
                nv.getRole(),
                nv.getPhone(),
                nv.getEmail(),
                nv.getHireDate()
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
        // Không còn renderer nữa => dùng mặc định
    }
}
