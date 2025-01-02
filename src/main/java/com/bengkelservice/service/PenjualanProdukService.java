package com.bengkelservice.service;

import com.bengkelservice.model.PenjualanProduk;
import com.bengkelservice.repository.PenjualanProdukRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PenjualanProdukService {

    @Autowired
    private PenjualanProdukRepository penjualanProdukRepository;

    public List<PenjualanProduk> getAllProduk() {
        return penjualanProdukRepository.findAll();
    }

    public void saveProduk(PenjualanProduk produk) {
        penjualanProdukRepository.save(produk);
    }

    public PenjualanProduk getProdukById(Long id) {
        return penjualanProdukRepository.findById(id).orElse(null);
    }

    // Method untuk mencari mekanik berdasarkan nama
    public List<PenjualanProduk> searchProduk(String searchQuery) {
        return penjualanProdukRepository.findBynamaProdukContainingIgnoreCase(searchQuery);
    }

    public void deleteProduk(Long id) {
        penjualanProdukRepository.deleteById(id);
    }
}
