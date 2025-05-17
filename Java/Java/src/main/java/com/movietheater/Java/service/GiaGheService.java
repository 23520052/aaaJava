// GiaGheService.java
package com.movietheater.Java.service;

import com.movietheater.Java.dto.GiaGheCreateDto;
import com.movietheater.Java.dto.GiaGheResponseDto;
import com.movietheater.Java.dto.GiaGheUpdateDto;
import com.movietheater.Java.entity.GiaGhe;
import com.movietheater.Java.exception.BadRequestException;
import com.movietheater.Java.exception.NotFoundException;
import com.movietheater.Java.repository.GiaGheRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GiaGheService {

    @Autowired
    private GiaGheRepository giaGheRepository;

    public List<GiaGheResponseDto> getAllGiaGhe() {
        return giaGheRepository.findAll().stream()
                .map(giaGhe -> new GiaGheResponseDto(
                        giaGhe.getLoaiGhe(),
                        giaGhe.getGia()))
                .collect(Collectors.toList());
    }

    public GiaGheResponseDto getGiaGheById(String loaiGhe) {
        GiaGhe giaGhe = giaGheRepository.findById(loaiGhe)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy giá ghế " + loaiGhe));
        return new GiaGheResponseDto(giaGhe.getLoaiGhe(), giaGhe.getGia());
    }

    public GiaGheResponseDto createGiaGhe(GiaGheCreateDto dto) {
        if (giaGheRepository.existsById(dto.getLoaiGhe())) {
            throw new BadRequestException("Đã tồn tại giá ghế " + dto.getLoaiGhe());
        }
        GiaGhe giaGhe = new GiaGhe(dto.getLoaiGhe(), dto.getGia());
        GiaGhe saved = giaGheRepository.save(giaGhe);
        return new GiaGheResponseDto(saved.getLoaiGhe(), saved.getGia());
    }

    public GiaGheResponseDto updateGiaGhe(String loaiGhe, GiaGheUpdateDto dto) {
        GiaGhe giaGhe = giaGheRepository.findById(loaiGhe)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy giá ghế " + loaiGhe));
        giaGhe.setGia(dto.getGia());
        GiaGhe updated = giaGheRepository.save(giaGhe);
        return new GiaGheResponseDto(updated.getLoaiGhe(), updated.getGia());
    }

    public void deleteGiaGhe(String loaiGhe) {
        if (!giaGheRepository.existsById(loaiGhe)) {
            throw new NotFoundException("Không tìm thấy giá ghế " + loaiGhe);
        }
        giaGheRepository.deleteById(loaiGhe);
    }
}
