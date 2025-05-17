// PhongChieuRepository.java
package com.movietheater.Java.repository;

import com.movietheater.Java.entity.PhongChieu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhongChieuRepository extends JpaRepository<PhongChieu, String> {
}
