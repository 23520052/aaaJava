package com.movietheater.Java.repository;

import com.movietheater.Java.entity.ChiTietDatCho;
import com.movietheater.Java.entity.ChiTietDatChoId;
import com.movietheater.Java.entity.DatCho;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChiTietDatChoRepository extends JpaRepository<ChiTietDatCho, ChiTietDatChoId> {
    List<ChiTietDatCho> findByDatCho(DatCho datCho);
}
