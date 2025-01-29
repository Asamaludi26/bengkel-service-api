package com.bengkelservice.repository;

import com.bengkelservice.model.Layanan;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface LayananRepository extends JpaRepository<Layanan, Long> {
}