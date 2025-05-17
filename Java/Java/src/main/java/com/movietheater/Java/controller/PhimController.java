// PhimController.java
package com.movietheater.Java.controller;

import com.movietheater.Java.dto.PhimCreateDto;
import com.movietheater.Java.dto.PhimResponseDto;
import com.movietheater.Java.dto.PhimUpdateDto;
import com.movietheater.Java.service.PhimService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/phim")
public class PhimController {

    @Autowired
    private PhimService phimService;

    @GetMapping
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public List<PhimResponseDto> getAllPhim() {
        return phimService.getAllPhim();
    }

    @GetMapping("/{maPhim}")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public PhimResponseDto getPhimById(@PathVariable String maPhim) {
        return phimService.getPhimById(maPhim);
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ADMIN')")
    public PhimResponseDto createPhim(@RequestBody PhimCreateDto dto) {
        return phimService.createPhim(dto);
    }

    @PutMapping(value = "/{maPhim}")
    @PreAuthorize("hasRole('ADMIN')")
    public PhimResponseDto updatePhim(@PathVariable String maPhim, @RequestBody PhimUpdateDto dto) {
        return phimService.updatePhim(maPhim, dto);
    }

    @DeleteMapping("/{maPhim}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('ADMIN')")
    public void deletePhim(@PathVariable String maPhim) {
        phimService.deletePhim(maPhim);
    }
}
