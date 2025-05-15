package com.movietheater.Java.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CancelBookingDTO {
    @NotBlank(message = "Mã đặt không được để trống")
    private String maDat;
}
