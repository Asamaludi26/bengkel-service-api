package com.bengkelservice.repository;

import com.bengkelservice.model.Mekanik;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MekanikRepository extends JpaRepository<Mekanik, Long> {

    List<Mekanik> findByNamaContainingIgnoreCase(String nama);
}