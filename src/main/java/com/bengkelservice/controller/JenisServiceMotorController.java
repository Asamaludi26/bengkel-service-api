package com.bengkelservice.controller;

import com.bengkelservice.model.JenisServiceMotor;
import com.bengkelservice.service.JenisServiceMotorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/jenis-service-motor")
public class JenisServiceMotorController {

    @Autowired
    private JenisServiceMotorService jenisServiceMotorService;

    // Endpoint untuk mendapatkan semua jenis service motor
    @GetMapping
    public List<JenisServiceMotor> getAllJenisServiceMotor() {
        return jenisServiceMotorService.getAllJenisServiceMotor();
    }

    // Endpoint untuk mencari jenis service motor berdasarkan nama service
    @GetMapping("/search")
    public List<JenisServiceMotor> getJenisServiceMotorByNamaService(@RequestParam String namaService) {
        return jenisServiceMotorService.getJenisServiceMotorByNamaService(namaService);
    }

    // Endpoint untuk menambah jenis service motor baru
    @PostMapping
    public JenisServiceMotor addJenisServiceMotor(@RequestBody JenisServiceMotor jenisServiceMotor) {
        return jenisServiceMotorService.addJenisServiceMotor(jenisServiceMotor);
    }
}