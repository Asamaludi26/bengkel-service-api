package com.bengkelservice.service;

import com.bengkelservice.model.Mekanik;
import com.bengkelservice.repository.MekanikRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    // Method untuk update data mekanik
    public Mekanik updateMekanik(Long id, Mekanik updatedMekanik) {
        if (mekanikRepository.existsById(id)) {
            updatedMekanik.setId(id); // Pastikan ID diatur untuk melakukan update
            return mekanikRepository.save(updatedMekanik);
        }
        throw new IllegalArgumentException("Mekanik dengan ID " + id + " tidak ditemukan");
    }

    public Optional<Mekanik> findById(Long id) {

        return mekanikRepository.findById(id);
    }

    public List<Mekanik> getMekanikByStatus(String status) {
        return mekanikRepository.findByStatus(status);
    }
}
