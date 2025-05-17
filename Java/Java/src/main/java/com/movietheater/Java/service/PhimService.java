// PhimService.java
package com.movietheater.Java.service;

import com.movietheater.Java.dto.PhimCreateDto;
import com.movietheater.Java.dto.PhimResponseDto;
import com.movietheater.Java.dto.PhimUpdateDto;
import com.movietheater.Java.entity.Phim;
import com.movietheater.Java.exception.BadRequestException;
import com.movietheater.Java.exception.NotFoundException;
import com.movietheater.Java.repository.PhimRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PhimService {

    @Autowired
    private PhimRepository phimRepository;

    public List<PhimResponseDto> getAllPhim() {
        return phimRepository.findAll().stream()
                .map(phim -> new PhimResponseDto(
                        phim.getMaPhim(),
                        phim.getTenPhim(),
                        phim.getDaoDien(),
                        phim.getTheLoai(),
                        phim.getThoiLuong(),
                        phim.getMoTa()))
                .collect(Collectors.toList());
    }

    public PhimResponseDto getPhimById(String maPhim) {
        Phim phim = phimRepository.findById(maPhim)
                .orElseThrow(() -> new NotFoundException("Phim " + maPhim + " đã tồn tại"));
        return new PhimResponseDto(
                phim.getMaPhim(),
                phim.getTenPhim(),
                phim.getDaoDien(),
                phim.getTheLoai(),
                phim.getThoiLuong(),
                phim.getMoTa());
    }

    public PhimResponseDto createPhim(PhimCreateDto dto) {
        if (phimRepository.existsById(dto.getMaPhim())) {
            throw new BadRequestException("Phim  " + dto.getMaPhim() + " đã tồn tại");
        }
        Phim phim = new Phim(
                dto.getMaPhim(),
                dto.getTenPhim(),
                dto.getDaoDien(),
                dto.getTheLoai(),
                dto.getThoiLuong(),
                dto.getMoTa());
        Phim saved = phimRepository.save(phim);
        return new PhimResponseDto(
                saved.getMaPhim(),
                saved.getTenPhim(),
                saved.getDaoDien(),
                saved.getTheLoai(),
                saved.getThoiLuong(),
                saved.getMoTa());
    }

    public PhimResponseDto updatePhim(String maPhim, PhimUpdateDto dto) {
        Phim phim = phimRepository.findById(maPhim)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy phim " + maPhim));
        phim.setTenPhim(dto.getTenPhim());
        phim.setDaoDien(dto.getDaoDien());
        phim.setTheLoai(dto.getTheLoai());
        phim.setThoiLuong(dto.getThoiLuong());
        phim.setMoTa(dto.getMoTa());
        Phim updated = phimRepository.save(phim);
        return new PhimResponseDto(
                updated.getMaPhim(),
                updated.getTenPhim(),
                updated.getDaoDien(),
                updated.getTheLoai(),
                updated.getThoiLuong(),
                updated.getMoTa());
    }

    public void deletePhim(String maPhim) {
        if (!phimRepository.existsById(maPhim)) {
            throw new NotFoundException("Không tìm thấy phim " + maPhim);
        }
        phimRepository.deleteById(maPhim);
    }
}
