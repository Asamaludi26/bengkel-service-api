package com.bengkelservice.service;

import com.bengkelservice.model.Customer;
import com.bengkelservice.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    // Method untuk mendapatkan semua customer
    public List<Customer> getAllCustomer() {
        return customerRepository.findAll();
    }

    // Method untuk mendapatkan customer berdasarkan ID
    public Customer getCustomerById(Long id) {
        return customerRepository.findById(id).orElse(null);
    }

    // Method untuk menambah atau mengupdate customer
    public Customer addCustomer(Customer customer) {
        return customerRepository.save(customer); // Spring Data JPA otomatis handle add/update
    }

    // Method untuk menghapus customer
    public void deleteCustomer(Long id) {
        customerRepository.deleteById(id);
    }

    // Method untuk mencari customer berdasarkan nama
    public List<Customer> searchCustomer(String searchQuery) {
        return customerRepository.findBynamaContainingIgnoreCase(searchQuery);
    }

    // Method untuk update data mekanik
    public Customer updateCustomer(Long id, Customer updatedCustomer) {
        if (customerRepository.existsById(id)) {
            updatedCustomer.setId(id); // Pastikan ID diatur untuk melakukan update
            return customerRepository.save(updatedCustomer);
        }
        throw new IllegalArgumentException("Customer dengan ID " + id + " tidak ditemukan");
    }

    public Optional<Customer> findById(Long id) {
        return customerRepository.findById(id);
    }

    public List<Customer> findAll() {
        return customerRepository.findAll();  // Fetch all customers from the database
    }
}