package com.bengkelservice.repository;

import com.bengkelservice.model.PenjualanProduk;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PenjualanProdukRepository extends JpaRepository<PenjualanProduk, Long> {

    // Custom query method: mencari produk berdasarkan nama produk
    List<PenjualanProduk> findBynamaProdukContainingIgnoreCase(String namaProduk);
}