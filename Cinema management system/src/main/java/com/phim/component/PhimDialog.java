
package com.phim.component;

import com.phim.model.PHIM;
import com.phim.controller.PhimController;

import javax.swing.*;
import java.awt.*;

public class PhimDialog extends JDialog {
    private JTextField txtMaPhim, txtTenPhim, txtDaoDien, txtTheLoai, txtThoiLuong, txtMoTa;
    private JComboBox<String> cbTrangThai;
    private JButton btnSave, btnCancel;
    private PHIM phim;
    private final PhimController phimController = new PhimController();

    public PhimDialog(Window parent, String title, PHIM phim) {
        super(parent, title, ModalityType.APPLICATION_MODAL);
        this.phim = phim;
        initUI(parent);
    }

    private void initUI(Component parent) {
        setLayout(new GridLayout(8, 2, 5, 5));
        setSize(500, 350);
        setLocationRelativeTo(parent);

        add(new JLabel("Mã Phim:"));
        txtMaPhim = new JTextField();
        add(txtMaPhim);

        add(new JLabel("Tên Phim:"));
        txtTenPhim = new JTextField();
        add(txtTenPhim);

        add(new JLabel("Đạo Diễn:"));
        txtDaoDien = new JTextField();
        add(txtDaoDien);

        add(new JLabel("Thể Loại:"));
        txtTheLoai = new JTextField();
        add(txtTheLoai);

        add(new JLabel("Thời Lượng (phút):"));
        txtThoiLuong = new JTextField();
        add(txtThoiLuong);

        add(new JLabel("Mô Tả:"));
        txtMoTa = new JTextField();
        add(txtMoTa);

        add(new JLabel("Trạng Thái:"));
        cbTrangThai = new JComboBox<>(new String[]{"Sắp chiếu", "Đang chiếu", "Ngừng chiếu"});
        add(cbTrangThai);

        btnSave = new JButton("Lưu");
        btnCancel = new JButton("Hủy");
        add(btnSave);
        add(btnCancel);

        // Nếu đang sửa, hiển thị dữ liệu cũ
        if (phim != null) {
            txtMaPhim.setText(phim.getMaPhim());
            txtMaPhim.setEditable(false); // Mã phim không cho sửa
            txtTenPhim.setText(phim.getTenPhim());
            txtDaoDien.setText(phim.getDaoDien());
            txtTheLoai.setText(phim.getTheLoai());
            txtThoiLuong.setText(String.valueOf(phim.getThoiLuong()));
            txtMoTa.setText(phim.getMoTa());
            cbTrangThai.setSelectedItem(phim.getTrangThai());
        }

        btnSave.addActionListener(e -> savePhim());
        btnCancel.addActionListener(e -> dispose());
    }

    private void savePhim() {
        String maPhim = txtMaPhim.getText().trim();
        String tenPhim = txtTenPhim.getText().trim();
        String daoDien = txtDaoDien.getText().trim();
        String theLoai = txtTheLoai.getText().trim();
        String thoiLuongStr = txtThoiLuong.getText().trim();
        String moTa = txtMoTa.getText().trim();
        String trangThai = (String) cbTrangThai.getSelectedItem();

        // Kiểm tra đầu vào
        if (maPhim.isEmpty() || tenPhim.isEmpty() || daoDien.isEmpty()
                || theLoai.isEmpty() || thoiLuongStr.isEmpty() || moTa.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng điền đầy đủ thông tin.");
            return;
        }

        int thoiLuong;
        try {
            thoiLuong = Integer.parseInt(thoiLuongStr);
            if (thoiLuong <= 0) throw new NumberFormatException();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Thời lượng phải là số nguyên dương.");
            return;
        }

        if (phim == null) {
            // Thêm mới
            PHIM newPhim = new PHIM(maPhim, tenPhim, daoDien, theLoai, thoiLuong, moTa, trangThai);
            if (phimController.getPhimByMa(maPhim) != null) {
                JOptionPane.showMessageDialog(this, "Mã phim đã tồn tại!");
                return;
            }
            if (phimController.addPhim(newPhim)) {
                JOptionPane.showMessageDialog(this, "Thêm phim thành công!");
            } else {
                JOptionPane.showMessageDialog(this, "Thêm phim thất bại!");
            }
        } else {
            // Cập nhật
            phim.setTenPhim(tenPhim);
            phim.setDaoDien(daoDien);
            phim.setTheLoai(theLoai);
            phim.setThoiLuong(thoiLuong);
            phim.setMoTa(moTa);
            phim.setTrangThai(trangThai);
            if (phimController.updatePhim(phim)) {
                JOptionPane.showMessageDialog(this, "Cập nhật phim thành công!");
            } else {
                JOptionPane.showMessageDialog(this, "Cập nhật phim thất bại!");
            }
        }

        dispose();
    }
}
