package com.cinema.cinema_backend.repository;

import com.cinema.cinema_backend.entity.SuatChieu;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SuatChieuRepository extends JpaRepository<SuatChieu, Long> {
    List<SuatChieu> findByNgayChieuAndGioChieu(String ngayChieu, String gioChieu);

}
