package com.bengkelservice.service;

import com.bengkelservice.model.Layanan;
import com.bengkelservice.model.LayananProduk;
import com.bengkelservice.model.PenjualanProduk;
import com.bengkelservice.repository.LayananProdukRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LayananProdukService {

    @Autowired
    private LayananProdukRepository layananProdukRepository;

    public void saveLayananProduk(LayananProduk layananProduk) {
        layananProdukRepository.save(layananProduk);
    }

    public List<LayananProduk> getByLayanan(Layanan layanan) {
        return layananProdukRepository.findByLayananId(layanan.getId());
    }
}