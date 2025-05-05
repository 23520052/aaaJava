package com.cinema.cinema_backend.controller;

import com.cinema.cinema_backend.entity.DatCho;
import com.cinema.cinema_backend.service.DatChoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/dat-cho")
public class DatChoController {

    @Autowired
    private DatChoService datChoService;

    @GetMapping("/datcho/{maDat}")
    public Optional<DatCho> getDatChoById(@PathVariable String maDat) {
        return datChoService.getDatChoById(maDat);
    }
}
