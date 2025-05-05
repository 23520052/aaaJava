package com.cinema.cinema_backend.repository;

import com.cinema.cinema_backend.entity.KhachHang;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KhachHangRepository extends JpaRepository<KhachHang, Long> {
    KhachHang findByEmail(String email);
}
