package com.cinema.cinema_backend.repository;

import com.cinema.cinema_backend.entity.ThanhToan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ThanhToanRepository extends JpaRepository<ThanhToan, String> {
    Optional<ThanhToan> findByDatCho_MaDat(String maDat);
}
