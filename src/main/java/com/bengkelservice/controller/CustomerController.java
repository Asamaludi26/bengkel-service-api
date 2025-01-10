package com.bengkelservice.controller;

import com.bengkelservice.model.Customer;
import com.bengkelservice.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/customer")  // Base URL untuk View
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    // Menampilkan halaman semua customer
    @GetMapping("/all")
    public String getAllCustomer(Model model) {
        List<Customer> customerList = customerService.getAllCustomer();
        model.addAttribute("customerList", customerList); // Gunakan nama atribut berbeda
        model.addAttribute("customer", new Customer()); // Untuk form tambah/edit
        return "customer"; // Menampilkan customer.html
    }

    // Menyimpan data customer baru atau update
    @PostMapping("/save")
    public String saveCustomer(@ModelAttribute Customer customer) {
        customerService.addCustomer(customer);
        return "redirect:/customer/all"; // Kembali ke halaman daftar
    }

    // Menghapus customer berdasarkan ID
    @GetMapping("/delete/{id}")
    public String deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomer(id);
        return "redirect:/customer/all"; // Kembali ke halaman daftar
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Optional<Customer> customer = customerService.findById(id);
        if (customer.isPresent()) {
            model.addAttribute("customer", customer.get()); // Mengisi model dengan data customer yang akan diedit
            return "customer"; // Mengembalikan nama template untuk form edit
        }
        return "redirect:/customer/all"; // Kembali ke halaman daftar jika tidak ditemukan
    }

    // Mencari customer berdasarkan nama
    @GetMapping("/search")
    public String searchCustomer(@RequestParam(value = "searchQuery", required = false) String searchQuery, Model model) {
        List<Customer> customerList;

        if (searchQuery != null && !searchQuery.isEmpty()) {
            customerList = customerService.searchCustomer(searchQuery);
        } else {
            customerList = customerService.getAllCustomer(); // Jika tidak ada query, tampilkan semua customer
        }

        model.addAttribute("customerList", customerList); // Daftar pelanggan
        model.addAttribute("customer", new Customer()); // Untuk form tambah/edit

        return "customer"; // Nama template Thymeleaf
    }
}