package com.bengkelservice.service;

import com.bengkelservice.model.Mekanik;
import com.bengkelservice.repository.MekanikRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MekanikService {

    @Autowired
    private MekanikRepository mekanikRepository;

    public List<Mekanik> getAllMekanik() {
        return mekanikRepository.findAll();
    }

    // Method untuk mendapatkan mekanik berdasarkan ID
    public Mekanik getMekanikById(Long id) {
        return mekanikRepository.findById(id).orElse(null);
    }

    // Method untuk mencari mekanik berdasarkan nama
    public List<Mekanik> searchMekanik(String searchQuery) {
        return mekanikRepository.findByNamaContainingIgnoreCase(searchQuery);
    }

    public Mekanik saveMekanik(Mekanik mekanik) {
        return mekanikRepository.save(mekanik);
    }

    // Method untuk menambah atau mengupdate mekanik
    public Mekanik addMekanik(Mekanik mekanik) {
        return mekanikRepository.save(mekanik); // Spring Data JPA otomatis handle add/update
    }

    public void deleteMekanik(Long id) {
        mekanikRepository.deleteById(id);
    }
}
