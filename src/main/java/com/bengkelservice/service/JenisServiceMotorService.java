package com.bengkelservice.service;

import com.bengkelservice.model.JenisServiceMotor;
import com.bengkelservice.repository.JenisServiceMotorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JenisServiceMotorService {

    @Autowired
    private JenisServiceMotorRepository repository;

    public List<JenisServiceMotor> getAllJenisService() {
        return repository.findAll();
    }

    public void saveJenisService(JenisServiceMotor jenisServiceMotor) {
        repository.save(jenisServiceMotor);
    }

    public JenisServiceMotor getJenisServiceById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public void deleteJenisService(Long id) {
        repository.deleteById(id);
    }
}