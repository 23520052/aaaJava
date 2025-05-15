package com.movietheater.Java.service;

import com.movietheater.Java.dto.*;

public interface DatVeService {
    String datVe(BookingRequestDTO dto);          // Đặt vé, trả về mã đặt
    void huyVe(CancelBookingDTO dto);             // Hủy vé theo mã đặt
    PaymentResultDTO thanhToan(PaymentDTO dto);
    BookingInfoDTO layThongTinDatCho(String maDat);
}
