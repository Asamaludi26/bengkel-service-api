package com.bengkelservice.service;

import com.bengkelservice.model.JenisServiceMotor;
import com.bengkelservice.repository.JenisServiceMotorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JenisServiceMotorService {

    @Autowired
    private JenisServiceMotorRepository jenisServiceMotorRepository;

    // Method untuk mendapatkan semua jenis service motor
    public List<JenisServiceMotor> getAllJenisServiceMotor() {
        return jenisServiceMotorRepository.findAll();
    }

    // Method untuk mencari jenis service motor berdasarkan nama service
    public List<JenisServiceMotor> getJenisServiceMotorByNamaService(String namaService) {
        return jenisServiceMotorRepository.findByNamaService(namaService);
    }

    // Method untuk menambah jenis service motor baru
    public JenisServiceMotor addJenisServiceMotor(JenisServiceMotor jenisServiceMotor) {
        return jenisServiceMotorRepository.save(jenisServiceMotor);
    }
}