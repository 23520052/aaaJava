package com.cinema.cinema_backend.repository;

import com.cinema.cinema_backend.entity.DatCho;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DatChoRepository extends JpaRepository<DatCho, String> {

}
