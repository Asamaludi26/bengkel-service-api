package com.bengkelservice.controller;

import com.bengkelservice.model.Customer;
import com.bengkelservice.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/search")
    public String searchCustomer(@RequestParam(value = "searchQuery", required = false) String searchQuery, Model model) {
        List<Customer> customer = customerService.searchCustomer(searchQuery);
        model.addAttribute("customer", customer);

        // Tambahkan objek customer kosong untuk form input
        model.addAttribute("customer", new Customer());

        return "customer"; // Nama template Thymeleaf
    }
}