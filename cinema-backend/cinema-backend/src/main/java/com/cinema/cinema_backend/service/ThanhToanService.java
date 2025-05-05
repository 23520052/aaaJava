package com.cinema.cinema_backend.service;

import com.cinema.cinema_backend.entity.DatCho;
import com.cinema.cinema_backend.entity.ThanhToan;
import com.cinema.cinema_backend.repository.ThanhToanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class ThanhToanService {

    @Autowired
    private ThanhToanRepository thanhToanRepository;

    // Lưu thông tin thanh toán mới
    public ThanhToan saveThanhToan(DatCho datCho, String phuongThuc, Double soTien) {
        ThanhToan thanhToan = new ThanhToan(); // Oracle sẽ tự sinh mã thanh toán sequence
        thanhToan.setDatCho(datCho);
        thanhToan.setPhuongThuc(phuongThuc);
        thanhToan.setSoTien(soTien);
        thanhToan.setNgayThanhToan(new Date());  // Thời gian thanh toán là thời điểm hiện tại

        return thanhToanRepository.save(thanhToan);
    }

    // Lấy thông tin thanh toán theo mã đặt chỗ
    public Optional<ThanhToan> getThanhToanByMaDat(String maDat) {
        return thanhToanRepository.findByDatCho_MaDat(maDat);
    }
}
