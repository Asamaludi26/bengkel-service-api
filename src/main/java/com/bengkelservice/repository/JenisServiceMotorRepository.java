package com.bengkelservice.repository;

import com.bengkelservice.model.JenisServiceMotor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JenisServiceMotorRepository extends JpaRepository<JenisServiceMotor, Long> {

    // Custom query method: mencari jenis service motor berdasarkan nama
    List<JenisServiceMotor> findByNamaService(String namaService);
}