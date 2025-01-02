package com.bengkelservice.controllers;

import com.bengkelservice.model.Customer;
import com.bengkelservice.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class ViewController {

    @Autowired
    private CustomerService customerService;

    // Menampilkan Dashboard Utama
    @GetMapping("/")
    public String showDashboard() {
        // Mengarahkan ke file index.html
        return "index";
    }

    // Menampilkan halaman Data Customer
    //@GetMapping("/customer")
    //public String ShowCustomerPage(Model model) {
        // Mengambil data customer dari service
        //List<Customer> customers = customerService.getAllCustomers();

        // Mengirim data customer ke halaman HTML (Thymeleaf)
        //model.addAttribute("customers", customers);

        // Mengarahkan ke file index.html di folder templates
        //return "customer";
    //}

    // Menampilkan halaman Data Mekanik
    //@GetMapping("/mekanik")
    //public String ShowMekanikPage() {
        // Mengarahkan ke file mekanik.html di folder templates
        //return "mekanik";
    //}

    // Menampilkan halaman Penjualan Produk
    //@GetMapping("/penjualan")
    //public String penjualanPage() {
        //return "penjualan"; // Nama file HTML di folder templates
    //}
}