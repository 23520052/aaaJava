// SuatChieuController.java
package com.movietheater.Java.controller;

import com.movietheater.Java.dto.SuatChieuCreateDto;
import com.movietheater.Java.dto.SuatChieuResponseDto;
import com.movietheater.Java.dto.SuatChieuUpdateDto;
import com.movietheater.Java.service.SuatChieuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/suatchieu")
public class SuatChieuController {

    @Autowired
    private SuatChieuService suatChieuService;

    @GetMapping
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public List<SuatChieuResponseDto> getAllSuatChieu() {
        return suatChieuService.getAllSuatChieu();
    }

    @GetMapping("/{maSuat}")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public SuatChieuResponseDto getSuatChieuById(@PathVariable String maSuat) {
        return suatChieuService.getSuatChieuById(maSuat);
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ADMIN')")
    public SuatChieuResponseDto createSuatChieu(@RequestBody SuatChieuCreateDto dto) {
        return suatChieuService.createSuatChieu(dto);
    }

    @PutMapping(value = "/{maSuat}")
    @PreAuthorize("hasRole('ADMIN')")
    public SuatChieuResponseDto updateSuatChieu(@PathVariable String maSuat, @RequestBody SuatChieuUpdateDto dto) {
        return suatChieuService.updateSuatChieu(maSuat, dto);
    }

    @DeleteMapping("/{maSuat}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteSuatChieu(@PathVariable String maSuat) {
        suatChieuService.deleteSuatChieu(maSuat);
    }
}
