package com.movietheater.Java.repository;

import com.movietheater.Java.entity.DatCho;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface DatChoRepository extends JpaRepository<DatCho, String> {
    List<DatCho> findByThanhToanIsNullAndNgayDatBefore(LocalDateTime thoiGian);

}
