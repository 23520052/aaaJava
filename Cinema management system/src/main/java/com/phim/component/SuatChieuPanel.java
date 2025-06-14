
package com.phim.component;

import com.phim.controller.SuatChieuController;
import com.phim.model.SUAT_CHIEU;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;


public class SuatChieuPanel extends JPanel {
    private JTable table;
    private DefaultTableModel model;
    private SuatChieuController controller;

    public SuatChieuPanel(SuatChieuController controller) {
        this.controller = controller;
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        // ==== NORTH ====
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
        JButton searchByFilmButton = new JButton("Tìm theo phim");
        JButton searchByRoomButton = new JButton("Tìm theo phòng");
        searchPanel.add(searchByFilmButton);
        searchPanel.add(searchByRoomButton);

        topPanel.add(buttonPanel, BorderLayout.WEST);
        topPanel.add(searchPanel, BorderLayout.EAST);
        add(topPanel, BorderLayout.NORTH);

        // ==== CENTER ====
        model = new DefaultTableModel(new String[]{"Mã suất", "Tên phim", "Phòng chiếu", "Thời gian chiếu"}, 0);
        table = new JTable(model);
        styleModernTable(table);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // ==== ACTIONS ====
        addButton.addActionListener(e -> SuatChieuDialog.showCreateDialog(this, controller, this::loadData));
        updateButton.addActionListener(e -> SuatChieuDialog.showUpdateDialog(this, table, controller, this::loadData));
        deleteButton.addActionListener(e -> SuatChieuDialog.showDeleteDialog(this, table, controller, this::loadData));
        refreshButton.addActionListener(e -> loadData());
        searchByFilmButton.addActionListener(e -> SuatChieuDialog.showSearchByFilmDialog(this, table, controller));
        searchByRoomButton.addActionListener(e -> SuatChieuDialog.showSearchByRoomDialog(this, table, controller));

        // Load initial data
        loadData();
    }

    private void loadData() {
        model.setRowCount(0);
        List<SUAT_CHIEU> list = controller.getAllSuatChieu();
        for (SUAT_CHIEU sc : list) {
            String tenPhim = controller.getFilmNameById(sc.getMaPhim());
            String tenPhong = controller.getRoomNameById(sc.getMaPhong());
            model.addRow(new Object[]{
                sc.getMaSuat(),
                tenPhim,
                tenPhong,
                sc.getThoiGianChieu()
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
