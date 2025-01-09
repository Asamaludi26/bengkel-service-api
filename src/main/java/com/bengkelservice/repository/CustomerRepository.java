package com.bengkelservice.repository;

import com.bengkelservice.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    // Custom query method: mencari customer berdasarkan nama
    List<Customer> findBynamaContainingIgnoreCase(String nama);
}