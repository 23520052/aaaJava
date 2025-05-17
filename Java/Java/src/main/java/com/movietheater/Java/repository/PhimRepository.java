// PhimRepository.java
package com.movietheater.Java.repository;

import com.movietheater.Java.entity.Phim;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhimRepository extends JpaRepository<Phim, String> {
}
