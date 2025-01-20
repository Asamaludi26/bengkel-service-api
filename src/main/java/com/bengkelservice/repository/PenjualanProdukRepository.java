package com.bengkelservice.repository;

import com.bengkelservice.model.PenjualanProduk;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PenjualanProdukRepository extends JpaRepository<PenjualanProduk, Long> {

    // Search products by name (case-insensitive)
    List<PenjualanProduk> findByNamaContainingIgnoreCase(String searchQuery);

    // Add new method to find a single product by name (case-insensitive)
    PenjualanProduk findBynamaContainingIgnoreCase(String nama);
}
