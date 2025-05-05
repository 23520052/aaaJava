package com.cinema.cinema_backend.controller;

import com.cinema.cinema_backend.entity.DatCho;
import com.cinema.cinema_backend.entity.ThanhToan;
import com.cinema.cinema_backend.service.ThanhToanService;
import com.cinema.cinema_backend.service.DatChoService;  // Bạn cần có dịch vụ DatChoService
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/thanh-toan")
public class ThanhToanController {

    @Autowired
    private ThanhToanService thanhToanService;

    @Autowired
    private DatChoService datChoService;  // Bạn cần phải tạo dịch vụ này để lấy đặt chỗ từ CSDL

    // Thực hiện thanh toán
    @PostMapping("/thanh-toan")
    public String thanhToan(@RequestParam String maDat, @RequestParam String phuongThuc, @RequestParam Double soTien) {
        Optional<DatCho> datChoOpt = datChoService.getDatChoById(maDat);

        if (!datChoOpt.isPresent()) {
            return "Đặt chỗ không tồn tại!";
        }

        DatCho datCho = datChoOpt.get();
        ThanhToan thanhToan = thanhToanService.saveThanhToan(datCho, phuongThuc, soTien);

        return "Thanh toán thành công, mã thanh toán: " + thanhToan.getMaTt();
    }

    // Lấy thông tin thanh toán của một mã đặt chỗ
    @GetMapping("/{maDat}")
    public Optional<ThanhToan> getThanhToan(@PathVariable String maDat) {
        return thanhToanService.getThanhToanByMaDat(maDat);
    }
}
