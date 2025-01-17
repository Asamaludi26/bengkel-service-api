package com.bengkelservice.service;

import com.bengkelservice.model.PenjualanProduk;
import com.bengkelservice.repository.PenjualanProdukRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PenjualanProdukService {

    @Autowired
    private PenjualanProdukRepository penjualanProdukRepository;

    public List<PenjualanProduk> getAllProduk() {
        return penjualanProdukRepository.findAll();
    }

    public PenjualanProduk getProdukById(Long id) {
        Optional<PenjualanProduk> optionalProduk = penjualanProdukRepository.findById(id);
        return optionalProduk.orElse(null);
    }

    public void saveProduk(PenjualanProduk penjualanProduk) {
        penjualanProdukRepository.save(penjualanProduk);
    }

    public void deleteProduk(Long id) {
        penjualanProdukRepository.deleteById(id);
    }
}
