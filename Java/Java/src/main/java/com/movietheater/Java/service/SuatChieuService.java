// SuatChieuService.java
package com.movietheater.Java.service;

import com.movietheater.Java.dto.SuatChieuCreateDto;
import com.movietheater.Java.dto.SuatChieuResponseDto;
import com.movietheater.Java.dto.SuatChieuUpdateDto;
import com.movietheater.Java.entity.Phim;
import com.movietheater.Java.entity.PhongChieu;
import com.movietheater.Java.entity.SuatChieu;
import com.movietheater.Java.exception.BadRequestException;
import com.movietheater.Java.exception.NotFoundException;
import com.movietheater.Java.repository.PhimRepository;
import com.movietheater.Java.repository.PhongChieuRepository;
import com.movietheater.Java.repository.SuatChieuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SuatChieuService {

    @Autowired
    private SuatChieuRepository suatChieuRepository;

    @Autowired
    private PhimRepository phimRepository;

    @Autowired
    private PhongChieuRepository phongChieuRepository;

    public List<SuatChieuResponseDto> getAllSuatChieu() {
        return suatChieuRepository.findAll().stream()
                .map(suat -> new SuatChieuResponseDto(
                        suat.getMaSuat(),
                        suat.getNgayChieu(),
                        suat.getGioChieu(),
                        suat.getPhim().getMaPhim(),
                        suat.getPhongChieu().getMaPhong()))
                .collect(Collectors.toList());
    }

    public SuatChieuResponseDto getSuatChieuById(String maSuat) {
        SuatChieu suat = suatChieuRepository.findById(maSuat)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy suất chiếu " + maSuat));
        return new SuatChieuResponseDto(
                suat.getMaSuat(),
                suat.getNgayChieu(),
                suat.getGioChieu(),
                suat.getPhim().getMaPhim(),
                suat.getPhongChieu().getMaPhong());
    }

    public SuatChieuResponseDto createSuatChieu(SuatChieuCreateDto dto) {
        if (suatChieuRepository.existsById(dto.getMaSuat())) {
            throw new BadRequestException("Đã tồn tại suất chiếu " + dto.getMaSuat());
        }
        Phim phim = phimRepository.findById(dto.getMaPhim())
                .orElseThrow(() -> new NotFoundException("Không tồn tại phim " + dto.getMaPhim()));
        PhongChieu phongChieu = phongChieuRepository.findById(dto.getMaPhong())
                .orElseThrow(() -> new NotFoundException("Không tồn tại phòng chiếu " + dto.getMaPhong()));
        SuatChieu suat = new SuatChieu(
                dto.getMaSuat(),
                dto.getNgayChieu(),
                dto.getGioChieu(),
                phim,
                phongChieu);
        SuatChieu saved = suatChieuRepository.save(suat);
        return new SuatChieuResponseDto(
                saved.getMaSuat(),
                saved.getNgayChieu(),
                saved.getGioChieu(),
                saved.getPhim().getMaPhim(),
                saved.getPhongChieu().getMaPhong());
    }

    public SuatChieuResponseDto updateSuatChieu(String maSuat, SuatChieuUpdateDto dto) {
        SuatChieu suat = suatChieuRepository.findById(maSuat)
                .orElseThrow(() -> new NotFoundException("Không tồn tại suất chiếu " + maSuat));
        Phim phim = phimRepository.findById(dto.getMaPhim())
                .orElseThrow(() -> new NotFoundException("Không tồn tại phim " + dto.getMaPhim()));
        PhongChieu phongChieu = phongChieuRepository.findById(dto.getMaPhong())
                .orElseThrow(() -> new NotFoundException("Không tồn tại phòng chiếu " + dto.getMaPhong()));
        suat.setNgayChieu(dto.getNgayChieu());
        suat.setGioChieu(dto.getGioChieu());
        suat.setPhim(phim);
        suat.setPhongChieu(phongChieu);
        SuatChieu updated = suatChieuRepository.save(suat);
        return new SuatChieuResponseDto(
                updated.getMaSuat(),
                updated.getNgayChieu(),
                updated.getGioChieu(),
                updated.getPhim().getMaPhim(),
                updated.getPhongChieu().getMaPhong());
    }

    public void deleteSuatChieu(String maSuat) {
        if (!suatChieuRepository.existsById(maSuat)) {
            throw new NotFoundException("Không tồn tại suất chiếu " + maSuat);
        }
        suatChieuRepository.deleteById(maSuat);
    }
}
