package com.phim.component;

import com.phim.controller.SuatChieuController;
import com.phim.model.PHIM;
import com.phim.model.PHONG_CHIEU;
import com.phim.model.SUAT_CHIEU;
import java.awt.Component;
import java.awt.GridLayout;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
import javax.swing.table.DefaultTableModel;


public class SuatChieuDialog extends JDialog{
    private JComboBox<String> filmComboBox;
    private JComboBox<String> roomComboBox;
    private JTextField dateTimeField;
    private JButton saveButton, cancelButton;

    private SuatChieuController controller;
    private Runnable onSaveCallback;
    private SUAT_CHIEU suatChieu;

    private Map<String, String> filmNameToId = new HashMap<>();
    private Map<String, String> roomNameToId = new HashMap<>();

    public static void showCreateDialog(Component parent, SuatChieuController controller, Runnable onSave) {
        SuatChieuDialog dialog = new SuatChieuDialog(controller, onSave, null);
        dialog.setTitle("Thêm suất chiếu");
        dialog.setLocationRelativeTo(parent);
        dialog.setVisible(true);
    }

    public static void showUpdateDialog(Component parent, JTable table, SuatChieuController controller, Runnable onSave) {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(parent, "Vui lòng chọn suất chiếu để sửa.");
            return;
        }
        String maSuat = table.getValueAt(row, 0).toString();
        SUAT_CHIEU sc = controller.getSuatChieuByMaSuat(maSuat);
        SuatChieuDialog dialog = new SuatChieuDialog(controller, onSave, sc);
        dialog.setTitle("Sửa suất chiếu");
        dialog.setLocationRelativeTo(parent);
        dialog.setVisible(true);
    }
    
    
    public static void showDeleteDialog(Component parent, JTable table, SuatChieuController controller, Runnable onSuccess) {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(parent, "Vui lòng chọn dòng cần xóa!");
            return;
        }
        String maSuat = table.getValueAt(selectedRow, 0).toString();
        int confirm = JOptionPane.showConfirmDialog(parent, "Xác nhận xóa suất chiếu này?", "Xác nhận", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            controller.deleteSuatChieu(maSuat);
            JOptionPane.showMessageDialog(parent, "Đã xóa thành công!");
            onSuccess.run();
        }
    }

    public static void showSearchByFilmDialog(Component parent, JTable table, SuatChieuController controller) {
        String tenPhim = JOptionPane.showInputDialog(parent, "Nhập tên phim cần tìm:");
        if (tenPhim != null && !tenPhim.trim().isEmpty()) {
            List<SUAT_CHIEU> list = controller.laySuatChieuTheoTenPhim(tenPhim.trim());
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            model.setRowCount(0);
            for (SUAT_CHIEU sc : list) {
                String ten = controller.getFilmNameById(sc.getMaPhim());
                String phong = controller.getRoomNameById(sc.getMaPhong());
                model.addRow(new Object[]{
                    sc.getMaSuat(), ten, phong, sc.getThoiGianChieu()
                });
            }
        }
    }
    
    public static void showSearchByRoomDialog(Component parent, JTable table, SuatChieuController controller) {
        String tenPhong = JOptionPane.showInputDialog(parent, "Nhập tên phòng cần tìm:");
        if (tenPhong != null && !tenPhong.trim().isEmpty()) {
            List<SUAT_CHIEU> list = controller.getSuatChieuByTenPhong(tenPhong.trim());
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            model.setRowCount(0);
            for (SUAT_CHIEU sc : list) {
                String ten = controller.getFilmNameById(sc.getMaPhim());
                String phong = controller.getRoomNameById(sc.getMaPhong());
                model.addRow(new Object[]{
                    sc.getMaSuat(), ten, phong, sc.getThoiGianChieu()
                });
            }
        }
    }

    

    private SuatChieuDialog(SuatChieuController controller, Runnable onSaveCallback, SUAT_CHIEU suatChieu) {
        this.controller = controller;
        this.onSaveCallback = onSaveCallback;
        this.suatChieu = suatChieu;

        setModal(true);
        setSize(400, 300);
        setLayout(new GridLayout(5, 2, 10, 10));
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        add(new JLabel("Phim:"));
        filmComboBox = new JComboBox<>();
        loadFilms();
        add(filmComboBox);

        add(new JLabel("Phòng chiếu:"));
        roomComboBox = new JComboBox<>();
        loadRooms();
        add(roomComboBox);

        add(new JLabel("Thời gian chiếu (yyyy-MM-dd HH:mm):"));
        dateTimeField = new JTextField();
        add(dateTimeField);

        saveButton = new JButton("Lưu");
        cancelButton = new JButton("Hủy");

        add(saveButton);
        add(cancelButton);

        if (suatChieu != null) {
            // Set giá trị cũ vào form
            filmComboBox.setSelectedItem(controller.getFilmNameById(suatChieu.getMaPhim()));
            roomComboBox.setSelectedItem(controller.getRoomNameById(suatChieu.getMaPhong()));
            dateTimeField.setText(suatChieu.getThoiGianChieu().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        }

        saveButton.addActionListener(e -> save());
        cancelButton.addActionListener(e -> dispose());
    }

    private void loadFilms() {
        List<PHIM> films = controller.getAllPhim();
        for (PHIM phim : films) {
            filmComboBox.addItem(phim.getTenPhim());
            filmNameToId.put(phim.getTenPhim(), phim.getMaPhim());
        }
    }

    private void loadRooms() {
        List<PHONG_CHIEU> rooms = controller.getAllPhongChieu();
        for (PHONG_CHIEU pc : rooms) {
            roomComboBox.addItem(pc.getTenPhong());
            roomNameToId.put(pc.getTenPhong(), pc.getMaPhong());
        }
    }

    private void save() {
        try {
            String tenPhim = (String) filmComboBox.getSelectedItem();
            String tenPhong = (String) roomComboBox.getSelectedItem();
            String maPhim = filmNameToId.get(tenPhim);
            String maPhong = roomNameToId.get(tenPhong);
            LocalDateTime thoiGian = LocalDateTime.parse(dateTimeField.getText(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));

            if (suatChieu == null) {
                // Create mode
                SUAT_CHIEU newSC = new SUAT_CHIEU(null, maPhim, maPhong, thoiGian);
                controller.addSuatChieu(newSC);
            } else {
                // Update mode
                suatChieu.setMaPhim(maPhim);
                suatChieu.setMaPhong(maPhong);
                suatChieu.setThoiGianChieu(thoiGian);
                controller.updateSuatChieu(suatChieu);
            }

            JOptionPane.showMessageDialog(this, "Lưu thành công!");
            onSaveCallback.run();
            dispose();
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi: " + ex.getMessage());
        }
    }
}
