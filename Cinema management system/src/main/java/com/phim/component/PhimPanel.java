
package com.phim.component;

import com.phim.component.PhimDialog;
import com.phim.controller.PhimController;
import com.phim.model.PHIM;
import com.formdev.flatlaf.FlatLightLaf;
import java.util.List;
import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;

public class PhimPanel extends JPanel {
    private PhimController phimController;
    private JTable tblPhim;
    private DefaultTableModel tableModel;
    
    private JTextField txtTimKiem;

    public PhimPanel() {
        setLayout(new BorderLayout()); // Cực kỳ quan trọng!
        
        phimController = new PhimController();
        initComponents();
        loadPhimToTable();
    }

    private void initComponents() {
        setLayout(new BorderLayout());
          
        // Font lớn cho nút
        Font buttonFont = new Font("Segoe UI", Font.BOLD, 14);

        // Panel chứa nút chức năng
        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        // Tạo các nút với icon
        JButton btnThem = new JButton("Thêm", new ImageIcon(getClass().getResource("/Thêm.png")));
        JButton btnSua = new JButton("Sửa", new ImageIcon(getClass().getResource("/sửa.png")));
        JButton btnXoa = new JButton("Xóa", new ImageIcon(getClass().getResource("/xóa.png")));
        JButton btnLamMoi = new JButton("Làm Mới", new ImageIcon(getClass().getResource("/làm mới.png")));
        JButton btnTim = new JButton("Tìm", new ImageIcon(getClass().getResource("/tim.png")));

        txtTimKiem = new JTextField(20);

        // Tăng cỡ chữ cho nút
        JButton[] buttons = {btnThem, btnSua, btnXoa, btnLamMoi, btnTim};
        for (JButton btn : buttons) {
            btn.setFont(buttonFont);
            btn.setFocusPainted(false); // Tắt viền khi nhấn
        }

        // Tô màu cho các nút
        btnThem.setBackground(new Color(102, 255, 102));     // Xanh lá
        btnSua.setBackground(new Color(255, 255, 153));      // Vàng nhạt
        btnXoa.setBackground(new Color(255, 102, 102));      // Đỏ nhạt
        btnTim.setBackground(new Color(153, 204, 255));      // Xanh dương nhạt
        btnLamMoi.setBackground(new Color(192, 192, 192));   // Xám

        // Thêm vào panel
        controlPanel.add(btnThem);
        controlPanel.add(btnSua);
        controlPanel.add(btnXoa);
        controlPanel.add(btnLamMoi);
        controlPanel.add(new JLabel("Tên phim:"));
        controlPanel.add(txtTimKiem);
        controlPanel.add(btnTim);

        add(controlPanel, BorderLayout.NORTH);

        // Bảng phim
        String[] columnNames = {"Mã Phim", "Tên Phim", "Đạo Diễn", "Thể Loại", "Thời Lượng", "Mô Tả", "Trạng Thái"};
        tableModel = new DefaultTableModel(columnNames, 0);
        tblPhim = new JTable(tableModel);
        add(new JScrollPane(tblPhim), BorderLayout.CENTER);
        
        // Sự kiện nút
        btnThem.addActionListener(e -> {
             PhimDialog dialog = new PhimDialog((Window) SwingUtilities.getWindowAncestor(this), "Thêm Phim", null);
            dialog.setVisible(true);
            loadPhimToTable(); // Refresh lại sau khi đóng dialog   
        });
        btnSua.addActionListener(e -> {
            int row = tblPhim.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn phim để sửa.");
                return;
            }
            String maPhim = (String) tableModel.getValueAt(row, 0);
            PHIM phim = phimController.getPhimByMa(maPhim);
            if (phim != null) {
                PhimDialog dialog = new PhimDialog(SwingUtilities.getWindowAncestor(this), "Sửa phim", phim);
                dialog.setVisible(true);
                loadPhimToTable();
            } 
        });
        btnXoa.addActionListener(e -> {
            int row = tblPhim.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn phim để xóa.");
                return;
            }
            String maPhim = (String) tableModel.getValueAt(row, 0);
            int confirm = JOptionPane.showConfirmDialog(this, "Bạn chắc chắn muốn xóa phim này?");
            if (confirm == JOptionPane.YES_OPTION) {
                if (phimController.deletePhim(maPhim)) {
                    JOptionPane.showMessageDialog(this, "Xóa thành công");
                    loadPhimToTable();
                } else {
                    JOptionPane.showMessageDialog(this, "Xóa thất bại");
                }
            }    
        });
        btnTim.addActionListener(e -> {
            String tenPhim = txtTimKiem.getText().trim();
            List<PHIM> ds = phimController.searchPhimByTen(tenPhim);
            tableModel.setRowCount(0);
            for (PHIM p : ds) {
                tableModel.addRow(new Object[]{
                        p.getMaPhim(), p.getTenPhim(), p.getDaoDien(), p.getTheLoai(),
                        p.getThoiLuong(), p.getMoTa(), p.getTrangThai()
                });
            }    
        });
        btnLamMoi.addActionListener(e -> {
            txtTimKiem.setText("");
            loadPhimToTable();
        });
    }

    private void loadPhimToTable() {
        tableModel.setRowCount(0);
        List<PHIM> ds = phimController.getAllPhim();
        for (PHIM p : ds) {
            tableModel.addRow(new Object[]{
                p.getMaPhim(), p.getTenPhim(), p.getDaoDien(), p.getTheLoai(),
                p.getThoiLuong(), p.getMoTa(), p.getTrangThai()
            });
        }
    }
    
    

}
