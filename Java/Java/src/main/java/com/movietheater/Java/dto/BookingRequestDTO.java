package com.movietheater.Java.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.List;

@Data
public class BookingRequestDTO {
    @NotBlank(message = "Mã khách hàng không được để trống")
    private String maKhachHang;

    @NotBlank(message = "Mã suất chiếu không được để trống")
    private String maSuatChieu;

    @NotEmpty(message = "Danh sách mã ghế không được rỗng")
    private List<@NotBlank(message = "Mã ghế không hợp lệ") String> maGheList;

}
