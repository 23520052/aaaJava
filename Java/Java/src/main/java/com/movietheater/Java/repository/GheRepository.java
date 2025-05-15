package com.movietheater.Java.repository;

import com.movietheater.Java.entity.Ghe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface GheRepository extends JpaRepository<Ghe, String> {
    @Modifying
    @Query("UPDATE Ghe g SET g.trangThai = :trangThaiMoi WHERE g.maGhe = :maGhe AND g.trangThai = :trangThaiHienTai")
    int updateTrangThaiIfAvailable(@Param("maGhe") String maGhe,
                                   @Param("trangThaiMoi") String trangThaiMoi,
                                   @Param("trangThaiHienTai") String trangThaiHienTai);

}
