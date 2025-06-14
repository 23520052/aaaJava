package com.phim.view;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Frame;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

import com.phim.component.AdDashBoard;
import com.phim.component.HoaDonPanel;
import com.phim.component.NhanVienPanel;
import com.phim.component.SuatChieuPanel;
import com.phim.controller.NhanVienController;
import com.phim.controller.SuatChieuController;
import com.phim.controller.ThanhToanController;

public class QuanLyView extends javax.swing.JFrame {
    private CardLayout cardLayout;

    public QuanLyView() {
        try {
            //Thiết lập giao diện theo hệ điều hành (Windows, macOS,...)
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            initComponents();// Khởi tạo giao diện gốc
            initCustomComponents();// Khởi tạo các panel bên trong giao diện

            setMinimumSize(new Dimension(800, 600)); // Kích thước tối thiểu
            setLocationRelativeTo(null);             // Canh giữa màn hình
            setExtendedState(JFrame.MAXIMIZED_BOTH); // toàn màn hình
            setVisible(true);                        // Hiển thị

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi khởi tạo giao diện: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
    
// Khởi tạo các panel tùy chỉnh và xử lý chuyển đổi panel
    private void initCustomComponents() {
        
        
        // Khởi tạo CardLayout cho CardPanel
        cardLayout = (CardLayout) CardPanel.getLayout();
        // Khởi tạo controller
        SuatChieuController suatChieuController = new SuatChieuController();
        NhanVienController nhanVienController = new NhanVienController();
        ThanhToanController hoaDonController = new ThanhToanController();
        // Thêm các panel nội dung vào CardPanel với key tương ứng(giống như tab nhưng ẩn)
        CardPanel.add(new com.phim.component.PhimPanel(),     "PHIM");
        CardPanel.add(new NhanVienPanel(nhanVienController), "EMPLOYEE");
        CardPanel.add(new AdDashBoard(), "DASHBOARD");
        CardPanel.add(new HoaDonPanel(hoaDonController), "HOADON");
        CardPanel.add(new SuatChieuPanel(suatChieuController), "SUATCHIEU");
        

        // Hiển thị panel mặc định
        cardLayout.show(CardPanel, "DASHBOARD");

        // Lắng nghe sự kiện chọn phim trên sidebar
        sidebar.setPhimSelectionListener(phimKey -> {
            System.out.println("DEBUG: phimKey nhận được là -> " + phimKey);
        // Giả sử sidebar trả về các key như "PHIM", "NHÂN VIÊN", "DASHBOARD", "HÓA ĐƠN"
            cardLayout.show(CardPanel, phimKey);
        });
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {
        // Khởi tạo các thành phần chính
        cashierHeader = new com.phim.component.CashierHeader();// Phần tiêu đề phía trên
        DisplayPanel   = new javax.swing.JPanel(); // Panel chứa sidebar + nội dung
        sidebar        = new com.phim.component.Sidebar();// Menu bên trái
        jspAdmin       = new javax.swing.JScrollPane();// Scroll panel bọc nội dung
        CardPanel      = new javax.swing.JPanel();// Panel chứa nội dung chính

        // Đóng chương trình khi tắt
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        // Header có chiều cao tối thiểu
        cashierHeader.setMinimumSize(new java.awt.Dimension(170, 40));
        // Đặt header lên phía trên
        getContentPane().add(cashierHeader, java.awt.BorderLayout.PAGE_START);

        // CardPanel sẽ dùng CardLayout kiểu (0,0)
        CardPanel.setLayout(new java.awt.CardLayout());
        jspAdmin.setViewportView(CardPanel);

        javax.swing.GroupLayout DisplayPanelLayout = new javax.swing.GroupLayout(DisplayPanel);
        // Bố cục ngang: sidebar cố định 201px, phần còn lại là jspAdmin
        DisplayPanel.setLayout(DisplayPanelLayout);
        DisplayPanelLayout.setHorizontalGroup(
                DisplayPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(DisplayPanelLayout.createSequentialGroup()
                                .addComponent(sidebar, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(6, 6, 6)
                                .addComponent(jspAdmin, javax.swing.GroupLayout.DEFAULT_SIZE, 669, Short.MAX_VALUE))
        );
        // Bố cục dọc: sidebar và jspAdmin cùng chiều cao
        DisplayPanelLayout.setVerticalGroup(
                DisplayPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(DisplayPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(DisplayPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(sidebar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jspAdmin, javax.swing.GroupLayout.DEFAULT_SIZE, 397, Short.MAX_VALUE)))
        );

        getContentPane().add(DisplayPanel, java.awt.BorderLayout.CENTER);

        pack(); // Tự điều chỉnh kích thước
    }
    // Chạy chương trình
    public static void main(String args[]) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        // Tạo form đăng nhập đầu tiên
        EventQueue.invokeLater(() -> {
            LoginForm view = new LoginForm();
            view.setVisible(true);
        });
    }

    // Variables declaration - do not modify
    private com.phim.component.CashierHeader cashierHeader;
    private javax.swing.JPanel CardPanel;
    private javax.swing.JPanel DisplayPanel;
    private javax.swing.JScrollPane jspAdmin;
    private com.phim.component.Sidebar sidebar;
}
