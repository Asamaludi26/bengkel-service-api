package com.bengkelservice.repository;

import com.bengkelservice.model.LayananProduk;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LayananProdukRepository extends JpaRepository<LayananProduk, Long> {
    // Metode pencarian berdasarkan layanan.id tanpa menggunakan @Query atau @Param
    List<LayananProduk> findByLayananId(Long layananId);
}