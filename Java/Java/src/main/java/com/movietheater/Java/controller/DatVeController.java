package com.movietheater.Java.controller;

import com.movietheater.Java.dto.*;
import com.movietheater.Java.exception.BookingException;
import com.movietheater.Java.service.DatVeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/datve")
public class DatVeController {
    @Autowired
    private DatVeService datVeService;

    @PostMapping("/dat")
    public ResponseEntity<?> datVe(@Valid @RequestBody BookingRequestDTO dto, BindingResult result) {
        if (result.hasErrors()) {
            String msg = result.getAllErrors().stream().map(ObjectError::getDefaultMessage)
                    .collect(Collectors.joining(", "));
            return ResponseEntity.badRequest().body("Lỗi dữ liệu: " + msg);
        }
        return ResponseEntity.ok(datVeService.datVe(dto));
    }

    @PostMapping("/thanh-toan")
    public ResponseEntity<?> thanhToan(@Valid @RequestBody PaymentDTO dto, BindingResult result) {
        if (result.hasErrors()) {
            String msg = result.getAllErrors().stream()
                    .map(ObjectError::getDefaultMessage)
                    .collect(Collectors.joining(", "));
            return ResponseEntity.badRequest().body("Lỗi dữ liệu: " + msg);
        }

        PaymentResultDTO response = datVeService.thanhToan(dto);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/thong-tin/{maDat}")
    public ResponseEntity<BookingInfoDTO> layThongTinDatCho(@PathVariable String maDat) {
        BookingInfoDTO bookingInfo = datVeService.layThongTinDatCho(maDat);
        return ResponseEntity.ok(bookingInfo);
    }

    @PostMapping("/huy")
    public ResponseEntity<?> huyVe(@Valid @RequestBody CancelBookingDTO dto, BindingResult result) {
        if (result.hasErrors()) {
            String msg = result.getAllErrors().stream()
                    .map(ObjectError::getDefaultMessage)
                    .collect(Collectors.joining(", "));
            return ResponseEntity.badRequest().body("Lỗi dữ liệu: " + msg);
        }

        try {
            datVeService.huyVe(dto);
            return ResponseEntity.ok("Đã hủy đặt vé thành công.");
        } catch (BookingException e) {
            return ResponseEntity.badRequest().body("Không thể hủy: " + e.getMessage());
        }
    }
}
