// GiaGheController.java
package com.movietheater.Java.controller;

import com.movietheater.Java.dto.GiaGheCreateDto;
import com.movietheater.Java.dto.GiaGheResponseDto;
import com.movietheater.Java.dto.GiaGheUpdateDto;
import com.movietheater.Java.service.GiaGheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/giaghe")
public class GiaGheController {

    @Autowired
    private GiaGheService giaGheService;

    @GetMapping
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public List<GiaGheResponseDto> getAllGiaGhe() {
        return giaGheService.getAllGiaGhe();
    }

    @GetMapping("/{loaiGhe}")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public GiaGheResponseDto getGiaGheById(@PathVariable String loaiGhe) {
        return giaGheService.getGiaGheById(loaiGhe);
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ADMIN')")
    public GiaGheResponseDto createGiaGhe(@RequestBody GiaGheCreateDto dto) {
        return giaGheService.createGiaGhe(dto);
    }

    @PutMapping(value = "/{loaiGhe}")
    @PreAuthorize("hasRole('ADMIN')")
    public GiaGheResponseDto updateGiaGhe(@PathVariable String loaiGhe, @RequestBody GiaGheUpdateDto dto) {
        return giaGheService.updateGiaGhe(loaiGhe, dto);
    }

    @DeleteMapping("/{loaiGhe}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteGiaGhe(@PathVariable String loaiGhe) {
        giaGheService.deleteGiaGhe(loaiGhe);
    }
}
