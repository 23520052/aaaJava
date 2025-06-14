
package com.phim.component;

import com.phim.controller.ThanhToanController;
import com.phim.model.THANH_TOAN;
import java.awt.Component;
import java.awt.GridLayout;
import java.time.LocalDateTime;
import java.util.List;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;


public class ThanhToanDialog {
    // Tạo thanh toán mới
    public static void showCreateDialog(Component parent, ThanhToanController controller, Runnable reloadCallback) {
        JTextField maDatField = new JTextField();
        JTextField maNvField = new JTextField();
        JTextField tongTienField = new JTextField();
        JComboBox<String> phuongThucBox = new JComboBox<>(new String[]{"Tiền mặt", "Thẻ tín dụng", "Ví điện tử", "Chuyển khoản"});

        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Mã Đặt chỗ:"));
        panel.add(maDatField);
        panel.add(new JLabel("Mã Nhân viên:"));
        panel.add(maNvField);
        panel.add(new JLabel("Tổng tiền:"));
        panel.add(tongTienField);
        panel.add(new JLabel("Phương thức thanh toán:"));
        panel.add(phuongThucBox);

        int result = JOptionPane.showConfirmDialog(parent, panel, "Tạo hóa đơn mới",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
        try {   
            String maDat = maDatField.getText().trim();
            int maNv = Integer.parseInt(maNvField.getText().trim());
            double tongTien = Double.parseDouble(tongTienField.getText().trim());
            String phuongThuc = (String) phuongThucBox.getSelectedItem();

            THANH_TOAN tt = new THANH_TOAN(
                null,
                maDat, maNv, tongTien,
                LocalDateTime.now(),
                phuongThuc
            );

            controller.addThanhToan(tt);
            JOptionPane.showMessageDialog(parent, "Tạo hóa đơn thành công!");
            reloadCallback.run();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(parent, "Lỗi: " + ex.getMessage());
            }
        }
    }

    // Sửa thanh toán
    public static void showUpdateDialog(Component parent, JTable table, ThanhToanController controller, Runnable reloadCallback) {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(parent, "Vui lòng chọn một hóa đơn để sửa!");
            return;
        }

        String maTT = table.getValueAt(row, 0).toString();
        THANH_TOAN tt = controller.getById(maTT);

        JTextField tongTienField = new JTextField(String.valueOf(tt.getSoTien()));
        JComboBox<String> phuongThucBox = new JComboBox<>(new String[]{"Tiền mặt", "Thẻ tín dụng", "Ví điện tử", "Chuyển khoản"});
        phuongThucBox.setSelectedItem(tt.getPhuongThuc());

        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Tổng tiền:"));
        panel.add(tongTienField);
        panel.add(new JLabel("Phương thức thanh toán:"));
        panel.add(phuongThucBox);

        int result = JOptionPane.showConfirmDialog(parent, panel, "Cập nhật hóa đơn",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            double tongTien = Double.parseDouble(tongTienField.getText().trim());
            String phuongThuc = (String) phuongThucBox.getSelectedItem();

            tt.setSoTien(tongTien);
            tt.setPhuongThuc(phuongThuc);
            controller.update(tt);
            JOptionPane.showMessageDialog(parent, "Cập nhật thành công!");
            reloadCallback.run();
        }
    }

    // Xóa thanh toán
    public static void showDeleteDialog(Component parent, JTable table, ThanhToanController controller, Runnable reloadCallback) {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(parent, "Vui lòng chọn hóa đơn để xóa!");
            return;
        }
        String maTT = table.getValueAt(row, 0).toString();

        int confirm = JOptionPane.showConfirmDialog(parent,
                "Bạn có chắc chắn muốn xóa hóa đơn: " + maTT + "?", "Xác nhận xóa",
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            controller.deleteThanhToan(maTT);
            JOptionPane.showMessageDialog(parent, "Đã xóa hóa đơn.");
            reloadCallback.run();
        }
    }

    // Tìm kiếm
    public static void showSearchDialog(Component parent, JTable table, ThanhToanController controller) {
        String keyword = JOptionPane.showInputDialog(parent, "Nhập mã thanh toán hoặc phương thức:");
        if (keyword == null || keyword.trim().isEmpty()) return;

        List<THANH_TOAN> resultList = controller.search(keyword.trim());
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);

        for (THANH_TOAN tt : resultList) {
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
}
