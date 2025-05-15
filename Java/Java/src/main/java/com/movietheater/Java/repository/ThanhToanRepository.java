package com.movietheater.Java.repository;

import com.movietheater.Java.entity.ThanhToan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ThanhToanRepository extends JpaRepository<ThanhToan, String> {
    boolean existsByDatCho_MaDat(String maDat);
}
