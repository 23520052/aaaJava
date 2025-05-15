package com.movietheater.Java.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class PaymentDTO {
    @NotBlank(message = "Mã đặt không được để trống")
    private String maDat;

    @NotBlank(message = "Phương thức thanh toán không được để trống")
    private String phuongThuc;

    @NotNull(message = "Tổng tiền không được để null")
    @DecimalMin(value = "0.0", inclusive = false, message = "Tổng tiền phải lớn hơn 0")
    private BigDecimal tongTien;
}
