package com.cinema.cinema_backend.service;

import com.cinema.cinema_backend.entity.DatCho;
import com.cinema.cinema_backend.repository.DatChoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DatChoService {
    @Autowired
    private DatChoRepository datChoRepository;

    // Tìm kiếm thông tin đặt chỗ theo mã đặt
    public Optional<DatCho> getDatChoById(String maDat) {
        // Kiểm tra nếu mã đặt chỗ là hợp lệ (chuỗi không rỗng)
        if (maDat == null || maDat.isEmpty()) {
            return Optional.empty();  // Trả về Optional.empty nếu mã đặt chỗ không hợp lệ
        }

        return datChoRepository.findById(maDat);
    }
}
