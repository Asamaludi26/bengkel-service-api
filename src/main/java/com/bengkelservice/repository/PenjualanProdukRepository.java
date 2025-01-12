package com.bengkelservice.repository;

import com.bengkelservice.model.PenjualanProduk;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PenjualanProdukRepository extends JpaRepository<PenjualanProduk, Long> {
}