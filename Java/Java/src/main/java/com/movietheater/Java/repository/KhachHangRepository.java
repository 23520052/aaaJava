package com.movietheater.Java.repository;

import com.movietheater.Java.entity.KhachHang;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KhachHangRepository extends JpaRepository<KhachHang, String> {
}
