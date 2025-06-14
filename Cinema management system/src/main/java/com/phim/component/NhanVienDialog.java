package com.phim.component;

import javax.swing.*;
import java.awt.*;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import javax.swing.table.DefaultTableModel;

import com.phim.model.NHAN_VIEN;
import com.phim.controller.NhanVienController;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

public class NhanVienDialog {
    private static JPanel createNhanVienForm(NHAN_VIEN nv, boolean includeHireDate, boolean includePassword,
                                             JTextField fullNameField, JTextField roleField, JTextField phoneField,
                                             JTextField emailField, JTextField hireDateField, JPasswordField passwordField) {
        JPanel panel = new JPanel(new GridLayout(0, 1, 5, 5));
        fullNameField.setText(nv != null ? nv.getFullName() : "");
        roleField.setText(nv != null ? nv.getRole() : "");
        phoneField.setText(nv != null ? nv.getPhone() : "");
        emailField.setText(nv != null ? nv.getEmail() : "");
        if (includeHireDate && nv != null && nv.getHireDate() != null) {
            hireDateField.setText(nv.getHireDate().toString());
        }

        panel.add(new JLabel("Họ và tên:")); panel.add(fullNameField);
        panel.add(new JLabel("Chức vụ (ROLE):")); panel.add(roleField);
        panel.add(new JLabel("Số điện thoại:")); panel.add(phoneField);
        panel.add(new JLabel("Email:")); panel.add(emailField);

        if (includeHireDate) {
            panel.add(new JLabel("Ngày nhận việc (yyyy-MM-dd):"));
            panel.add(hireDateField);
        }
        if (includePassword) {
            panel.add(new JLabel("Mật khẩu:"));
            panel.add(passwordField);
        }

        return panel;
    }

    public static void showCreateDialog(Component parent, NhanVienController controller, Runnable onSuccess) {
        JTextField fullNameField = new JTextField(20);
        JTextField roleField = new JTextField(20);
        JTextField phoneField = new JTextField(20);
        JTextField emailField = new JTextField(20);
        JTextField hireDateField = new JTextField(20);
        JPasswordField passwordField = new JPasswordField(20);

        JPanel form = createNhanVienForm(null, true, true, fullNameField, roleField, phoneField, emailField, hireDateField, passwordField);

        int result = JOptionPane.showConfirmDialog(parent, form, "Thêm nhân viên mới",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            try {
                String fullName = fullNameField.getText().trim();
                String role = roleField.getText().trim();
                String phone = phoneField.getText().trim();
                String email = emailField.getText().trim();
                String password = new String(passwordField.getPassword()).trim();
                String hireDateStr = hireDateField.getText().trim();

                if (fullName.isEmpty() || role.isEmpty() || phone.isEmpty() || email.isEmpty() || password.isEmpty() || hireDateStr.isEmpty()) {
                    JOptionPane.showMessageDialog(parent, "Vui lòng nhập đầy đủ thông tin!", "Thiếu thông tin", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                LocalDate hireDate = LocalDate.parse(hireDateStr, DateTimeFormatter.ISO_LOCAL_DATE);
                NHAN_VIEN nv = new NHAN_VIEN(0, fullName, role, phone, email, hireDate, password);
                boolean success = controller.addNhanVien(nv);
                if (success) {
                    JOptionPane.showMessageDialog(parent, "Thêm nhân viên thành công!");
                    onSuccess.run();
                } else {
                    JOptionPane.showMessageDialog(parent, "Không thể thêm nhân viên!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }

            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(parent, "Dữ liệu không hợp lệ hoặc định dạng ngày sai!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public static void showUpdateDialog(Component parent, JTable table, NhanVienController controller, Runnable onSuccess) {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(parent, "Vui lòng chọn nhân viên để sửa!");
            return;
        }

        int maNv = (int) table.getValueAt(row, 0);
        NHAN_VIEN nv = controller.getNhanVienById(maNv);
        if (nv == null) {
            JOptionPane.showMessageDialog(parent, "Không tìm thấy nhân viên!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        JTextField fullNameField = new JTextField(20);
        JTextField roleField = new JTextField(20);
        JTextField phoneField = new JTextField(20);
        JTextField emailField = new JTextField(20);
        JTextField dummyHireDateField = new JTextField(); // Không dùng nhưng cần truyền
        JPasswordField passwordField = new JPasswordField(20);

        JPanel form = createNhanVienForm(nv, false, true, fullNameField, roleField, phoneField, emailField, dummyHireDateField, passwordField);

        int result = JOptionPane.showConfirmDialog(parent, form, "Cập nhật nhân viên - Mã: " + maNv,
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            String fullName = fullNameField.getText().trim();
            String role = roleField.getText().trim();
            String phone = phoneField.getText().trim();
            String email = emailField.getText().trim();
            String password = new String(passwordField.getPassword()).trim();

            if (fullName.isEmpty() || role.isEmpty() || phone.isEmpty() || email.isEmpty()) {
                JOptionPane.showMessageDialog(parent, "Không được bỏ trống các trường thông tin chính!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            nv.setFullName(fullName);
            nv.setRole(role);
            nv.setPhone(phone);
            nv.setEmail(email);
            if (!password.isEmpty()) nv.setPassword(password);

            if (controller.updateNhanVien(nv)) {
                JOptionPane.showMessageDialog(parent, "Cập nhật thành công!");
                onSuccess.run();
            } else {
                JOptionPane.showMessageDialog(parent, "Cập nhật thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public static void showDeleteDialog(Component parent, JTable table, NhanVienController controller, Runnable onSuccess) {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(parent, "Vui lòng chọn nhân viên để xóa!");
            return;
        }

        int maNv = (int) table.getValueAt(row, 0);
        NHAN_VIEN nv = controller.getNhanVienById(maNv);
        if (nv == null) {
            JOptionPane.showMessageDialog(parent, "Không tìm thấy nhân viên!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(parent,
                "Bạn chắc chắn muốn xóa nhân viên này?\nHọ tên: " + nv.getFullName(),
                "Xác nhận", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            if (controller.deleteNhanVien(maNv)) {
                JOptionPane.showMessageDialog(parent, "Xóa thành công!");
                onSuccess.run();
            } else {
                JOptionPane.showMessageDialog(parent, "Xóa thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public static void showSearchDialog(Component parent, JTable table, NhanVienController controller) {
        String keyword = JOptionPane.showInputDialog(parent, "Nhập từ khóa tìm kiếm theo tên:");
        if (keyword == null || keyword.trim().isEmpty()) return;

        List<NHAN_VIEN> filtered = controller.getAllNhanVien().stream()
                .filter(nv -> nv.getFullName() != null &&
                        Arrays.stream(nv.getFullName().toLowerCase().split("\\s+"))
                              .anyMatch(part -> part.equals(keyword.trim().toLowerCase())))
                .collect(Collectors.toList());

        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);
        for (NHAN_VIEN nv : filtered) {
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
}
