package com.bengkelservice.repository;

import com.bengkelservice.model.Layanan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface LayananRepository extends JpaRepository<Layanan, Long> {

    List<Layanan> findByTanggalBetween(LocalDate startDate, LocalDate endDate);  // ✅ Pakai nama field yang benar
}